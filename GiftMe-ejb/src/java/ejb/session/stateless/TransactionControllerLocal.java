/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import com.google.maps.errors.ApiException;
import entity.Transaction;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateDeliveryException;
import util.exception.CreateNewTransactionException;
import util.exception.CustomerNotFoundException;
import util.exception.DeliveryNotFoundException;
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

    public Transaction retrieveTransactionByTransactionId(Long transactionId) throws TransactionNotFoundException;

    public List<Transaction> retrieveAllTransactions();

    public List<Transaction> retrieveTransactionsByCustomerEmail(String email);

    public Transaction createNewTransactionFromRemoteCheckoutRequest(String promoCode, List<RemoteCheckoutLineItem> remoteCheckoutLineItems, String email, String customerAddress, String shopAddress) throws CreateNewTransactionException, CustomerNotFoundException, PromotionNotFoundException, NoSuchAlgorithmException, CreateDeliveryException, InterruptedException, ApiException, IOException;

    public Transaction retrieveTransactionByDeliveryCode(String deliveryCode) throws DeliveryNotFoundException;

}
