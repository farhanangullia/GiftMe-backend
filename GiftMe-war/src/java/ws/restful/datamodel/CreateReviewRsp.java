/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlRootElement
@XmlType(name = "createReviewRsp", propOrder = {
    "reviewId"
})
public class CreateReviewRsp {
    
    private Long reviewId;

    public CreateReviewRsp() {
    }

    public CreateReviewRsp(Long reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * @return the reviewId
     */
    public Long getReviewId() {
        return reviewId;
    }

    /**
     * @param reviewId the reviewId to set
     */
    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }
    
    
    
}
