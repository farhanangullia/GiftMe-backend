/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import com.google.maps.errors.ApiException;
import entity.Customer;
import entity.Delivery;
import entity.Product;
import entity.Promotion;
import entity.Transaction;
import entity.TransactionLineItem;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.common.DeliveryDistanceTimeCalculator;
import util.common.RandomGenerator;
import util.email.EmailManager;
import util.exception.CreateDeliveryException;
import util.exception.CreateNewTransactionException;
import util.exception.CustomerNotFoundException;
import util.exception.DeliveryNotFoundException;
import util.exception.ProductInsufficientQuantityOnHandException;
import util.exception.ProductNotFoundException;
import util.exception.PromotionNotFoundException;
import util.exception.TransactionNotFoundException;
import ws.restful.datamodel.RemoteCheckoutLineItem;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class TransactionController implements TransactionControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;

 @Resource
    private EJBContext eJBContext;
    
    @EJB
    private ProductControllerLocal productControllerLocal;
    
    @EJB
    private CustomerControllerLocal customerControllerLocal;
    
    @EJB
    private PromotionControllerLocal promotionControllerLocal;
    
    @EJB
    private DeliveryControllerLocal deliveryControllerLocal;
    

    public TransactionController() {
    }
    
    
    
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Transaction createNewTransaction(Transaction newTransaction) throws CreateNewTransactionException
    {
        if(newTransaction != null)
        {
            try
            {
                em.persist(newTransaction);

                for(TransactionLineItem transactionLineItem:newTransaction.getTransactionLineItems())
                {
                    productControllerLocal.debitQuantityOnHand(transactionLineItem.getProduct().getProductId(), transactionLineItem.getQuantity());
                    em.persist(transactionLineItem);
                }

                em.flush();

                return newTransaction;
            }
            catch(ProductNotFoundException | ProductInsufficientQuantityOnHandException ex)
            {
                eJBContext.setRollbackOnly();
                throw new CreateNewTransactionException(ex.getMessage());
            }
        }
        else
        {
            throw new CreateNewTransactionException("Transaction information not provided");
        }
    }
    

    
    
    
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Transaction createNewTransactionFromRemoteCheckoutRequest(String promoCode, List<RemoteCheckoutLineItem> remoteCheckoutLineItems, String email, String customerAddress, String shopAddress) throws CreateNewTransactionException, CustomerNotFoundException, PromotionNotFoundException, NoSuchAlgorithmException, CreateDeliveryException
    {
        
  
        
        System.out.println("controller "+ email);
        
        if((email != null) && (remoteCheckoutLineItems != null) && (!remoteCheckoutLineItems.isEmpty()))
        {
            try
            {
                System.out.println("HEREEE ADASDAS");
                Promotion promotion = new Promotion();
               if(promoCode != null)
                   if(!promoCode.isEmpty())
                   promotion = promotionControllerLocal.retrievePromotionByPromoCode(promoCode);
                
               
               
               
                System.out.println("trans controller email is " + email);
                    Customer customer =  customerControllerLocal.retrieveCustomerByEmail(email);
                System.out.println("trans controller customer email is " + customer.getEmail());
                List<TransactionLineItem> transactionLineItems = new ArrayList<>();
                Integer totalLineItem = 0;    
                Integer totalQuantity = 0;    
                BigDecimal totalAmount = new BigDecimal("0.00");
                BigDecimal deliveryFee = new BigDecimal("0.00");
                         String deliveryCode;
                
                         

                for(RemoteCheckoutLineItem remoteCheckoutLineItem:remoteCheckoutLineItems)
                {
                    Product product = productControllerLocal.retrieveProductBySkuCode(remoteCheckoutLineItem.getSkuCode());
                     
                   
                    BigDecimal subTotal = product.getPrice().multiply(new BigDecimal(remoteCheckoutLineItem.getQuantity()));
                    ++totalLineItem;
                    transactionLineItems.add(new TransactionLineItem(totalLineItem, product, remoteCheckoutLineItem.getQuantity(), product.getPrice(), subTotal));
                    totalQuantity += remoteCheckoutLineItem.getQuantity();
                    totalAmount = totalAmount.add(subTotal);
                }
                
                if(totalAmount.compareTo(new BigDecimal("40")) == -1)
                    deliveryFee = new BigDecimal("10");
                    
                
                //CALCULATE ARRIVAL TIME AND DISTANCE BASED ON GOOGLE API
  
        //  DeliveryDistanceTimeCalculator.getArrivalTime(shopAddress, customerAddress);
       //Long dist = 0L;
          //  DeliveryDistanceTimeCalculator.getDriveDist(shopAddress, customerAddress);
         //      String distanceAway = dist.toString().concat(" Metres");
               
              List<String> exactAddresses = DeliveryDistanceTimeCalculator.getExactAddresses(shopAddress, customerAddress);
               String fromAddress = exactAddresses.get(0);
               String toAddress = exactAddresses.get(1);
                
                
                  String arrivalTime = DeliveryDistanceTimeCalculator.getArrivalTime(shopAddress, customerAddress);
                  System.out.println("ARRIVAL TIME" + arrivalTime);
                  Long dist = DeliveryDistanceTimeCalculator.getDriveDist(shopAddress, customerAddress);
                  String distanceAway = dist.toString().concat(" Metres");
                  
           
                deliveryCode = RandomGenerator.RandomDeliveryCode();
                
                    System.out.println("CODE IS "+ deliveryCode);
                  
                Delivery delivery = deliveryControllerLocal.createDelivery(new Delivery( deliveryCode ,"PROCESSING", toAddress , arrivalTime, distanceAway, fromAddress));
                totalAmount = totalAmount.add(deliveryFee); //Total amount = Total Amt + Delivery Fee

                if(promoCode==null || promoCode.isEmpty())
                { //without promo discount
                   Transaction transaction = createNewTransaction(new Transaction(totalLineItem, totalQuantity, totalAmount, new Date(), transactionLineItems, customer, new BigDecimal("0"), deliveryFee,delivery));
                   
                   delivery.setTransaction(transaction);
                   em.merge(delivery);
                   
                      sendDeliveryEmail(delivery);
                   
                   return transaction;
                }
                
                
                  Transaction transaction = createNewTransaction(new Transaction(totalLineItem, totalQuantity, totalAmount.subtract(promotion.getDiscount()), new Date(), transactionLineItems, customer, promotion.getDiscount(), deliveryFee,delivery));
                    
                       delivery.setTransaction(transaction);
                   em.merge(delivery);
                  
                  sendDeliveryEmail(delivery);
                   
                    return transaction;
                  
                    
                
                          }
            catch(ProductNotFoundException | CustomerNotFoundException ex)
            {
                throw new CreateNewTransactionException("Unable to create new transaction remotely as product does not exist: " + ex.getMessage());
            }
             catch( Exception exc)
            {System.out.println(exc.getMessage());
               throw new CreateNewTransactionException("Nothing to checkout dsdremotely!");
               // throw new InterruptedException("Unable to create new transaction remotely as exception occured in google api: " + exc.getMessage());
            }
           
        }
        else
        {
            throw new CreateNewTransactionException("Nothing to checkout remotely!");
            
        }
   }
   

    
    
    
     @Override
    public List<Transaction> retrieveAllTransactions()
    {
        Query query = em.createQuery("SELECT t FROM Transaction t");
        
        List<Transaction> transactions = query.getResultList();
        
        for(Transaction transaction:transactions)
        {
            transaction.getTransactionLineItems().size();
        }
        
        return transactions;
    }
    
    
    
    @Override
    public Transaction retrieveTransactionByTransactionId(Long transactionId) throws TransactionNotFoundException
    {
        Transaction transaction = em.find(Transaction.class, transactionId);
        
        if(transaction != null)
        {
            transaction.getTransactionLineItems().size();
            
            return transaction;
        }
        else
        {
            throw new TransactionNotFoundException("Transaction ID " + transactionId + " does not exist!");
        }                
    }
    
    
      @Override
    public List<Transaction> retrieveTransactionsByCustomerEmail(String email)
    {
        Query query = em.createNamedQuery("selectAllTransactionsByCustomerEmail");
        query.setParameter("inCustomerEmail", email);
        
        List<Transaction> transactions = query.getResultList();
        
        for(Transaction transaction:transactions)
        {
            transaction.getTransactionLineItems().size();
        }
        
        return transactions;
    }
    
    
     @Override
    public Transaction retrieveTransactionByDeliveryCode(String deliveryCode) throws DeliveryNotFoundException
    {
       Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.delivery.deliveryCode = :inDeliveryCode");
        query.setParameter("inDeliveryCode", deliveryCode);
        
        try
        {
            return (Transaction)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            throw new DeliveryNotFoundException("Transaction with delivery code: " + deliveryCode + " does not exist!");
        }
    }
    
    
    
    
     public void sendDeliveryEmail(Delivery delivery) {

        //    String encryptedPassword 
        System.out.println("HERE");
       String receipientEmail = delivery.getTransaction().getCustomer().getEmail();
        EmailManager emailManager = new EmailManager("e0032247", "giftmepassword");    //replace e0032247 with ur SOC unix acc and <MY PASSWORD> with ur UNIX acc password OR leave it (this acc is Farhan's)
        Boolean result = emailManager.emailDelivery("mail.giftme@gmail.com", receipientEmail, delivery); //replace <EMAIL TO> with the email of the receipient

        if (result) {
            // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email sent successfully", null));
            System.out.println("Email sent successfully");
        } else {

            System.out.println("An error has occured while sending email");

            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while sending email", null));
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
