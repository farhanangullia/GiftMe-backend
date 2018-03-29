/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import java.util.List;
import javax.ejb.Local;
import util.exception.ProductNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface ProductControllerLocal {

    public List<Product> retrieveAllProducts();

    public Product retrieveProduct(Long id) throws ProductNotFoundException;

    public void createProduct(Product product);

    public void updateProduct(Product product) throws ProductNotFoundException;

    public void deleteProduct(Long id) throws ProductNotFoundException;
    
    
    
}
