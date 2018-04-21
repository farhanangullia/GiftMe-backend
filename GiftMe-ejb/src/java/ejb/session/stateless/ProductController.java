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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ProductInsufficientQuantityOnHandException;
import util.exception.ProductNotFoundException;
import util.exception.ShopNotFoundException;

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
    public List<Product> retrieveAllProductsByShopId(Long id) throws ShopNotFoundException {

        try {

            Query query = em.createQuery("SELECT p FROM Product p WHERE p.shop.shopId = :inShopId ORDER BY p.productName ASC");
            query.setParameter("inShopId", id);
            return query.getResultList();

        } catch (Exception e) {
            throw new ShopNotFoundException("Shop " + id + " does not exist");
        }

    }

    @Override
    public Product retrieveProductById(Long id) throws ProductNotFoundException {

        Product product = em.find(Product.class, id);

        if (product != null) {
            return product;
        } else {
            throw new ProductNotFoundException("Product " + id + " does not exist");
        }

    }

    @Override
    public Product createProduct(Product product) {

        em.persist(product);
        em.flush();

        return product;

    }

    @Override
    public void updateProduct(Product product) throws ProductNotFoundException {

        if (product.getProductId() != null) {
            Product productToUpdate = retrieveProductById(product.getProductId());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setProductName(product.getProductName());
            productToUpdate.setSkuCode(product.getSkuCode());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setQuantityOnHand(product.getQuantityOnHand());
        } else {
            throw new ProductNotFoundException("ID not provided for product to be updated");
        }

    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        if (id != null) {
            Product productToDelete = retrieveProductById(id);
            em.remove(productToDelete);
        } else {
            throw new ProductNotFoundException("ID not provided for product to be deleted");
        }
    }

    @Override
    public void debitQuantityOnHand(Long productId, Integer quantityToDebit) throws ProductNotFoundException, ProductInsufficientQuantityOnHandException {
        Product productEntity = retrieveProductById(productId);

        if (productEntity.getQuantityOnHand() >= quantityToDebit) {
            productEntity.setQuantityOnHand(productEntity.getQuantityOnHand() - quantityToDebit);
        } else {
            throw new ProductInsufficientQuantityOnHandException("Product " + productEntity.getSkuCode() + " quantity on hand is " + productEntity.getQuantityOnHand() + " versus quantity to debit of " + quantityToDebit);
        }
    }

    @Override
    public void creditQuantityOnHand(Long productId, Integer quantityToCredit) throws ProductNotFoundException {
        Product productEntity = retrieveProductById(productId);
        productEntity.setQuantityOnHand(productEntity.getQuantityOnHand() + quantityToCredit);
    }

    @Override
    public Product retrieveProductBySkuCode(String skuCode) throws ProductNotFoundException {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.skuCode = :inSkuCode");
        query.setParameter("inSkuCode", skuCode);

        try {
            return (Product) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new ProductNotFoundException("Sku Code " + skuCode + " does not exist!");
        }
    }

}
