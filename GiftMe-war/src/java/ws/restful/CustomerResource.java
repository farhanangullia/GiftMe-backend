/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.CustomerControllerLocal;
import entity.Customer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import util.exception.CreateCustomerException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;
import ws.restful.datamodel.CreateCustomerReq;
import ws.restful.datamodel.CreateCustomerRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.GetCustomerRsp;
import ws.restful.datamodel.UpdateCustomerPasswordReq;
import ws.restful.datamodel.UpdateCustomerReq;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Customer")
public class CustomerResource {
    
    @Context
    private UriInfo context;
    
    private final CustomerControllerLocal customerControllerLocal = lookupCustomerControllerLocal();
    
    ;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }
    
    @Path("getCustomer")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@QueryParam("email") String email,
            @QueryParam("password") String password) {
        try {
            Customer customer = customerControllerLocal.customerLogin(email, password);
            System.out.println("********** CustomerResource.getCustomer(): Customer " + customer.getEmail() + " login remotely via web service");
            
            customer.setPassword(null);       //for security purposes on client side
            customer.setSalt(null);
            
            customer.getTransactions().clear();
           
            
            return Response.status(Status.OK).entity(new GetCustomerRsp(customer)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(JAXBElement<CreateCustomerReq> jaxbCreateCustomerReq) {
        
        System.err.println("*********** HERE 1");
        
        if ((jaxbCreateCustomerReq != null) && (jaxbCreateCustomerReq.getValue() != null)) {
            try {
                
                System.err.println("*********** HERE 2");
                
                CreateCustomerReq createCustomerReq = jaxbCreateCustomerReq.getValue();
                //createCustomerReq.getCustomer().setTransactions(null);
                Long id = customerControllerLocal.createNewCustomer(createCustomerReq.getCustomer());
                System.err.println("********  id " + id);
                CreateCustomerRsp createCustomerRsp = new CreateCustomerRsp(id);
                
                System.err.println("*********** HERE 3");
                
                return Response.status(Response.Status.OK).entity(createCustomerRsp).build();
            } catch (CreateCustomerException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create customer request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("updateCustomer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(JAXBElement<UpdateCustomerReq> jaxbUpdateCustomerReq) {
        if ((jaxbUpdateCustomerReq != null) && (jaxbUpdateCustomerReq.getValue() != null)) {
            try {
                UpdateCustomerReq updateCustomerReq = jaxbUpdateCustomerReq.getValue();
                
                customerControllerLocal.updateCustomer(updateCustomerReq.getCustomer());
                
                
                return Response.status(Response.Status.OK).build();
            } catch (UpdateCustomerException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update customer request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @Path("updateCustomerPassword")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomerPassword(JAXBElement<UpdateCustomerPasswordReq> jaxbUpdateCustomerPasswordReq) {
        if ((jaxbUpdateCustomerPasswordReq != null) && (jaxbUpdateCustomerPasswordReq.getValue() != null)) {
            try {
                UpdateCustomerPasswordReq updateCustomerPasswordReq = jaxbUpdateCustomerPasswordReq.getValue();
                
                customerControllerLocal.updateCustomerPassword(updateCustomerPasswordReq.getCustomer());
                
                return Response.status(Response.Status.OK).build();
            } catch (UpdateCustomerException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update customer request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    private CustomerControllerLocal lookupCustomerControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerControllerLocal) c.lookup("java:global/GiftMe/GiftMe-ejb/CustomerController!ejb.session.stateless.CustomerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
