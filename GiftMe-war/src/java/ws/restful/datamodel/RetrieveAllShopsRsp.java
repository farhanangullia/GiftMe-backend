/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Shop;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Farhan Angullia
 */
@XmlRootElement
@XmlType(name = "retrieveAllShopsRsp", propOrder = {
    "shops"
})
public class RetrieveAllShopsRsp {

    private List<Shop> shops;

    public RetrieveAllShopsRsp() {
    }

    public RetrieveAllShopsRsp(List<Shop> shops) {
        this.shops = shops;
    }

    /**
     * @return the shops
     */
    public List<Shop> getShops() {
        return shops;
    }

    /**
     * @param shops the shops to set
     */
    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

}
