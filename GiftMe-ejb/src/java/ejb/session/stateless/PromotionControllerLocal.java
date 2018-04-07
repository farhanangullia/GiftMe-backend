/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Promotion;
import java.util.List;
import javax.ejb.Local;
import util.exception.PromotionNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface PromotionControllerLocal {

    public List<Promotion> retrieveAllPromotions();

    public Promotion retrievePromotionByPromoCode(String promoCode) throws PromotionNotFoundException;

    public Promotion createPromotion(Promotion promotion);

    public void togglePromotionStatus(Promotion promotion, Boolean enabled);

    public Promotion retrievePromotionById(Long id) throws PromotionNotFoundException;
    
}
