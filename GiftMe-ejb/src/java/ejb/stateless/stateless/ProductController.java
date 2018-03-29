/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless.stateless;

import entity.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class ProductController implements ProductControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;


    
    @Override
    public List<Product> retrieveAllProducts()
    {
        
        Query query = em.createQuery("SELECT p FROM Product p");
        return query.getResultList();
    }

    
    
    
    
    
}
