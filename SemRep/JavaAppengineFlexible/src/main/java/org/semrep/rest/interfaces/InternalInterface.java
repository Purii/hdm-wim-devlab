package main.java.org.semrep.rest.interfaces;

import java.util.LinkedHashMap;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Path("/internal")
public class InternalInterface {
	
	 /**
	  * Die Methode 'getAllDocCategory' gibt alle Dokumentenkategorien eines Projekts zurück, die in der A-Box vorhanden sind, zurück.
	  * Aufgerufen wird die Methode durch das Google App Scipt.
	  * alle Projekte darzustellen. 
	  * 
	  * */

	 @GET
	 @Produces("application/json")
	 @Path("/getdoccategory") 
	 public Response getAllDocCategory()  {
			JSONObject jsonObject = new JSONObject();

			String jsonInString = "ggggg";
			LinkedHashMap<String, String> projektHashMap = new LinkedHashMap<String, String>();

			try {
				
				String sparql = "";
				String projektNameStr = "";

				sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> " + "SELECT DISTINCT ?Dokumentkategorie "
						+ "WHERE { " + "?x ontology:Dokumentkategorie_Name ?Dokumentkategorie . " + "} " + "ORDER BY ?Dokumentkategorie";

				if (sparql != "") {

					// Initialisierung und Ausführung einer SPARQL-Query
					QueryExecution queryExecution =
							QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query",
									sparql);

					ResultSet resultSet = queryExecution.execSelect();
					
				//	System.out.println(resultSet.(2).toString());

					String splitResult = "";
					int indexOfToSplitCharacter;

					for (int i = 0; resultSet.hasNext() == true; i++) {
						QuerySolution querySolution = resultSet.nextSolution();
						projektHashMap.put( ""+ i, "");
						
						for (int j = 0; j < resultSet.getResultVars().size(); j++) {
							String results = resultSet.getResultVars().get(j).toString();
							RDFNode rdfNode = querySolution.get(results);

	 						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
							splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);
							
								// einmaliges befüllen der nachfolgenden Werte
								if (((projektHashMap.get( ""+   i) == "") == true)) {

									switch (results) {
									case "Projekte":
										projektNameStr = splitResult;
										projektHashMap.put( ""+ i, projektNameStr);
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

			for (String key : projektHashMap.keySet()) {
				System.out.println(key + ": " + projektHashMap.get(key));
			}
				jsonInString = projektHashMap.toString();
			 	return Response.status(200).entity(jsonInString).build();
	    }
	
	
	 
	 /**
	  * Die Methode 'getAllProjectStage' gibt alle Projektphasen, die in der A-Box vorhanden sind, zurück.
	  * Aufgerufen wird die Methode durch das Google App Scipt, um in der GUI des Google Plugins
	  * alle Projekte darzustellen. 
	  * 
	  * */
	
	
	 @GET
	 @Produces("application/json")
	 @Path("/getprojectstage") 
	 public Response getAllProjectStage()  {
			JSONObject jsonObject = new JSONObject();

			String jsonInString = "ggggg";
			LinkedHashMap<String, String> projektHashMap = new LinkedHashMap<String, String>();

			try {
				 
				String sparql = "";
				String projektNameStr = "";

				/*SPARQL-SELECT-Query*/
				sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> " + "SELECT DISTINCT ?Phase "
						+ "WHERE { " + "?x ontology:Phase_Name ?Phase . " + "} " + "ORDER BY ?Phase";


				// Initialisierung und Ausführung einer SPARQL-Query
				QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query", sparql);

				//Ergebisse der Ausführung werden in ResultSet gespeichert
				ResultSet resultSet = queryExecution.execSelect();
					
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

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

				// projektHasMap wird in String umgewandelt
				jsonInString = projektHashMap.toString();
				
				// JASON-String wird zurückgegeben
			 	return Response.status(200).entity(jsonInString).build();
	 }
	 
	 
	
	
/**
 * Die Methode 'getAllProjekcts' gibt alle Projekte, die in der A-Box vorhanden sind, zurück.
 * Aufgerufen wird die Methode durch das Google App Scipt, um in der GUI des Google Plugins
 * alle Projekte darzustellen. 
 * 
 * */
	 @GET
	 @Produces("application/json")
	 @Path("/getprojects") 
	 public Response getProjects()  {

			String jsonInString = "";
			LinkedHashMap<String, String> projektHashMap = new LinkedHashMap<String, String>();

			try {
				 
				String sparql = "";
				String projektNameStr = "";

				sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> " + "SELECT DISTINCT ?Projekte "
						+ "WHERE { " + "?x ontology:Projekt_Name ?Projekte . " + "} " + "ORDER BY ?Projekte";

				if (sparql != "") {

					// Initialisierung und Ausführung einer SPARQL-Query
					QueryExecution queryExecution =
							QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query",
									sparql);

					ResultSet resultSet = queryExecution.execSelect();
					
				//	System.out.println(resultSet.(2).toString());

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
										projektHashMap.put("" + i, projektNameStr);
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

			for (String key : projektHashMap.keySet()) {
				System.out.println(key + ": " + projektHashMap.get(key));
			}
				jsonInString = projektHashMap.toString();
			 	return Response.status(200).entity(jsonInString).build();
	    }
	 
	 
		
	 /**
	  * Die Methode 'insertMethode' gibt alle Projekte, die in der A-Box vorhanden sind, zurück.
	  * Aufgerufen wird die Methode durch das Google App Scipt, um in der GUI des Google Plugins
	  * alle Projekte darzustellen. 
	  * 
	  * */
	 
	 @POST
	 @Produces("application/json")
	 @Consumes("application/x-www-form-urlencoded")
	 @Path("/insertMetadata") 
	 public Boolean insertMetadata(){
					
					
			     
					
					
					
				 
		 
				return false;
	 
	 }



	public static void main(String[] args) {
	}

}
