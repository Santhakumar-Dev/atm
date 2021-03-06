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

@Path("/withdraw")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Withdraw {
	
	private Repository repository = new Repository();

	    @POST
	    @Path("/withdrawAmount")
	    @Consumes(MediaType.TEXT_PLAIN)
	    public List<Notes> withdraw(long value) {
	        
	        try {
	        	
	            return repository.withdraw(value);
	        }
	        catch (Exception e) {
	            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
	                    .entity(e.getMessage()).build());
	        }
	    }
}
