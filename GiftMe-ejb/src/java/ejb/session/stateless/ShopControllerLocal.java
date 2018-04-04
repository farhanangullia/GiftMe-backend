/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Shop;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteShopException;
import util.exception.ShopNotFoundException;

/**
 *
 * @author Farhan Angullia
 */
@Local
public interface ShopControllerLocal {

    public List<Shop> retrieveAllShops();

    public Shop createShop(Shop shop);

    public Shop retrieveShopById(Long id) throws ShopNotFoundException;

    public void updateShop(Shop shop) throws ShopNotFoundException;

    public void deleteShop(Long shopId) throws ShopNotFoundException, DeleteShopException;
    
}
