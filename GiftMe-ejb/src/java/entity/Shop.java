/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import util.enumeration.ShopType;

/**
 *
 * @author Farhan Angullia
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "selectAllShops", query = "SELECT s FROM Shop s")
})
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @Column(length = 32, nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String area;
    
 /*   @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopType shopType;
*/
    @OneToMany(mappedBy = "shop")
    private List<Product> products= new ArrayList<Product>();
    
    @OneToMany(mappedBy = "shop")
    private List<Review> reviews = new ArrayList<Review>();
  /*  
    @OneToOne
    private Address address;
*/
    public Shop() {
    }

    /* public Shop(String shopName, String location, ShopType shopType) {
       
        this.shopName = shopName;
        this.location = location;
        this.shopType = shopType;
     
    }*/
    
    
    public Shop(String shopName, String location, String area) {
       
        this.shopName = shopName;
        this.location = location;
        this.area = area;

     
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shopId != null ? shopId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shop)) {
            return false;
        }
        Shop other = (Shop) object;
        if ((this.shopId == null && other.shopId != null) || (this.shopId != null && !this.shopId.equals(other.shopId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Shop[ id=" + shopId + " ]";
    }

    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the shopType
     */
  /*  public ShopType getShopType() {
        return shopType;
    }
*/
    /**
     * @param shopType the shopType to set
     */
/*    public void setShopType(ShopType shopType) {
        this.shopType = shopType;
    }
*/




    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

}
