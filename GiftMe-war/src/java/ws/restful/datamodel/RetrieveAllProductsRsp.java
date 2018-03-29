/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Product;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */


@XmlRootElement
@XmlType(name = "retrieveAllProductsRsp", propOrder = {
    "products"
})
public class RetrieveAllProductsRsp {
    
    private List<Product> products;

    public RetrieveAllProductsRsp() {
    }

    public RetrieveAllProductsRsp(List<Product> products) {
        this.products = products;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
    
    
}
