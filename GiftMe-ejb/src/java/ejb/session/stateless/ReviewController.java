/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import entity.Shop;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.CreateReviewException;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class ReviewController implements ReviewControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;
    
    @EJB
    private ShopControllerLocal shopControllerLocal;

     @Override
    public Review createReview(Review review) throws CreateReviewException {

        
        try {
        em.persist(review);
        em.flush();
        
        }
        catch(Exception ex) {
        throw new CreateReviewException(ex.getMessage());
        }
        
        return review;

    }
    
    
    
    @Override
    public Review createShopReview(Review review,Long shopId) throws CreateReviewException {

        
        try {
            
 ;
         Shop shop = shopControllerLocal.retrieveShopById(shopId);
       //  review.setShop(shop);

        em.persist(review);
        
        shop.getReviews().add(review);
        
        em.merge(shop);

        em.flush();
        
        }
        catch(Exception ex) {
        throw new CreateReviewException(ex.getMessage());
        }
        
        return review;

    }
    
    
    
    
    
    
    
    
    
    
    @Override
    public void updateReview(Review review) {
        em.merge(review);
        
    }
    
    
    @Override
    public List<Review> retrieveAllReviews() {

        Query query = em.createNamedQuery("selectAllReviews");
        return query.getResultList();
    }

    @Override
    public List<Review> retrieveAllReviewsByShopId(Long id) {

        Query query = em.createQuery("SELECT r FROM Review r WHERE r.shop.shopId = :inShopId");
        query.setParameter("inShopId", id);
        return query.getResultList();
    }
    
     @Override
    public Review retrieveReviewById(Long id) throws ReviewNotFoundException {

        Review review = em.find(Review.class, id);

        if (review != null) {
            return review;
        } else {
            throw new ReviewNotFoundException("Review " + id + " does not exist");
        }

    }

}
