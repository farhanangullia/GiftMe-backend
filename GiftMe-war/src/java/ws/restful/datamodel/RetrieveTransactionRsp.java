/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Transaction;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlType(name = "retrieveTransactionRsp", propOrder = {
		"transaction"
	})
public class RetrieveTransactionRsp {
    
    private Transaction transaction;

    public RetrieveTransactionRsp() {
    }

    public RetrieveTransactionRsp(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

   
    
    
}
