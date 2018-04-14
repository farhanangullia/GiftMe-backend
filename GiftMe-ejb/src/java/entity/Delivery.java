/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.DeliveryStatus;

/**
 *
 * @author Farhan Angullia
 */
@Entity
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledTime;
    /*
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
     */

    @Column
    private String deliveryStatus;

    @Column
    private String shopAddress;

    @Column
    private String customerAddress;

    @Column
    private String timeToArrival;

    @Column
    private String distanceAway;

    @Column(unique = true, length = 8)
    private String deliveryCode;

    @OneToOne
    private Transaction transaction;

    public Delivery() {
    }

    public Delivery(String deliveryCode, String deliveryStatus, String customerAddress, String timeToArrival, String distanceAway, String shopAddress) {

        // this.scheduledTime = scheduledTime;
        //this.status = status;
        this.deliveryStatus = deliveryStatus;
        this.deliveryCode = deliveryCode;
        this.customerAddress = customerAddress;
        this.timeToArrival = timeToArrival;
        this.distanceAway = distanceAway;
        this.shopAddress = shopAddress;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliveryId != null ? deliveryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        if ((this.deliveryId == null && other.deliveryId != null) || (this.deliveryId != null && !this.deliveryId.equals(other.deliveryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Delivery[ id=" + deliveryId + " ]";
    }

    /**
     * @return the scheduledTime
     */
    public Date getScheduledTime() {
        return scheduledTime;
    }

    /**
     * @param scheduledTime the scheduledTime to set
     */
    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
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

    /**
     * @return the deliveryCode
     */
    public String getDeliveryCode() {
        return deliveryCode;
    }

    /**
     * @param deliveryCode the deliveryCode to set
     */
    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    /**
     * @return the deliveryStatus
     */
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * @param deliveryStatus the deliveryStatus to set
     */
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return the timeToArrival
     */
    public String getTimeToArrival() {
        return timeToArrival;
    }

    /**
     * @param timeToArrival the timeToArrival to set
     */
    public void setTimeToArrival(String timeToArrival) {
        this.timeToArrival = timeToArrival;
    }

    /**
     * @return the distanceAway
     */
    public String getDistanceAway() {
        return distanceAway;
    }

    /**
     * @param distanceAway the distanceAway to set
     */
    public void setDistanceAway(String distanceAway) {
        this.distanceAway = distanceAway;
    }

    /**
     * @return the shopAddress
     */
    public String getShopAddress() {
        return shopAddress;
    }

    /**
     * @param shopAddress the shopAddress to set
     */
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

}
