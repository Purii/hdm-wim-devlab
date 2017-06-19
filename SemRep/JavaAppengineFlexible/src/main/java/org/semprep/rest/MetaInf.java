package main.java.org.semprep.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationPath("/v")

public class MetaInf {

	 
	 @GET 
	 @Path("/v") 
	 @Produces("text/plain")
	
	 public String getEmployee() {
	        return "juuu!";
	    }
	 
	 
	 
	 
	}