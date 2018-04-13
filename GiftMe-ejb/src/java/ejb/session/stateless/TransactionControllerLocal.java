/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Transaction;
import javax.ejb.Local;
import util.exception.CreateNewTransactionException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface TransactionControllerLocal {

    public Transaction createNewTransaction(Transaction newTransaction) throws CreateNewTransactionException;
    
}
