/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Transaction;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewTransactionException;
import util.exception.CustomerNotFoundException;
import util.exception.PromotionNotFoundException;
import util.exception.TransactionNotFoundException;
import ws.restful.datamodel.RemoteCheckoutLineItem;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface TransactionControllerLocal {

    public Transaction createNewTransaction(Transaction newTransaction) throws CreateNewTransactionException;

    public Transaction createNewTransactionFromRemoteCheckoutRequest(String promoCode, List<RemoteCheckoutLineItem> remoteCheckoutLineItems, String email) throws CreateNewTransactionException, CustomerNotFoundException, PromotionNotFoundException;

    public Transaction retrieveTransactionByTransactionId(Long transactionId) throws TransactionNotFoundException;



    public List<Transaction> retrieveAllTransactions();

    public List<Transaction> retrieveTransactionsByCustomerEmail(String email);
    
}
