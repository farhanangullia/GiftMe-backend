/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import java.util.List;
import javax.ejb.Local;
import util.exception.ProductInsufficientQuantityOnHandException;
import util.exception.ProductNotFoundException;
import util.exception.ShopNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface ProductControllerLocal {

    public List<Product> retrieveAllProducts();

    public Product createProduct(Product product);

    public void updateProduct(Product product) throws ProductNotFoundException;

    public void deleteProduct(Long id) throws ProductNotFoundException;

    public Product retrieveProductById(Long id) throws ProductNotFoundException;

    public void creditQuantityOnHand(Long productId, Integer quantityToCredit) throws ProductNotFoundException;

    public void debitQuantityOnHand(Long productId, Integer quantityToDebit) throws ProductNotFoundException, ProductInsufficientQuantityOnHandException;

    public List<Product> retrieveAllProductsByShopId(Long id) throws ShopNotFoundException;

    public Product retrieveProductBySkuCode(String skuCode) throws ProductNotFoundException;
    
    
    
}
