/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ProductNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class ProductController implements ProductControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;

    @Override
    public List<Product> retrieveAllProducts() {

        Query query = em.createNamedQuery("selectAllProducts");
        return query.getResultList();
    }

    @Override
    public Product retrieveProduct(Long id) throws ProductNotFoundException {

        Product product = em.find(Product.class, id);

        if (product != null) {
            return product;
        } else {
            throw new ProductNotFoundException("Product " + id + " does not exist");
        }

    }

    @Override
    public void createProduct(Product product) {

        em.persist(product);
        em.flush();

    }
    
    @Override
    public void updateProduct(Product product) throws ProductNotFoundException {
        
        
         if(product.getProductId()!= null)
        {
            Product productToUpdate = retrieveProduct(product.getProductId());           
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setProductName(product.getProductName());
            productToUpdate.setSkuCode(product.getSkuCode());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setQuantityOnHand(product.getQuantityOnHand());
        }
        else
        {
            throw new ProductNotFoundException("ID not provided for product to be updated");
        }
        
        
    }
    
    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException
    {
        if(id != null)
        {
            Product productToDelete = retrieveProduct(id);
            em.remove(productToDelete);
        }
        else
        {
            throw new ProductNotFoundException("ID not provided for product to be deleted");
        }
    }

}
