/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Farhan Angullia
 */
@Entity
public class LineProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineProductId;
    

    

    
        @Column(precision = 11, scale = 2)
    private BigDecimal subTotal;
    
    @ManyToOne
    private Cart cart;
    
    @OneToOne
    private Product product;

    public LineProduct() {
    }

    public LineProduct(Long lineProductId, BigDecimal subTotal, Cart cart, Product product) {
        this.lineProductId = lineProductId;
        this.subTotal = subTotal;
        this.cart = cart;
        this.product = product;
    }
    
    

    public Long getLineProductId() {
        return lineProductId;
    }

    public void setLineProductId(Long lineProductId) {
        this.lineProductId = lineProductId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineProductId != null ? lineProductId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LineProduct)) {
            return false;
        }
        LineProduct other = (LineProduct) object;
        if ((this.lineProductId == null && other.lineProductId != null) || (this.lineProductId != null && !this.lineProductId.equals(other.lineProductId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LineProduct[ id=" + lineProductId + " ]";
    }

    

    /**
     * @return the subTotal
     */
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the subTotal to set
     */
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * @param cart the cart to set
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
    
}
