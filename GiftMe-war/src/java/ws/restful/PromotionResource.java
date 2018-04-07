/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.PromotionControllerLocal;
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
import util.exception.PromotionNotFoundException;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllPromotionsRsp;
import ws.restful.datamodel.RetrievePromotionRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Promotion")
public class PromotionResource {

    @Context
    private UriInfo context;

    private final PromotionControllerLocal promotionControllerLocal = lookupPromotionControllerLocal();

    /**
     * Creates a new instance of PromotionResource
     */
    public PromotionResource() {

    }

    @Path("retrievePromotion/{promoCode}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePromotion(@PathParam("promoCode") String promoCode) {
        try {

            RetrievePromotionRsp retrievePromotionRsp = new RetrievePromotionRsp(promotionControllerLocal.retrievePromotionByPromoCode(promoCode));

            return Response.status(Response.Status.OK).entity(retrievePromotionRsp).build();

        } catch (PromotionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    
     @Path("retrieveAllPromotions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPromotions() {
        try {
            RetrieveAllPromotionsRsp retrieveAllPromotionsRsp = new RetrieveAllPromotionsRsp(promotionControllerLocal.retrieveAllPromotions());

            return Response.status(Response.Status.OK).entity(retrieveAllPromotionsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            System.out.println("ERROR: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    

    private PromotionControllerLocal lookupPromotionControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PromotionControllerLocal) c.lookup("java:global/GiftMe/GiftMe-ejb/PromotionController!ejb.session.stateless.PromotionControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
