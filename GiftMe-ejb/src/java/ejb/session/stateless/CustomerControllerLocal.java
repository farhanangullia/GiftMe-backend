/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface CustomerControllerLocal {

    public Customer createNewCustomer(Customer newCustomer);

    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException;

    public Customer customerLogin(String email, String password) throws InvalidLoginCredentialException;

    public void updateCustomer(Customer customer) throws CustomerNotFoundException;
    
}