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
            sendEmail();
            customerControllerLocal.retrieveCustomerByEmail("mail.giftme@gmail.com");
            
        } catch (CustomerNotFoundException ex) {
            initializeData();
        }
        
    }
    
    public void sendEmail() {

        //    String encryptedPassword 
        EmailManager emailManager = new EmailManager("e0032247", "<MY PASSWORD>");    //replace e0032247 with ur SOC unix acc and <MY PASSWORD> with ur UNIX acc password
        Boolean result = emailManager.email("mail.giftme@gmail.com", "<EMAIL TO>"); //replace <EMAIL TO> with the email of the receipient
        
        if (result) {
            // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email sent successfully", null));
            System.out.println("Email sent successfully");
        } else {
            
            System.out.println("An error has occured while sending email");

            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while sending email", null));
        }
    }
    
    public void initializeData() {
        try {
            
            Product product = new Product();
            product.setProductName("Roses");
            product.setCategory("Flowers");
            product.setDescription("Red roses for your loved ones");
            product.setPrice(new BigDecimal("20"));
            product.setQuantityOnHand(50);
            product.setSkuCode("PROD005");
            product.setImgPath("../assets/img/products/PROD005.png");
            
            Shop shop1 = shopControllerLocal.createShop(new Shop("Kent Ridge Flora", "South West", ShopType.PREMIUM));
            product.setShop(shop1);
            em.persist(product);
            em.flush();
            

        

    
            Shop shop = shopControllerLocal.createShop(new Shop("PlushRUs Store", "South West", ShopType.NORMAL));
            customerControllerLocal.createNewCustomer(new Customer("admin", "admin", "mail.giftme@gmail.com", "password", "82222034"));
            Product product2 = productControllerLocal.createProduct(new Product(40, "Teddy Bear", "Soft and cute teddy bear", "Plushies", new BigDecimal("6"), "PROD006", "../assets/img/products/PROD006.png", shop));
       
            
            /*      //after linking shops to product, restful will give error 500
       shop.getProducts().add(product2);
        em.merge(shop);
*/    

            promotionControllerLocal.createPromotion(new Promotion("5OFF", new BigDecimal("5"), true));
            Review reviewTest = new Review(5, "Testing review", "Admin");
            reviewTest.setShop(shop);
            em.persist(reviewTest);
          /*  List<Review> reviews = new ArrayList();            
            reviews.add(reviewTest);
            shop.setReviews(reviews);
            em.merge(shop);
            
            em.flush();*/
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }    
    
}
