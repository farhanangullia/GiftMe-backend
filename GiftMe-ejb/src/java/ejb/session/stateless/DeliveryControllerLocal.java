/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Delivery;
import javax.ejb.Local;
import util.exception.CreateDeliveryException;
import util.exception.DeliveryNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface DeliveryControllerLocal {

    public Delivery createDelivery(Delivery delivery) throws CreateDeliveryException;

    public Delivery retrieveDeliveryByDeliveryCode(String deliveryCode) throws DeliveryNotFoundException;

}
