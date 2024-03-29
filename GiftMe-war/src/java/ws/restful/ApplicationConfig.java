/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Farhan Angullia
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.restful.CustomerResource.class);
        resources.add(ws.restful.ProductResource.class);
        resources.add(ws.restful.PromotionResource.class);
        resources.add(ws.restful.ReviewResource.class);
        resources.add(ws.restful.ShopResource.class);
        resources.add(ws.restful.TimeResource.class);
        resources.add(ws.restful.TransactionResource.class);
    }

}
