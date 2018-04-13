/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateReviewException;
import util.exception.ReviewNotFoundException;
import util.exception.ShopNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface ReviewControllerLocal {

    public List<Review> retrieveAllReviews();

    public List<Review> retrieveAllReviewsByShopId(Long id) throws ShopNotFoundException;

    public Review retrieveReviewById(Long id) throws ReviewNotFoundException;

    public Review createReview(Review review) throws CreateReviewException;

    public void updateReview(Review review);

  

    public Review createShopReview(Review review, Long shopid) throws CreateReviewException;
    
}
