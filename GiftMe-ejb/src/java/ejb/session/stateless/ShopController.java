/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Product;
import entity.Shop;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.DeleteShopException;
import util.exception.ShopNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Stateless
public class ShopController implements ShopControllerLocal {

    @PersistenceContext(unitName = "GiftMe-ejbPU")
    private EntityManager em;

    @EJB
    private ProductControllerLocal productControllerLocal;

    @Override
    public List<Shop> retrieveAllShops() {

        Query query = em.createNamedQuery("selectAllShops");
        return query.getResultList();
    }

    @Override
    public Shop retrieveShopById(Long id) throws ShopNotFoundException {

        Shop shop = em.find(Shop.class, id);

        if (shop != null) {
            return shop;
        } else {
            throw new ShopNotFoundException("Shop " + id + " does not exist");
        }

    }

    @Override
    public Shop createShop(Shop shop) {

        em.persist(shop);
        em.flush();

        return shop;

    }

    @Override
    public void updateShop(Shop shop) throws ShopNotFoundException {

        if (shop.getShopId() != null) {
            Shop shopToUpdate = retrieveShopById(shop.getShopId());
            shopToUpdate.setLocation(shop.getLocation());
            shopToUpdate.setShopName(shop.getShopName());
            //   shopToUpdate.setShopType(shop.getShopType());

        } else {
            throw new ShopNotFoundException("ID not provided for shop to be updated");
        }

    }

    @Override
    public void deleteShop(Long shopId) throws ShopNotFoundException, DeleteShopException {
        Shop shopToRemove = retrieveShopById(shopId);

        // List<Review> reviews = saleTransactionEntityControllerLocal.retrieveSaleTransactionsByStaffId(staffId);
        List<Product> products = productControllerLocal.retrieveAllProductsByShopId(shopId);

        if (products.isEmpty()) {
            em.remove(shopToRemove);
        } else {
            throw new DeleteShopException("There are product(s) or review(s) associated with the staff");
        }
    }

}
