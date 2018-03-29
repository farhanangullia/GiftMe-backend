/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ProductControllerLocal;
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
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllProductsRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Product")
public class ProductResource {

    @Context
    private UriInfo context;

    private ProductControllerLocal productControllerLocal;

    
    
    /**
     * Creates a new instance of ProductResource
     */
    public ProductResource() {

   productControllerLocal = lookupProductControllerLocal();
    }

    @Path("retrieveAllProducts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProducts() {
        try {
            RetrieveAllProductsRsp retrieveAllProductsRsp = new RetrieveAllProductsRsp(productControllerLocal.retrieveAllProducts());

            return Response.status(Response.Status.OK).entity(retrieveAllProductsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }


    
      
    private ProductControllerLocal lookupProductControllerLocal() 
    {
        try 
        {
            javax.naming.Context c = new InitialContext();
            return (ProductControllerLocal) c.lookup("java:global/GiftMe/GiftMe-ejb/ProductController!ejb.session.stateless.ProductControllerLocal");
        }
        catch (NamingException ne) 
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    

}
