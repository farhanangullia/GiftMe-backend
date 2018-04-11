/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import util.security.CryptographicHelper;

/**
 *
 * @author Farhan Angullia
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(length = 32, nullable = false)
    private String firstName, lastName;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 8, unique = true, nullable = false)
    private String mobileNumber;

    @Column(columnDefinition = "CHAR(32)")
    private String password;
    @Column(columnDefinition = "CHAR(32)")
    private String salt;

   /*@OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;
   */
    public Customer() {
        //  this.salt = CryptographicHelper.getInstance().generateRandomString(32);
         // transactions = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String email, String password, String mobileNumber) {

        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        //CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));

        this.mobileNumber = mobileNumber;

    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + customerId + " ]";
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the transactions
     */
 /* public List<Transaction> getTransactions() {
        return transactions;
    }
  */
    /**
     * @param transactions the transactions to set
     */
   
  /*   public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
*/
     
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setEncryptedPassword(String password) {
        if (password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        } else {
            this.password = null;
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* public void setPassword(String password) {
        this.password = password;
    }*/
    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

}
