/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Product;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlType(name = "retrieveProductRsp", propOrder = {
    "product"
})
public class RetrieveProductRsp {

    private Product product;

    public RetrieveProductRsp() {
    }

    public RetrieveProductRsp(Product product) {
        this.product = product;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

}
