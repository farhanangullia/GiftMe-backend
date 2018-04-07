/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateCustomerException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.security.CryptographicHelper;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class CustomerController implements CustomerControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;


  @Override
    public Customer createNewCustomer(Customer newCustomer) throws CreateCustomerException
    {
        try {
        em.persist(newCustomer);
        em.flush();
        
        return newCustomer;
        }
         catch(PersistenceException ex)
        {
            if(ex.getCause() != null && 
                    ex.getCause().getCause() != null &&
                    ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException"))
            {
                throw new CreateCustomerException("Customer with same email already exist");
            }
            else
            {
                throw new CreateCustomerException("An unexpected error has occurred: " + ex.getMessage());
            }
        }
        catch(Exception ex)
        {
            throw new CreateCustomerException("An unexpected error has occurred: " + ex.getMessage());
        }
        
    }
    
    
       @Override
    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.email = :inEmail");
        query.setParameter("inEmail", email);
        
        try
        {
            return (Customer)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Customer email " + email + " does not exist!");
        }
    }
    
    @Override
    public Customer customerLogin(String email, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Customer customer = retrieveCustomerByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + customer.getSalt()));
            
            if(customer.getPassword().equals(passwordHash))
            {
                return customer;
            }
            else
            {
                throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
            }
        }
        catch(CustomerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
        }
    }
    
    
    
    
     @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException
    {
        if(customer.getCustomerId() != null)
        {
            Customer customerToUpdate = retrieveCustomerByEmail(customer.getEmail());
            if(customerToUpdate.getEmail().equals(customer.getEmail()))
            {
                customerToUpdate.setFirstName(customer.getFirstName());
                customerToUpdate.setLastName(customer.getLastName());
                customerToUpdate.setMobileNumber(customer.getMobileNumber());
                customerToUpdate.setEmail(customer.getEmail());
            }
        }
        else
        {
            throw new CustomerNotFoundException("Customer email not provided for customer to be updated");
        }
    }
    
    
  
    
    
}
