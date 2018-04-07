/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Shop;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlType(name = "retrieveShopRsp", propOrder = {
		"shop"
	})
public class RetrieveShopRsp {
    private Shop shop;

    public RetrieveShopRsp() {
    }

    public RetrieveShopRsp(Shop shop) {
        this.shop = shop;
    }

    /**
     * @return the shop
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(Shop shop) {
        this.shop = shop;
    }
    
    
    
    
}
