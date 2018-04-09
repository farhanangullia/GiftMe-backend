/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ReviewControllerLocal;
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
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllReviewsByShopRsp;
import ws.restful.datamodel.RetrieveAllReviewsRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Review")
public class ReviewResource {

    @Context
    private UriInfo context;

    private final ReviewControllerLocal reviewControllerLocal = lookupReviewControllerLocal();

    /**
     * Creates a new instance of ReviewResource
     */
    public ReviewResource() {
    }

    @Path("retrieveAllReviews")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviews() {
        try {
            RetrieveAllReviewsRsp retrieveAllReviewsRsp = new RetrieveAllReviewsRsp(reviewControllerLocal.retrieveAllReviews());

            return Response.status(Response.Status.OK).entity(retrieveAllReviewsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            System.out.println("ERROR: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllReviewsByShop/{shopId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviewsByShop(@PathParam("shopId") Long shopId) {
        try {
            RetrieveAllReviewsByShopRsp retrieveAllReviewsRsp = new RetrieveAllReviewsByShopRsp(reviewControllerLocal.retrieveAllReviewsByShopId(shopId));

            return Response.status(Response.Status.OK).entity(retrieveAllReviewsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            System.out.println("ERROR: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private ReviewControllerLocal lookupReviewControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewControllerLocal) c.lookup("java:global/GiftMe/GiftMe-ejb/ReviewController!ejb.session.stateless.ReviewControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}