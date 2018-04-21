/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Transaction;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlRootElement
@XmlType(name = "retrieveAllTransactionsByEmailRsp", propOrder = {
    "transactions"
})
public class RetrieveAllTransactionsByEmailRsp {

    private List<Transaction> transactions;

    public RetrieveAllTransactionsByEmailRsp() {
    }

    public RetrieveAllTransactionsByEmailRsp(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * @return the transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
