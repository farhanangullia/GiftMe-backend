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
            int distance = (int) dist;

            String arrivalTime = DeliveryDistanceTimeCalculator.getArrivalTime("Tuas", "701 changi coast road");

            customerControllerLocal.createNewCustomer(new Customer("admin", "admin", "mail.giftme@gmail.com", "password", "82222034"));
            initializeConfectionary();
            initializeFlowers();
            initializePlushies();

            System.out.println("DIST IS " + distance);
            System.out.println("TIME IS " + arrivalTime);

            /* 
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
             */
            promotionControllerLocal.createPromotion(new Promotion("5OFF", new BigDecimal("5"), true));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void initializeConfectionary() {

        Shop starbucks = shopControllerLocal.createShop(new Shop("Starbucks ION Mall", "238 Orchard Blvd, ION Orchard", "Central"));
        Shop biscuitBaskets = shopControllerLocal.createShop(new Shop("Biscuit Baskets", "4 Tampines Central 5, Tampines Mall", "East"));
        Shop naturallySuperb = shopControllerLocal.createShop(new Shop("Naturally Superb", "282 Bishan Street 22, Bishan North Shopping Mall", "North"));
        Shop chocoNotLate = shopControllerLocal.createShop(new Shop("ChocoNotLate", "50 Jurong Gateway Rd, Jem", "West"));
        Shop birthdayTreats = shopControllerLocal.createShop(new Shop("Birthday Treats", "1 Harbourfront Walk,, VivoCity", "South"));

        Product confect1 = productControllerLocal.createProduct(new Product(40, "Cookie Paradise Basket", "Fox Chunkie Dark Chocolate Cookie, L’Q Danish Butter Cookies 150g, Caffarel Latte 100g, Butlers Mint Crunch 100g, Redondo Wafers Box 100g, Café Direct Hot Chocolate", "Confectionary", new BigDecimal("35"), "CON001", "../assets/img/products/CON001.jpg", biscuitBaskets));
        Product confect2 = productControllerLocal.createProduct(new Product(40, "Sweet Toothie Basket", "Caffarel Latte Milk 150g, Butterfingers Petticoat 150g, St Dalfour Wild Blueberry 284g, Basilur Bouquet Assorted Green Tea 30g, K. Harrodson Butter Cookie 180g, Arataki Honey 250g", "Confectionary", new BigDecimal("42"), "CON002", "../assets/img/products/CON002.jpg", biscuitBaskets));
        Product confect3 = productControllerLocal.createProduct(new Product(45, "Warm Comfort Basket", "Caffarel Bianco 150g, Butlers White Mixed Berry Chocolate, Basilur BQ Assorted Green Tea 30g, Droste Pastilles 100g, Boncafe Mocca Coffee 50g, K.H Almond Butter Cookies", "Confectionary", new BigDecimal("45"), "CON003", "../assets/img/products/CON003.jpg", biscuitBaskets));
        Product confect4 = productControllerLocal.createProduct(new Product(43, "Dark Berry Basket", "L'Q Danish Butter Cookies 150g, RW Knudsen Just Black Cherry 32oz, Chesapeake Dark Chocolate Pecan, Epicure Straws 100g, Caffarel Bianco 150g, Post Blueberry Cereal 382g", "Confectionary", new BigDecimal("32"), "CON004", "../assets/img/products/CON004.jpg", biscuitBaskets));
        Product confect5 = productControllerLocal.createProduct(new Product(25, "Starbucks Gift Set", "2 Starbucks® Logo Mugs,Walker's Shortbread Cookies (1.4 oz.), Caramel Wafer Cookie (1.38 oz.), Starbucks® Vanilla Almond Biscotti (1.5 oz.),Starbucks® Cafe Verona Ground Coffee (2.5 oz.), Starbucks® House Blend Ground Coffee (2.5 oz.), 1 Starbucks® Green Tea Tazo Tea Box (1.8 oz.), 2 Theo Mini Chocolate Squares", "Confectionary", new BigDecimal("60"), "CON005", "../assets/img/products/CON005.jpg", starbucks));
        Product confect6 = productControllerLocal.createProduct(new Product(25, "Organic Gift Box", "Un Mondo Dry Salsiccia Salame (6 oz.), Olive Oil & Sea Salt Crackers (2 oz.), Cherry Berry Nut Trail Mix (3 oz.), Cheese Crisps (3 oz.), Candy Coated Peanuts (3 oz.), Butter Toffee Peanuts (3 oz.)", "Confectionary", new BigDecimal("39"), "CON006", "../assets/img/products/CON006.jpg", naturallySuperb));
        Product confect7 = productControllerLocal.createProduct(new Product(35, "Chocolate Delight Box", "Chocolate Sea Salt Caramel Popcorn (5 oz.), 2 Milk Chocolate Covered Pretzels, Vanilla Marshmallows (4 oz.), 2 Chocolate Covered Marshmallow Pops, Milk Chocolate Covered Gummy Bears (3 oz.), Dark Chocolate Covered Apricot Bites (3 oz.), Dusted Chocolate Peanut Butter Toffee Balls (3 oz.), Dark Chocolate Almond Bark (3 oz.), Cocoa Biscotti Bites (3 oz.)", "Confectionary", new BigDecimal("33"), "CON007", "../assets/img/products/CON007.jpg", chocoNotLate));
        Product confect8 = productControllerLocal.createProduct(new Product(23, "Mini Chocolate Cheesecake Bites", "12 Dark Chocolate Covered Cheesecake Bites with a Strawberry Puree and Pink Swizzles, 6 Dark Chocolate Covered Cheesecake Bites with a Strawberry Puree and White Swizzles, Certified Kosher Dairy by Orthodox Union", "Confectionary", new BigDecimal("43"), "CON008", "../assets/img/products/CON008.jpg", chocoNotLate));
        Product confect9 = productControllerLocal.createProduct(new Product(23, "9 Birthday Truffles with Hidden Messages", "1 Lemon Cake Truffle Dipped in White and Topped with Lemon Swizzles, 1 Salted Caramel Cake Truffle Dipped in Dark and Topped with Sea Salt, 1 Red Velvet Cake Truffle Dipped in White and Topped with Red Velvet Cake Crumbles, 1 German Chocolate Cake Truffle Dipped in Dark and Topped with Coconut Flakes and Pecans, 1 Confetti Cake Truffle Dipped in White and Topped with Confetti Sprinkles, 1 Peanut Butter Cake Truffle Dipped in Dark and Topped with Peanut Butter Colored Swizzles, 1 Cookies and Cream Cake Truffle Dipped in White and Topped with crushed OREO® Cookies, 1 Chocolate Cake Truffle Dipped in Dark and Topped with Cacao Nibs, 1 Strawberry Cake Truffle Dipped in White and Topped with Pink Shimmer Sugar, Messages Include:, Treat yourself, today’s our day, You don’t look a day over fabulous, You + wine = better with age, You’re the life of every party, Happy Birthday!, You make birthday hats cool, All the world needs is you, You’re the icing on the cake (truffle), You’re more fun than confetti", "Confectionary", new BigDecimal("55"), "CON009", "../assets/img/products/CON009.jpg", birthdayTreats));
        Product confect10 = productControllerLocal.createProduct(new Product(39, "18 Handmade Chocolates", "5 Classic Salted Caramels with Murray River Pink Flake Salt, 5 Vanilla Bean Caramels with Alaea Hawaiian Sea Salt, 4 Honey Caramels with Cyprus Flake Fleur de Sel, 4 Orange Blossom Caramels with Hawaiian Black Lava Salt, Net Weight 11.3 oz.", "Confectionary", new BigDecimal("40"), "CON010", "../assets/img/products/CON010.jpg", chocoNotLate));

        biscuitBaskets.getProducts().add(confect1);
        biscuitBaskets.getProducts().add(confect2);
        biscuitBaskets.getProducts().add(confect3);
        biscuitBaskets.getProducts().add(confect4);
        em.merge(biscuitBaskets);

        starbucks.getProducts().add(confect5);
        em.merge(starbucks);

        naturallySuperb.getProducts().add(confect6);
        em.merge(naturallySuperb);

        chocoNotLate.getProducts().add(confect7);
        chocoNotLate.getProducts().add(confect8);
        chocoNotLate.getProducts().add(confect10);
        em.merge(chocoNotLate);

        birthdayTreats.getProducts().add(confect9);
        em.merge(birthdayTreats);

        Review reviewTest = new Review(5, "Testing review", "Admin", "Good value");
        reviewTest.setShop(chocoNotLate);
        em.persist(reviewTest);

        chocoNotLate.getReviews().add(reviewTest);
        em.merge(chocoNotLate);

        em.flush();

    }

    public void initializeFlowers() {

        Shop angelFlorist = shopControllerLocal.createShop(new Shop("Angel Florist", "8 Admiralty Street, Admirax", "North"));
        Shop floralGarage = shopControllerLocal.createShop(new Shop("Floral Garage", "756 Upper Serangoon Road", "East"));
        Shop fionaTreadwell = shopControllerLocal.createShop(new Shop("Fiona Treadwell", "34 Telok Ayer St", "Central"));

        Product flowers1 = productControllerLocal.createProduct(new Product(40, "Red Roses Round Flower Box", "An alternative to purely roses only collection, we have added a natural touch to achieve the feel of wild nobility for these bright red roses. Accompanied with our green foliages, one will always remember the other personality of roses - thorny beauty.", "Flowers", new BigDecimal("58"), "FLR001", "../assets/img/products/FLR001.jpg", angelFlorist, "Red"));
        Product flowers2 = productControllerLocal.createProduct(new Product(40, "White Roses & Purple Eustoma Bouquet ", "White and purple are a classic colour combination, fit for any occasion from a birthday to a romantic anniversary. White roses represent respect and appreciation, whereas the purple eustoma is regal and majestic as a backdrop. The splash of colour makes for a fantastic backdrop. This bouquet looks fantastic on display at home or in the office.", "Flowers", new BigDecimal("75"), "FLR002", "../assets/img/products/FLR002.jpg", angelFlorist, "White"));
        Product flowers3 = productControllerLocal.createProduct(new Product(40, "Red Roses with Vase", "A beautiful floral arrangement can make waves. Our Red Roses with Vase is a stunning bouquet that is impressive and shows admiration. All 12 stems of Season’s Best roses show up in a clear glass vase, ready for display on a shelf at home or on a desk at the office thanks to Valentine’s Day, an anniversary or a special birthday.", "Flowers", new BigDecimal("45"), "FLR003", "../assets/img/products/FLR003.jpg", angelFlorist, "Red"));
        Product flowers4 = productControllerLocal.createProduct(new Product(40, "99 Roses Bouquet", "When romance calls, it sometimes calls hard. Sometimes, this passion calls for 99 roses, and our 99 Roses Bouquet is the best way to handle that. Our long-stemmed roses are beyond impressive, allowing you to show just how passionately you love that special someone in your life. Send your significant other this giant bouquet to show your abundance of love for Valentine’s Day, an anniversary or a special birthday.", "Flowers", new BigDecimal("300"), "FLR004", "../assets/img/products/FLR004.jpg", angelFlorist, "Red"));
        Product flowers5 = productControllerLocal.createProduct(new Product(40, "Sunflowers with Vase", "Sunflowers are the flower of cheer, and an arrangement of these delightful flowers can brighten someone’s day. Send a beautiful ray of sunshine with our Sunflowers with Vase, an arrangement that includes 10 lovely stems of bold blooms. Flowers arrive in a clear glass vase, perfect for Father’s Day, Mother’s Day or just to wish somebody a good day.", "Flowers", new BigDecimal("35"), "FLR005", "../assets/img/products/FLR005.jpg", fionaTreadwell, "Yellow"));
        Product flowers6 = productControllerLocal.createProduct(new Product(40, "Sunflowers Bouquet", "It is no wonder why sunflowers have been given such a cheery name. The flowers are gorgeous and reminiscent of warm weather and sunshine. Our Sunflowers Bouquet provides a gorgeous splash of color fit to brighten anybody’s day. Roses and seasonal foliage adorn the arrangement, which symbolizes love and devotion. Send this gorgeous gift to commemorate any special occasion.", "Flowers", new BigDecimal("45"), "FLR006", "../assets/img/products/FLR006.jpg", fionaTreadwell, "Yellow"));
        Product flowers7 = productControllerLocal.createProduct(new Product(40, "Fiery Love", "Red is the colour commonly associated with passion, and that’s where this bouquet comes in. With 12 stems of gorgeous roses in tow, this bouquet sends a strong message. The deep red meshes with the alstroemeria to create a seasonal look that is ideal for the Christmas holidays or Valentine’s Day. Of course, it is also romantic enough to present any time of the year.", "Flowers", new BigDecimal("100"), "FLR007", "../assets/img/products/FLR007.jpg", floralGarage, "Red"));
        Product flowers8 = productControllerLocal.createProduct(new Product(40, "Pink Lady", "Want to send roses without the backdrop of foliage? This simple bouquet allows you to do just that. This bouquet offers 36 stems of pink and white roses, each one luscious and eye-popping. Let somebody special know that you love them with this fantastic bouquet, which is ready for display when it arrives. It is ideal for a romantic celebration, including an anniversary or Valentine’s Day.", "Flowers", new BigDecimal("100"), "FLR008", "../assets/img/products/FLR008.jpg", floralGarage, "Pink"));
        Product flowers9 = productControllerLocal.createProduct(new Product(40, "Champagne Roses Bouquet", "Although not a striking colour, champagne roses carry strong meanings of fascination, desire, passion and enthusiasm. On the other hand, it also represents love and tenderness. Got someone you admire and determined to make them yours? This subtle yet beautiful bouquet with 12 stalks of roses is wonderful way to say, “Let’s get together.”", "Flowers", new BigDecimal("70"), "FLR009", "../assets/img/products/FLR009.jpg", floralGarage, "Champagne"));
        Product flowers10 = productControllerLocal.createProduct(new Product(40, "Mini Hydragnea Bouquet", "The hydrangea is an ornate flower with unique, woody stems. The lacy nature of the flower means that the blooms are a special delight for the special person who receives them. Hydrangeas in every color, ranging from purple to pink are enchanting. Sending this bouquet of hydrangeas to somebody special sends the message of gratefulness and understanding in many cases, creating a heartfelt message. This flower is also commonly given as a fourth wedding anniversary gift, making it a romantic choice.", "Flowers", new BigDecimal("65"), "FLR010", "../assets/img/products/FLR010.jpg", floralGarage, "Purple"));

        angelFlorist.getProducts().add(flowers1);
        angelFlorist.getProducts().add(flowers2);
        angelFlorist.getProducts().add(flowers3);
        angelFlorist.getProducts().add(flowers4);
        em.merge(angelFlorist);

        fionaTreadwell.getProducts().add(flowers5);
        fionaTreadwell.getProducts().add(flowers6);
        em.merge(fionaTreadwell);

        floralGarage.getProducts().add(flowers7);
        floralGarage.getProducts().add(flowers8);
        floralGarage.getProducts().add(flowers9);
        floralGarage.getProducts().add(flowers10);
        em.merge(floralGarage);

    }

    public void initializePlushies() {

        Shop plushRUs = shopControllerLocal.createShop(new Shop("Plush R Us", "3155 Commonwealth Ave W, The Clementi Mall", "West"));
        Shop teddyLodge = shopControllerLocal.createShop(new Shop("Teddy Lodge", "1 Wallich Street, Guoco Tower", "Central"));

        Product plushies1 = productControllerLocal.createProduct(new Product(40, "Delightful Teddy", "Let one and all delight on this one cuddly bear that is surprisingly appealing whatever day!", "Plushies", new BigDecimal("20"), "PSH001", "../assets/img/products/PSH001.jpg", plushRUs));
        Product plushies2 = productControllerLocal.createProduct(new Product(40, "Cheery Teddy", "Compel your darling’s day to be extra cheerful by giving her Christie, this enthusiastic bear!", "Plushies", new BigDecimal("25"), "PSH002", "../assets/img/products/PSH002.jpg", plushRUs));
        Product plushies3 = productControllerLocal.createProduct(new Product(40, "Married Bears", "This pair of couple cuddly bears is perfect as the gift for the newlywed! ", "Plushies", new BigDecimal("30"), "PSH003", "../assets/img/products/PSH003.jpg", plushRUs));
        Product plushies4 = productControllerLocal.createProduct(new Product(40, "Comfort Bear", "Wish your loved ones a speedy recovery with this Get Well Soon cuddly bear!", "Plushies", new BigDecimal("28"), "PSH004", "../assets/img/products/PSH004.jpg", plushRUs));
        Product plushies5 = productControllerLocal.createProduct(new Product(40, "Basic Bear", "All the soft toy lovers out there are bound to love some cuddly bears! What’s more, the arms and legs of these bears are sewn in such a way that they are movable!", "Plushies", new BigDecimal("26"), "PSH005", "../assets/img/products/PSH005.jpg", teddyLodge));
        Product plushies6 = productControllerLocal.createProduct(new Product(40, "Knitted Sheep", "This handmade knitted plushy is sure to melt some hearts. Surprise your loved ones with this snuggly sheep today!", "Plushies", new BigDecimal("30"), "PSH006", "../assets/img/products/PSH006.jpg", teddyLodge));
        Product plushies7 = productControllerLocal.createProduct(new Product(40, "Furry Bear", "We all need a furry teddy bear in our soft toy collection. This huggable soft toy is definitely a hot favourite!", "Plushies", new BigDecimal("30"), "PSH007", "../assets/img/products/PSH007.jpg", teddyLodge));
        Product plushies8 = productControllerLocal.createProduct(new Product(40, "Happy Kangaroo", "These handmade knitted kangaroos come in a pair. Bonus- the baby kangaroo fits in the pouch of mama kangaroo! ", "Plushies", new BigDecimal("35"), "PSH008", "../assets/img/products/PSH008.jpg", teddyLodge));
        Product plushies9 = productControllerLocal.createProduct(new Product(40, "Red polka-dot bear", "Let this adorable cuddly buddy bring a smile into your love ones face. What’s more, the arms and legs of these bears are sewn in such a way that they are movable!", "Plushies", new BigDecimal("23"), "PSH009", "../assets/img/products/PSH009.jpg", teddyLodge));
        Product plushies10 = productControllerLocal.createProduct(new Product(40, "Floral Bear", "All the soft toy lovers out there are bound to love some cuddly bears! What’s more, the arms and legs of these bears are sewn in such a way that they are movable!", "Plushies", new BigDecimal("23"), "PSH010", "../assets/img/products/PSH010.jpg", teddyLodge));

        plushRUs.getProducts().add(plushies1);
        plushRUs.getProducts().add(plushies2);
        plushRUs.getProducts().add(plushies3);
        plushRUs.getProducts().add(plushies4);
        em.merge(plushRUs);

        teddyLodge.getProducts().add(plushies5);
        teddyLodge.getProducts().add(plushies6);
        teddyLodge.getProducts().add(plushies7);
        teddyLodge.getProducts().add(plushies8);
        teddyLodge.getProducts().add(plushies9);
        teddyLodge.getProducts().add(plushies10);
        em.merge(teddyLodge);

    }

}
