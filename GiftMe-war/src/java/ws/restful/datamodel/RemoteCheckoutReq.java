/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlRootElement
@XmlType(name = "remoteCheckoutReq", propOrder = {
    "promoCode","email",
  
    "remoteCheckoutLineItems"
})
public class RemoteCheckoutReq {
    
    private String promoCode;
    private String email;
      private List<RemoteCheckoutLineItem> remoteCheckoutLineItems;


    public RemoteCheckoutReq() {
             this.remoteCheckoutLineItems = new ArrayList<>();
    }

    public RemoteCheckoutReq(String promoCode, String email, List<RemoteCheckoutLineItem> remoteCheckoutLineItems) {
        this.promoCode = promoCode;
        this.email = email;
        this.remoteCheckoutLineItems = remoteCheckoutLineItems;
    }

  
    
        /**
     * @return the promoCode
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * @param promoCode the promoCode to set
     */
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
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

    
    
    
  
      @XmlElements({
        @XmlElement(name="remoteCheckoutLineItem", type=RemoteCheckoutLineItem.class)
    })
    @XmlElementWrapper(name = "remoteCheckoutLineItems")
    public List<RemoteCheckoutLineItem> getRemoteCheckoutLineItems() {
        return remoteCheckoutLineItems;
    }

    public void setRemoteCheckoutLineItems(List<RemoteCheckoutLineItem> remoteCheckoutLineItems) {
        this.remoteCheckoutLineItems = remoteCheckoutLineItems;
    }


    
}
