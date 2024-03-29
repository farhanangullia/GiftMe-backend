/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ProductControllerLocal;
import entity.Product;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.ProductNotFoundException;
import util.exception.ShopNotFoundException;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllProductsByShopRsp;
import ws.restful.datamodel.RetrieveAllProductsRsp;
import ws.restful.datamodel.RetrieveProductRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Product")
public class ProductResource {

    @Context
    private UriInfo context;

    private final ProductControllerLocal productControllerLocal = lookupProductControllerLocal();

    /**
     * Creates a new instance of ProductResource
     */
    public ProductResource() {

    }

    @Path("retrieveAllProducts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProducts() {
        try {
            List<Product> products = productControllerLocal.retrieveAllProducts();

            for (Product product : products) {
                product.getShop().getProducts().clear();
                product.getShop().getReviews().clear();
            }

            RetrieveAllProductsRsp retrieveAllProductsRsp = new RetrieveAllProductsRsp(products);

            return Response.status(Response.Status.OK).entity(retrieveAllProductsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveProduct/{productId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProduct(@PathParam("productId") Long productId) {
        try {

            Product product = productControllerLocal.retrieveProductById(productId);
            //  product.setShop(null);                     //to fix internal server unmarshalling error
            product.getShop().getProducts().clear();
            product.getShop().getReviews().clear();

            RetrieveProductRsp retrieveProductRsp = new RetrieveProductRsp(product);

            return Response.status(Response.Status.OK).entity(retrieveProductRsp).build();

        } catch (ProductNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllProductsByShop/{shopId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProductsByShop(@PathParam("shopId") Long shopId) {
        try {

            List<Product> products = productControllerLocal.retrieveAllProductsByShopId(shopId);

            for (Product product : products) {
                product.getShop().getProducts().clear();
                product.getShop().getReviews().clear();
            }

            RetrieveAllProductsByShopRsp retrieveAllProductsByShopRsp = new RetrieveAllProductsByShopRsp(products);

            return Response.status(Response.Status.OK).entity(retrieveAllProductsByShopRsp).build();

        } catch (ShopNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private ProductControllerLocal lookupProductControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProductControllerLocal) c.lookup("java:global/GiftMe/GiftMe-ejb/ProductController!ejb.session.stateless.ProductControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
