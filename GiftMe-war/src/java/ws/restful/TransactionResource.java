/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.TransactionControllerLocal;
import entity.Transaction;
import entity.TransactionLineItem;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import util.exception.CreateDeliveryException;
import util.exception.CreateNewTransactionException;
import util.exception.CustomerNotFoundException;
import util.exception.PromotionNotFoundException;
import util.exception.TransactionNotFoundException;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RemoteCheckoutReq;
import ws.restful.datamodel.RemoteCheckoutRsp;
import ws.restful.datamodel.RetrieveAllTransactionsByEmailRsp;
import ws.restful.datamodel.RetrieveTransactionByDeliveryCodeRsp;
import ws.restful.datamodel.RetrieveTransactionRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Transaction")
public class TransactionResource {

    @Context
    private UriInfo context;

    private final TransactionControllerLocal transactionControllerLocal = lookupTransactionControllerLocal();

    /**
     * Creates a new instance of TransactionResource
     */
    public TransactionResource() {
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response remoteCheckout(JAXBElement<RemoteCheckoutReq> jaxbRemoteCheckoutReq) {
        if ((jaxbRemoteCheckoutReq != null) && (jaxbRemoteCheckoutReq.getValue() != null)) {
            try {
                RemoteCheckoutReq remoteCheckoutReq = jaxbRemoteCheckoutReq.getValue();

                String promoCode = remoteCheckoutReq.getPromoCode();
                String customerAddress = remoteCheckoutReq.getCustomerAddress();
                String shopAddress = remoteCheckoutReq.getShopAddress();
                System.out.println("PROMO ID" + promoCode);
                String email = remoteCheckoutReq.getEmail();

                if (remoteCheckoutReq.getRemoteCheckoutLineItems().isEmpty()) {
                    System.out.println("EMPTY");
                }

                if (remoteCheckoutReq.getRemoteCheckoutLineItems() == null) {
                    System.out.println("NULLLLL");
                }

                System.out.println(email);
                

               // Transaction transaction = transactionControllerLocal.createNewTransactionFromRemoteCheckoutRequest(promoCode, remoteCheckoutReq.getRemoteCheckoutLineItems(), email, customerAddress, shopAddress);
Transaction transaction = transactionControllerLocal.createNewTransactionFromRemoteCheckoutRequest(promoCode, remoteCheckoutReq.getRemoteCheckoutLineItems(), email, customerAddress, shopAddress);
         //      Transaction newTransaction = new Transaction();
          //     Transaction transaction = transactionControllerLocal.createNewTransaction(newTransaction);
                transaction.getDelivery().setTransaction(null);
                
               

                /* for(TransactionLineItem transactionLineItem: transaction.getDelivery().getTransaction().getTransactionLineItems()){
           transactionLineItem.getProduct().getShop().getProducts().clear();
                    transactionLineItem.getProduct().getShop().getReviews().clear();
                   
                        }
                 */
                for (TransactionLineItem transactionLineItem : transaction.getTransactionLineItems()) {
                    transactionLineItem.getProduct().getShop().getProducts().clear();
                    transactionLineItem.getProduct().getShop().getReviews().clear();

                }
                /*  transaction.getTransactionLineItems().get(0).getProduct().getShop().getProducts().clear();
       transaction.getTransactionLineItems().get(0).getProduct().getShop().getReviews().clear();
       
        transaction.getTransactionLineItems().get(1).getProduct().getShop().getProducts().clear();
       transaction.getTransactionLineItems().get(1).getProduct().getShop().getReviews().clear();
                 */
                //  transaction.getTransactionLineItems().get(1).setProduct(null);

                transaction.getCustomer().setMobileNumber(null);
                transaction.getCustomer().setPassword(null);
                transaction.getCustomer().setSalt(null);
                RemoteCheckoutRsp remoteCheckoutRsp = new RemoteCheckoutRsp(transaction);
                
                 transaction.getCustomer().getTransactions().clear();

                return Response.status(Response.Status.OK).entity(remoteCheckoutRsp).build();
            } catch (  Exception ex) {
                System.out.println(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            
         
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid remote checkout request").build();
        }
    }

    @Path("retrieveTransaction/{transactionId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTransaction(@PathParam("transactionId") Long transactionId) {
        try {

            Transaction transaction = transactionControllerLocal.retrieveTransactionByTransactionId(transactionId);
            transaction.getDelivery().setTransaction(null);

            for (TransactionLineItem transactionLineItem : transaction.getTransactionLineItems()) {
                transactionLineItem.getProduct().getShop().getProducts().clear();
                transactionLineItem.getProduct().getShop().getReviews().clear();

            }
            transaction.getCustomer().setMobileNumber(null);
            transaction.getCustomer().setPassword(null);
            transaction.getCustomer().setSalt(null);

            transaction.getCustomer().getTransactions().clear();
            RetrieveTransactionRsp retrieveTransactionRsp = new RetrieveTransactionRsp(transaction);

            return Response.status(Response.Status.OK).entity(retrieveTransactionRsp).build();

        } catch (TransactionNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllTransactionsByEmail")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTransactionsByEmail(@QueryParam("email") String email) {
        try {

            List<Transaction> transactions = transactionControllerLocal.retrieveTransactionsByCustomerEmail(email);

            for (Transaction transaction : transactions) {
                transaction.getDelivery().setTransaction(null);

                for (TransactionLineItem transactionLineItem : transaction.getTransactionLineItems()) {
                    transactionLineItem.getProduct().getShop().getProducts().clear();
                    transactionLineItem.getProduct().getShop().getReviews().clear();

                }

                transaction.setCustomer(null); //neater so json wont display customer object
            }

            RetrieveAllTransactionsByEmailRsp retrieveAllTransactionsByEmailRsp = new RetrieveAllTransactionsByEmailRsp(transactions);

            return Response.status(Response.Status.OK).entity(retrieveAllTransactionsByEmailRsp).build();

        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveTransactionByDeliveryCode")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTransactionByDeliveryCode(@QueryParam("deliveryCode") String deliveryCode) {
        try {

           // List<Transaction> transactions = transactionControllerLocal.retrieveTransactionsByCustomerEmail(email);

           
           
           Transaction transaction = transactionControllerLocal.retrieveTransactionByDeliveryCode(deliveryCode);
            transaction.getDelivery().setTransaction(null);

            for (TransactionLineItem transactionLineItem : transaction.getTransactionLineItems()) {
                transactionLineItem.getProduct().getShop().getProducts().clear();
                transactionLineItem.getProduct().getShop().getReviews().clear();

            }
            transaction.getCustomer().setMobileNumber(null);
            transaction.getCustomer().setPassword(null);
            transaction.getCustomer().setSalt(null);

            RetrieveTransactionByDeliveryCodeRsp retrieveTransactionByDeliveryCodeRsp = new RetrieveTransactionByDeliveryCodeRsp(transaction);
           
           
           
     
            return Response.status(Response.Status.OK).entity(retrieveTransactionByDeliveryCodeRsp).build();

        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    
    
    
    
    
    

    private TransactionControllerLocal lookupTransactionControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TransactionControllerLocal) c.lookup("java:global/GiftMe/GiftMe-ejb/TransactionController!ejb.session.stateless.TransactionControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
