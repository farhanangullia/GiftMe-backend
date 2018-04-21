/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Promotion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.PromotionNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class PromotionController implements PromotionControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;

    @Override
    public List<Promotion> retrieveAllPromotions() {

        Query query = em.createNamedQuery("selectAllPromotions");
        return query.getResultList();
    }

    @Override
    public Promotion retrievePromotionByPromoCode(String promoCode) throws PromotionNotFoundException {
        Query query = em.createQuery("SELECT p FROM Promotion p WHERE p.promoCode = :inPromoCode AND p.enabled = true");
        query.setParameter("inPromoCode", promoCode);

        try {
            return (Promotion) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new PromotionNotFoundException("Promotion: " + promoCode + " does not exist!");
        }
    }

    @Override
    public Promotion retrievePromotionById(Long id) throws PromotionNotFoundException {

        Promotion promotion = em.find(Promotion.class, id);

        if (promotion != null) {
            return promotion;
        } else {
            throw new PromotionNotFoundException("Promotion " + id + " does not exist");
        }

    }

    @Override
    public Promotion createPromotion(Promotion promotion) {

        em.persist(promotion);
        em.flush();
        em.refresh(promotion);

        return promotion;

    }

    @Override
    public void togglePromotionStatus(Promotion promotion, Boolean enabled) {

        promotion.setEnabled(enabled);
        em.merge(promotion);
    }

}
