/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import entity.Product;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Farhan Angullia
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;

 @PostConstruct
 public void postConstruct()
 {
     
     Product product = new Product();
    product.setProductName("Roses");
    product.setCategory("Flowers");
    product.setDescription("Red roses for your loved ones");
    product.setPrice(new BigDecimal("20"));
    product.setQuantityOnHand(50);
    product.setSkuCode("PROD005");
    
    em.persist(product);
    
     
 }

    
    
}
