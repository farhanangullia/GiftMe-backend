/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Promotion;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlType(name = "retrievePromotionRsp", propOrder = {
		"promotion"
	})
public class RetrievePromotionRsp {
    
    private Promotion promotion;

    public RetrievePromotionRsp() {
    }

    public RetrievePromotionRsp(Promotion promotion) {
        this.promotion = promotion;
    }

    /**
     * @return the promotion
     */
    public Promotion getPromotion() {
        return promotion;
    }

    /**
     * @param promotion the promotion to set
     */
    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
    
    
    
}
