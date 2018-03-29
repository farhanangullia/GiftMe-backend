/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.GetTimeRsp;

/**
 * REST Web Service
 *
 * @author Farhan Angullia
 */
@Path("Time")
public class TimeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TimeResource
     */
    public TimeResource() {
    }

    /**
     * Retrieves representation of an instance of ws.restful.TimeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTime() {
       try
	{
		GetTimeRsp getTimeRsp = new GetTimeRsp(new Date().toString());

		return Response.status(Status.OK).entity(getTimeRsp).build();
	}
	catch(Exception ex)
	{
		ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
	}
    }


   
}
