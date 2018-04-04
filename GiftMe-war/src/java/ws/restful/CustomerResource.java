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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.GetCustomerRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Customer")
public class CustomerResource {

    @Context
    private UriInfo context;

    private final CustomerControllerLocal customerControllerLocal = lookupCustomerControllerLocal();;

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
                                        @QueryParam("password") String password)
                                      
    {
        try
        {
            Customer customer = customerControllerLocal.customerLogin(email, password);
            System.out.println("********** CustomerResource.getCustomer(): Customer " + customer.getEmail() + " login remotely via web service");

            return Response.status(Status.OK).entity(new GetCustomerRsp(customerControllerLocal.retrieveCustomerByEmail(email))).build();
        }
        catch(InvalidLoginCredentialException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
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
