package org.semrep.rest.interfaces;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.*;
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
import org.json.simple.JSONObject;

/*
import com.google.appengine.labs.repackaged.org.json.JSONObject;
*/

@Path("/internal")
public class InternalInterface {

	private ArrayList arrayList;

	/**
	 * Die Methode 'getAllDocCategory' gibt alle Dokumentenkategorien eines Projekts zurück, die in der A-Box vorhanden sind, zurück.
	 * Aufgerufen wird die Methode durch das Google App Scipt.
	 * alle Projekte darzustellen.
	 */

	@GET
	@Produces("application/json")
	@Path("/getdoccategory")
	public Response getAllDocCategory() {

		// Initialisierung von ArrayListe
		ArrayList<String> doccategoryList = new ArrayList<>();


			try {

				//SPARQL-SELECT-Query um die in der A-Box hintlegten Projektphasen abzufragen
				String sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
					+ "SELECT DISTINCT ?Dokumentkategorie "
					+ "WHERE { "
					+ "?x ontology:Dokumentkategorie_Name ?Dokumentkategorie . "
					+ "} "
					+ "ORDER BY ?Dokumentkategorie";


				// Initialisierung und Ausführung einer SPARQL-Query
				QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query", sparql);

				//Ergebisse der Ausführung werden in ResultSet gespeichert
				ResultSet resultSet = queryExecution.execSelect();

				String splitResult;
				int indexOfToSplitCharacter;

				//Resultset wird durchlaufen
				for (int i = 0; resultSet.hasNext() == true; i++) {

					//Antwort von SPARQL-Query wird in QuerySolution abgelegt
					QuerySolution querySolution = resultSet.nextSolution();

					//Werte des Resultsets werden durchlaufen
					for (int j = 0; j < resultSet.getResultVars().size(); j++) {

						//Wert auf Poition 'j' wird ausgelesen und in einen String umgewandelt
						String results = resultSet.getResultVars().get(j).toString();
						RDFNode rdfNode = querySolution.get(results);
						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
						splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

						//Wert auf Poition 'j' wird ArrayList hinzugefügt
						doccategoryList.add(splitResult);
					}
				}
				queryExecution.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// projectcategoryList wird in String umgewandelt
			String jsonInString = doccategoryList.toString();

			// JASON-String wird zurückgegeben
			return Response.status(200).entity(jsonInString).build();
		}


	/**
	 * Die Methode 'getAllProjectStage' gibt alle Projektphasen, die in der A-Box vorhanden sind, zurück.
	 * Aufgerufen wird die Methode durch das Google App Scipt, um in der GUI des Google Plugins
	 * alle Projektphasen darzustellen.
	 */

	@GET
	@Produces("application/json")
	@Path("/getprojectstage")
	public Response getAllProjectStage() {

		// Initialisierung von ArrayListe
		ArrayList<String> projectstageList = new ArrayList<>();

		try {

			//SPARQL-SELECT-Query um die in der A-Box hintlegten Projektphasen abzufragen
			String sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
				+ "SELECT DISTINCT ?Phase "
				+ "WHERE { "
				+ "?x ontology:Phase_Name ?Phase . " + "} "
				+ "ORDER BY ?Phase";

			// Initialisierung und Ausführung einer SPARQL-Query
			QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query", sparql);

			//Ergebisse der Ausführung werden in ResultSet gespeichert
			ResultSet resultSet = queryExecution.execSelect();

			String splitResult;
			int indexOfToSplitCharacter;

			//Resultset wird durchlaufen
			for (int i = 0; resultSet.hasNext() == true; i++) {

				//Antwort von SPARQL-Query wird in QuerySolution abgelegt
				QuerySolution querySolution = resultSet.nextSolution();

				//Werte des Resultsets werden durchlaufen
				for (int j = 0; j < resultSet.getResultVars().size(); j++) {

					//Wert auf Poition 'j' wird ausgelesen und in einen String umgewandelt
					String results = resultSet.getResultVars().get(j).toString();
					RDFNode rdfNode = querySolution.get(results);
					indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
					splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

					//Wert auf Poition 'j' wird ArrayList hinzugefügt
					projectstageList.add(splitResult);
				}
			}
			queryExecution.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// projektHasMap wird in String umgewandelt
		String jsonInString = projectstageList.toString();

		// JASON-String wird zurückgegeben
		return Response.status(200).entity(jsonInString).build();
	}



	/**
	 * Die Methode 'getAllProjekcts' gibt alle Projekte, die in der A-Box vorhanden sind, zurück.
	 * Aufgerufen wird die Methode durch das Google App Scipt, um in der GUI des Google Plugins
	 * alle Projekte darzustellen.
	 */
	@GET
	@Produces("application/json")
	@Path("/getprojects")
	public Response getProjects() {

		// Initialisierung von ArrayListe
		ArrayList<String> projectList = new ArrayList<>();

			try {

				//SPARQL-SELECT-Query um die in der A-Box hintlegten Projektphasen abzufragen
				String sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
					+ "SELECT DISTINCT ?Projekte "
					+ "WHERE { " + "?x ontology:Projekt_Name ?Projekte . "
					+ "} "
					+ "ORDER BY ?Projekte";

				// Initialisierung und Ausführung einer SPARQL-Query
				QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query", sparql);

				//Ergebisse der Ausführung werden in ResultSet gespeichert
				ResultSet resultSet = queryExecution.execSelect();

				String splitResult;
				int indexOfToSplitCharacter;

				//Resultset wird durchlaufen
				for (int i = 0; resultSet.hasNext() == true; i++) {

					//Antwort von SPARQL-Query wird in QuerySolution abgelegt
					QuerySolution querySolution = resultSet.nextSolution();

					//Werte des Resultsets werden durchlaufen
					for (int j = 0; j < resultSet.getResultVars().size(); j++) {

						//Wert auf Poition 'j' wird ausgelesen und in einen String umgewandelt
						String results = resultSet.getResultVars().get(j).toString();
						RDFNode rdfNode = querySolution.get(results);
						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
						splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

						//Wert auf Poition 'j' wird ArrayList hinzugefügt
						projectstageList.add(splitResult);
					}
				}
				queryExecution.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// projektHasMap wird in String umgewandelt
			String jsonInString = projectstageList.toString();

			// JASON-String wird zurückgegeben
			return Response.status(200).entity(jsonInString).build();
		}



	

}
