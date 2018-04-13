/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Product;
import entity.Promotion;
import entity.Transaction;
import entity.TransactionLineItem;
import java.math.BigDecimal;
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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreateNewTransactionException;
import util.exception.CustomerNotFoundException;
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
    public Transaction createNewTransactionFromRemoteCheckoutRequest(String promoCode, List<RemoteCheckoutLineItem> remoteCheckoutLineItems, String email) throws CreateNewTransactionException, CustomerNotFoundException, PromotionNotFoundException
    {
        
  
        
        System.out.println("controller "+ email);
        
        if((email != null) && (remoteCheckoutLineItems != null) && (!remoteCheckoutLineItems.isEmpty()))
        {
            try
            {
                System.out.println("HEREEE ADASDAS");
                Promotion promotion = new Promotion();
               if(promoCode != null)
                   promotion = promotionControllerLocal.retrievePromotionByPromoCode(promoCode);
                
               
                System.out.println("trans controller email is " + email);
                    Customer customer =  customerControllerLocal.retrieveCustomerByEmail(email);
                System.out.println("trans controller customer email is " + customer.getEmail());
                List<TransactionLineItem> transactionLineItems = new ArrayList<>();
                Integer totalLineItem = 0;    
                Integer totalQuantity = 0;    
                BigDecimal totalAmount = new BigDecimal("0.00");

                for(RemoteCheckoutLineItem remoteCheckoutLineItem:remoteCheckoutLineItems)
                {
                    Product product = productControllerLocal.retrieveProductBySkuCode(remoteCheckoutLineItem.getSkuCode());
                     
                   
                    BigDecimal subTotal = product.getPrice().multiply(new BigDecimal(remoteCheckoutLineItem.getQuantity()));
                    ++totalLineItem;
                    transactionLineItems.add(new TransactionLineItem(totalLineItem, product, remoteCheckoutLineItem.getQuantity(), product.getPrice(), subTotal));
                    totalQuantity += remoteCheckoutLineItem.getQuantity();
                    totalAmount = totalAmount.add(subTotal);
                }

                if(promoCode==null)
                return createNewTransaction(new Transaction(totalLineItem, totalQuantity, totalAmount, new Date(), transactionLineItems, customer, new BigDecimal("0")));
                else
                    return createNewTransaction(new Transaction(totalLineItem, totalQuantity, totalAmount.subtract(promotion.getDiscount()), new Date(), transactionLineItems, customer, promotion.getDiscount()));
            }
            catch(ProductNotFoundException ex)
            {
                throw new CreateNewTransactionException("Unable to create new transaction remotely as product does not exist: " + ex.getMessage());
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
    
    
    
    
    
    
    
    
    
    
    
    
}
