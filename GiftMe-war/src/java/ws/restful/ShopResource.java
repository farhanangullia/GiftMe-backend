/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ShopControllerLocal;
import entity.Product;
import entity.Review;
import entity.Shop;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.ShopNotFoundException;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllShopsRsp;
import ws.restful.datamodel.RetrieveShopRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Shop")
public class ShopResource {

    @Context
    private UriInfo context;
    
        private final ShopControllerLocal shopControllerLocal = lookupShopControllerLocal();

    /**
     * Creates a new instance of ShopResource
     */
    public ShopResource() {
    }

    
    @Path("retrieveAllShops")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllShops() {
        try {
            List<Shop> shops = shopControllerLocal.retrieveAllShops();
            
            for(Shop shop:shops)
            {
                for(Product product:shop.getProducts())
                {
                    product.setShop(null);
                }
             /*   for(Review review:shop.getReviews())
                {
                    review.setShop(null);
                }*/
            }
            
         
            RetrieveAllShopsRsp retrieveAllShopsRsp = new RetrieveAllShopsRsp(shops);

            return Response.status(Response.Status.OK).entity(retrieveAllShopsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            System.out.println("ERROR: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    
    
    @Path("retrieveShop/{shopId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveShop(@PathParam("shopId") Long shopId) {
        try {

            
            
            Shop shop = shopControllerLocal.retrieveShopById(shopId);
                            //to fix internal server unmarshalling error
            
                for(Product product:shop.getProducts())
                {
                   
                    product.setShop(null);
                }
              /*  for(Review review:shop.getReviews())
                {
                   review.setShop(null);
                }*/
                            

            
            RetrieveShopRsp retrieveShopRsp = new RetrieveShopRsp(shop);

            return Response.status(Response.Status.OK).entity(retrieveShopRsp).build();

        } catch (ShopNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
    

    
    private ShopControllerLocal lookupShopControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ShopControllerLocal) c.lookup("java:global/GiftMe/GiftMe-ejb/ShopController!ejb.session.stateless.ShopControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
