/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductControllerLocal;
import entity.Product;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Farhan Angullia
 */
@Named(value = "productManagedBean")
@RequestScoped
public class ProductManagedBean {

    @EJB
    private ProductControllerLocal productController;

    
    private List<Product> products;
    
    /**
     * Creates a new instance of ProductManagedBean
     */
    public ProductManagedBean() {
    }

    
    @PostConstruct
    public void postConstruct()
    {
        products = productController.retrieveAllProducts();
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
