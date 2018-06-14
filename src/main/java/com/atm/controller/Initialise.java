package com.atm.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.atm.model.Notes;
import com.atm.service.Repository;



@Path("/initialise")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Initialise {
	
	private Repository repository = new Repository();
	
	@POST
	@Path("/initialiseRepository")
	public Response initialiseRepository() {
        
        try {
        	repository.initialise();
        }
        catch (Exception e) {
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
                                                      .entity(e.getMessage()).build());
        }
        return Response.status(Status.OK).build();
    }
	
    @POST
    @Path("/deposit")
    public Response deposit(List<Notes> cash) {
        
    	try {
        	repository.addNotes(cash);
        }
        catch (Exception e) {
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build());
        }
        
        return Response.status(Status.OK).build();
    }

}
