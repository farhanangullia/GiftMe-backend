/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Review;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlRootElement
@XmlType(name = "retrieveAllReviewsRsp", propOrder = {
    "reviews"
})
public class RetrieveAllReviewsRsp {
    
    private List<Review> reviews;

    public RetrieveAllReviewsRsp() {
    }

    public RetrieveAllReviewsRsp(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    
    
}
