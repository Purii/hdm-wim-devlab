package org.semrep.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationPath("/klasse2")

public class MetaInf {

	 
	 @GET 
	 @Path("/geht") 
	 @Produces("text/plain")
	
	 public String getEmployee() {
	        return "jo";
	    }
	 
	 
	 
	 
	}