/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CustomerControllerLocal;
import ejb.session.stateless.ProductControllerLocal;
import ejb.session.stateless.PromotionControllerLocal;
import ejb.session.stateless.ReviewControllerLocal;
import ejb.session.stateless.ShopControllerLocal;
import entity.Customer;
import entity.Product;
import entity.Promotion;
import entity.Review;
import entity.Shop;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.common.DeliveryDistanceTimeCalculator;
import util.common.RandomGenerator;
import util.email.EmailManager;
import util.enumeration.ShopType;
import util.exception.CustomerNotFoundException;

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

    @EJB
    private CustomerControllerLocal customerControllerLocal;
    @EJB
    private ProductControllerLocal productControllerLocal;
    @EJB
    private ShopControllerLocal shopControllerLocal;
    @EJB
    private PromotionControllerLocal promotionControllerLocal;
    @EJB
    private ReviewControllerLocal reviewControllerLocal;

    @PostConstruct
    public void postConstruct() {
        try {
   //  sendEmail();
            customerControllerLocal.retrieveCustomerByEmail("mail.giftme@gmail.com");
        

        } catch (CustomerNotFoundException ex) {
            initializeData();
        } catch (Exception e) {

        }

    }

  
    public void initializeData() {
        try {
            
          long dist = DeliveryDistanceTimeCalculator.getDriveDist("kent ridge hall", "clementi mall");
          int distance = (int)dist;
          
          String arrivalTime = DeliveryDistanceTimeCalculator.getArrivalTime("Tuas", "701 changi coast road");
        
System.out.println("DIST IS " + distance);
System.out.println("TIME IS " + arrivalTime);
            Product product = new Product();
            product.setProductName("Roses");
            product.setCategory("Flowers");
            product.setDescription("Red roses for your loved ones");
            product.setPrice(new BigDecimal("20"));
            product.setQuantityOnHand(50);
            product.setSkuCode("PROD005");
            product.setImgPath("../assets/img/products/PROD005.png");
            product.setColour("Red");

            Shop shop1 = shopControllerLocal.createShop(new Shop("Kent Ridge Flora", "Kent Ridge MRT", "South West"));
            
            product.setShop(shop1);
            em.persist(product);
            em.flush();
            shop1.getProducts().add(product);
            em.merge(shop1);

            Shop shop = shopControllerLocal.createShop(new Shop("PlushRUs Store", "Star Vista Mall", "South West"));
            
            customerControllerLocal.createNewCustomer(new Customer("admin", "admin", "mail.giftme@gmail.com", "password", "82222034"));
            Product product2 = productControllerLocal.createProduct(new Product(40, "Teddy Bear", "Soft and cute teddy bear", "Plushies", new BigDecimal("6"), "PROD006", "../assets/img/products/PROD006.png", shop));

            shop.getProducts().add(product2);
            em.merge(shop);

            promotionControllerLocal.createPromotion(new Promotion("5OFF", new BigDecimal("5"), true));
            Review reviewTest = new Review(5, "Testing review", "Admin","Good value");
            reviewTest.setShop(shop);
            em.persist(reviewTest);

            shop.getReviews().add(reviewTest);
            em.merge(shop);

            em.flush();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}
