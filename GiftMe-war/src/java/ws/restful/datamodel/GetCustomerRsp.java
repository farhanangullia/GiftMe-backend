/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Customer;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */

@XmlType(name = "getCustomerRsp", propOrder = {
		"customer"
	})
public class GetCustomerRsp {
    
    private Customer customer;

    public GetCustomerRsp() {
    }

    public GetCustomerRsp(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    
    
    
}
