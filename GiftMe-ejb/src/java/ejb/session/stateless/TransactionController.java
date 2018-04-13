/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Transaction;
import entity.TransactionLineItem;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreateNewTransactionException;
import util.exception.ProductInsufficientQuantityOnHandException;
import util.exception.ProductNotFoundException;

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
    

    
    
    
    
}
