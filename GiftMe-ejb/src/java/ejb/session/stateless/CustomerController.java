/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Promotion;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.email.EmailManager;
import util.exception.CreateCustomerException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;
import util.security.CryptographicHelper;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class CustomerController implements CustomerControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;

    @EJB
    PromotionControllerLocal promotionControllerLocal;

    @Override
    public Long createNewCustomer(Customer newCustomer) throws CreateCustomerException {
        try {
            newCustomer = encryptCustomerPassword(newCustomer);
            em.persist(newCustomer);
            em.flush();
            em.refresh(newCustomer);
            if (!newCustomer.getEmail().equals("mail.giftme@gmail.com")) {

                Promotion defaultPromotion = promotionControllerLocal.retrievePromotionByPromoCode("5OFF");
                sendNewCustomerEmail(newCustomer, defaultPromotion);
            }

            return newCustomer.getCustomerId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CreateCustomerException("Customer with same email or mobile number already exist");
            } else {
                throw new CreateCustomerException("An unexpected error has occurred: " + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new CreateCustomerException("An unexpected error has occurred: " + ex.getMessage());
        }

    }

    @Override
    public Customer encryptCustomerPassword(Customer customer) {

        customer.setSalt(CryptographicHelper.getInstance().generateRandomString(32));
        customer.setPassword(CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(customer.getPassword() + customer.getSalt())));

        return customer;
    }

    @Override
    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.email = :inEmail");
        query.setParameter("inEmail", email);

        try {
            return (Customer) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Customer email " + email + " does not exist!");
        }
    }

    @Override
    public Customer customerLogin(String email, String password) throws InvalidLoginCredentialException {
        try {
            Customer customer = retrieveCustomerByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + customer.getSalt()));

            if (customer.getPassword().equals(passwordHash)) {
                return customer;
            } else {
                throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException, UpdateCustomerException {
        if (customer.getCustomerId() != null) {
            Customer customerToUpdate = retrieveCustomerByEmail(customer.getEmail());  //if error then change to ID
            if (customerToUpdate.getEmail().equals(customer.getEmail())) {
                customerToUpdate.setFirstName(customer.getFirstName());
                customerToUpdate.setLastName(customer.getLastName());
                customerToUpdate.setMobileNumber(customer.getMobileNumber());
            } else {
                throw new UpdateCustomerException("Email of customer does not match the email given");
            }

        } else {
            throw new CustomerNotFoundException("Customer email not provided for customer to be updated");
        }
    }

    @Override
    public void updateCustomerPassword(Customer customer) throws CustomerNotFoundException, UpdateCustomerException {
        if (customer.getCustomerId() != null) {
            Customer customerToUpdate = retrieveCustomerByEmail(customer.getEmail());  //if error then change to ID
            if (customerToUpdate.getEmail().equals(customer.getEmail())) {

                customerToUpdate.setEncryptedPassword(customer.getPassword());
            } else {
                throw new UpdateCustomerException("Email of customer does not match the email given");
            }

        } else {
            throw new CustomerNotFoundException("Customer email not provided for customer to be updated");
        }
    }

    public void sendNewCustomerEmail(Customer customer, Promotion defaultPromotion) {
        String receipientEmail = customer.getEmail();
        EmailManager emailManager = new EmailManager("e0032247", "giftmepassword");    //replace e0032247 with ur SOC unix acc and <MY PASSWORD> with ur UNIX acc password OR leave it (this acc is Farhan's)
        Boolean result = emailManager.emailNewCustomer("mail.giftme@gmail.com", receipientEmail, customer, defaultPromotion); //replace <EMAIL TO> with the email of the receipient

        if (result) {
            System.out.println("Email sent successfully");
        } else {

            System.out.println("An error has occured while sending email");
        }
    }
    
    
    @Override
        public void sendForgotPasswordEmail(String email) throws CustomerNotFoundException{
   
            try{
            Customer customer = retrieveCustomerByEmail(email);
            
            String password = "defaultPassword";
            customer.setEncryptedPassword(password);
            
            em.merge(customer);
            
        EmailManager emailManager = new EmailManager("e0032247", "giftmepassword");    //replace e0032247 with ur SOC unix acc and <MY PASSWORD> with ur UNIX acc password OR leave it (this acc is Farhan's)
        Boolean result = emailManager.emailForgotPassword("mail.giftme@gmail.com", customer.getEmail(), customer, password); //replace <EMAIL TO> with the email of the receipient

        if (result) {
            System.out.println("Email sent successfully");
        } else {

            System.out.println("An error has occured while sending email");
        }
    }
        catch(CustomerNotFoundException ex)
        {
            throw new CustomerNotFoundException(ex.getMessage());
        }

}
}
