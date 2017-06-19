package main.java.org.semprep.rest;

 
	 

	import javax.ws.rs.PathParam;

	import java.util.LinkedHashMap;
	import java.util.List;

	import javax.ws.rs.Consumes;
	import javax.ws.rs.POST;
	import javax.ws.rs.core.Response;
	
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.Produces;
	import javax.ws.rs.core.MediaType;

	 
 	import org.apache.jena.query.QueryExecution;
	import org.apache.jena.query.QueryExecutionFactory;
 	import org.apache.jena.query.QuerySolution;
	import org.apache.jena.query.ResultSet;
	import org.apache.jena.rdf.model.RDFNode;

 	import com.google.appengine.labs.repackaged.org.json.JSONObject;
 
	@Path("/rest")
	public class Ressourcee {
	 
		@GET
	    @Produces(MediaType.TEXT_PLAIN)
	    public String sayPlainTextHello() {
	        return "Hello Jersey";
	    }
		
	 @GET
	 @Produces("text/plain")
	 @Path("/m") 
	 public String getEmployee() {
		 
		 String test = "";
		 
		 //String sz1 = new awSzenario1().getDocumentsByProjectName();
	        return "Hello Appengine:";
	    }
	 
	 /*
	  * Diese Methode nimmt eine GET-Anfrage von dem Google App Script Addin entgegen. 
	  * Ziel der Methode ist es eine Verbindung zum Fusekiserver zur A-Box herzustellen. 
	  * Die Abfrage ist durch SPARQL realisiert. Als Antw schickt die Methode eine Liste der gefunden Projekte.
	  * Diese werden dann in der GUI im Google App Script in einem Drop-Down-Liste zu Anzeige gebracht.
	  * 
	  * */
	 
	 @GET
	 @Produces("application/json")
	 @Path("/getdoccategory") 
	 public String getCategoryOfDocument() {
		 
	        return "Hello Projects:";
	    }
	 
	 
	 @GET
	 @Produces("application/json")
	 @Path("/getprojectphase") 
	 public String getProjectPhase() {
		 
	        return "Hello Projects:";
	    }
	 
	 
	 @GET
	 @Produces("application/json")
	 @Path("/getprojects") 
	 public Response getProjects()  {
			JSONObject jsonObject = new JSONObject();

			String jsonInString = "ggggg";
			 
			LinkedHashMap<String, String> projektHashMap = new LinkedHashMap<String, String>();

			try {
				 
			//	ontologyModel.read(fileReader, null, "TTL");

				// initialisiere Variablen
				// sparql
				String sparql = "";

				// projekt
				String projektNameStr = "";


				// initialisiere Objekte
				// person

				sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> " + "SELECT DISTINCT ?Projekte "
						+ "WHERE { " + "?x ontology:Projekt_Name ?Projekte . " + "} " + "ORDER BY ?Projekte";

				if (sparql != "") {

					// Initialisierung und Ausführung einer SPARQL-Query
					QueryExecution queryExecution =
							QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query",
									sparql);

					// Initialisierung von Resultset für Ergebniswerte der
					// SPARQL-Query
					ResultSet resultSet = queryExecution.execSelect();
					
				//	System.out.println(resultSet.(2).toString());

					// initialisiere Variablen
					String splitResult = "";
					int indexOfToSplitCharacter;

					for (int i = 0; resultSet.hasNext() == true; i++) {
						QuerySolution querySolution = resultSet.nextSolution();
						projektHashMap.put("Projekt_" + i, "");
						
						for (int j = 0; j < resultSet.getResultVars().size(); j++) {
							String results = resultSet.getResultVars().get(j).toString();
							RDFNode rdfNode = querySolution.get(results);

	 						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
							splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

							
								// einmaliges befüllen der nachfolgenden Werte
								if (((projektHashMap.get("Projekt_" + i) == "") == true)) {

									switch (results) {
									case "Projekte":
										projektNameStr = splitResult;
										projektHashMap.put("Projekt_" + i, projektNameStr);
										break;
									}

								}

						}

					}

					queryExecution.close();

				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// drucke alles im richTokenHashMap aus
			for (String key : projektHashMap.keySet()) {
				System.out.println(key + ": " + projektHashMap.get(key));
			}
			
	  		


			jsonInString = projektHashMap.toString();

				//jsonObject.put("Projekte",projektHashMap);
			 
		
				 //projektHashMap
			 	return Response.status(200).entity(jsonInString).build();
			// return jsonInString;
	    }
	 
	 
	 
	 
	 @GET
		@Path("/fetchGet")
		@Produces("application/json")
		public Response fetchGet( ) {
			String employeeURI = "test response";

//			try {
//				String sparQuery = "prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
//						+ " select ?x where {" + " ?x ?y ?DriveUserID ."
//						+ " Filter (?DriveUserID = 'haruki.murakami@gmail.com') }";
	//
//				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
	//
//				ResultSet results = qe.execSelect();
//				List var = results.getResultVars();
	//
//				while (results.hasNext()) {
//					QuerySolution qs = results.nextSolution();
//					for (int i = 0; i < var.size(); i++) {
//						String va = var.get(i).toString();
//						RDFNode node = qs.get(va);
//						employeeURI = node.toString();
//					}
//				}
//				qe.close();
//			} catch (Exception e) {
//			//	log.error("GetEmployeeByDriveUserID: Can´t get the employee information " + e);
//			}
			//return Response.status(20).entity(employeeURI).build();
			//return employeeURI;
			return Response.status(200).entity(employeeURI).build();
	 	}
			
	 @POST
	 @Path("/fetchPost")
	 @Consumes("application/json")
	 @Produces("application/json")
		public Response fetchPost(@PathParam("driveUserID") String driveUserID) {
			String employeeURI = driveUserID;
			return Response.status(200).entity(employeeURI).build();

			
	 }
	 
	 
	 

	 
	 
	 @GET
	 @Produces("text/plain")
	 @Path("/methode2") 
	 public String getTurk() {
	        return "Methode 2 sagt Jaaaaa";
	    }
	 
	}


