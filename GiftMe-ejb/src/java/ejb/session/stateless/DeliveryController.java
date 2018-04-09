/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Delivery;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.CreateDeliveryException;
import util.exception.DeliveryNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class DeliveryController implements DeliveryControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;

    @Override
    public Delivery createDelivery(Delivery delivery) throws CreateDeliveryException {

        try {

            em.persist(delivery);
            em.flush();

            return delivery;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null
                    && ex.getCause().getCause() != null
                    && ex.getCause().getCause().getClass().getSimpleName().equals("MySQLIntegrityConstraintViolationException")) {
                throw new CreateDeliveryException("Delivery with the same delivery code already exist");
            } else {
                throw new CreateDeliveryException("An unexpected error has occurred: " + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new CreateDeliveryException("An unexpected error has occurred: " + ex.getMessage());
        }

    }

    @Override
    public Delivery retrieveDeliveryByDeliveryCode(String deliveryCode) throws DeliveryNotFoundException {
        Query query = em.createQuery("SELECT d FROM Delivery d WHERE d.deliveryCode = :inDeliveryCode");
        query.setParameter("inDeliveryCode", deliveryCode);

        try {
            return (Delivery) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new DeliveryNotFoundException("Delivery code " + deliveryCode + " does not exist!");
        }
    }

}
