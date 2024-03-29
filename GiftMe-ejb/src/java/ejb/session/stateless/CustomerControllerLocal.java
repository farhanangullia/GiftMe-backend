/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import util.exception.CreateCustomerException;
import util.exception.CustomerNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface CustomerControllerLocal {

    public Long createNewCustomer(Customer newCustomer) throws CreateCustomerException;

    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException;

    public Customer customerLogin(String email, String password) throws InvalidLoginCredentialException;

    public void updateCustomer(Customer customer) throws CustomerNotFoundException, UpdateCustomerException;

    // public Long createCustomerFromBackend(Customer newCustomer) throws CreateCustomerException;
    public Customer encryptCustomerPassword(Customer customer);

    public void updateCustomerPassword(Customer customer) throws CustomerNotFoundException, UpdateCustomerException;

    public void sendForgotPasswordEmail(String email) throws CustomerNotFoundException;

}
