/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ReviewControllerLocal;
import entity.Review;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import util.exception.CreateReviewException;
import ws.restful.datamodel.CreateReviewReq;
import ws.restful.datamodel.CreateReviewRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllReviewsByShopRsp;
import ws.restful.datamodel.RetrieveAllReviewsRsp;
import ws.restful.datamodel.RetrieveReviewRsp;

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

            List<Review> reviews = reviewControllerLocal.retrieveAllReviews();

            for (Review review : reviews) {
                review.getShop().getReviews().clear();
                review.getShop().getProducts().clear();

            }

            RetrieveAllReviewsRsp retrieveAllReviewsRsp = new RetrieveAllReviewsRsp(reviews);

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
System.out.println("HERE shop rev");
            List<Review> reviews = reviewControllerLocal.retrieveAllReviewsByShopId(shopId);

      System.out.println("HERE shop rev 2");
           for (Review review : reviews) {
                review.getShop().getReviews().clear();
                review.getShop().getProducts().clear();
                

            }
           
           RetrieveAllReviewsByShopRsp retrieveAllReviewsRsp = new RetrieveAllReviewsByShopRsp(reviews);

            return Response.status(Response.Status.OK).entity(retrieveAllReviewsRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            System.out.println("ERROR: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReview(JAXBElement<CreateReviewReq> jaxbCreateReviewReq) {

        if ((jaxbCreateReviewReq != null) && (jaxbCreateReviewReq.getValue() != null)) {
            try {

                CreateReviewReq createReviewReq = jaxbCreateReviewReq.getValue();

                System.out.println("HEREEE");
                 Long shopId = createReviewReq.getShopId();
                 System.out.println(shopId);
                 System.out.println("HEREEE");
     Review review = reviewControllerLocal.createShopReview(createReviewReq.getReview(), shopId);
//Review review = reviewControllerLocal.createReview(createReviewReq.getReview());
                CreateReviewRsp createReviewRsp = new CreateReviewRsp(review.getReviewId());

                return Response.status(Response.Status.OK).entity(createReviewRsp).build();
            } catch (CreateReviewException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create review request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
     @Path("retrieveReview/{reviewId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveReview(
                                        @PathParam("reviewId") Long reviewId)
    {
        try
        {
            
            Review review = reviewControllerLocal.retrieveReviewById(reviewId);
           review.getShop().getProducts().clear();
            review.getShop().getReviews().clear();
           
            return Response.status(Status.OK).entity(new RetrieveReviewRsp(review)).build();
        }
       
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
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
