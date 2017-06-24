package org.semrep.rest.interfacesPubSub;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.UUID;

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
						projectList.add(splitResult);
					}
				}
				queryExecution.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// projektHasMap wird in String umgewandelt
			String jsonInString = projectList.toString();

			// JASON-String wird zurückgegeben
			return Response.status(200).entity(jsonInString).build();
		}


	/**
	 * Die Methode 'insertMethode' nimmt die Daten via Jason an und fügt diese in die A-Box hinzu
	 */


	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	@Path("/insertMetadata")
	public Boolean insertMetadata(
		@FormParam("docVersion") String doc_version,
		@FormParam("docContext") String doc_have_context,
		@FormParam("docCategpry") String doc_category,
		@FormParam("docKeywords") String keywords,
		@FormParam("docStage") String doc_stage,
		@FormParam("docProjectlink") String doc_projectlink,
		@FormParam("docID") String doc_id,
		@FormParam("docUpdateTime") String doc_updateTime,
		@FormParam("docUrl") String doc_url,
		@FormParam("docCreationTime") String doc_creationTime,
		@FormParam("docRootFolder") String doc_rootFolder,
		@FormParam("docName") String doc_name,
		@FormParam("docTyp") String doc_typ,
		@FormParam("docFavoriten") String doc_favorite,
		@FormParam("docAutor") String doc_autor)
	{



		Boolean response = null;

		if (doc_id == "") {
			response =false;
 		}else {
			try {

				//SPARQL, checkt ob DokId bereits in abox vorhanden ist
				String checkSparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
					+ "SELECT DISTINCT ?" + doc_id
					+ "WHERE { " + "?x ontology:"+doc_id +" ?Dokumente . "
					+ "} "
					+ "ORDER BY ?Dokumente";

				// Initialisierung und Ausführung einer SPARQL-Query
				QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query", checkSparql);

				//Ergebisse der Ausführung werden in ResultSet gespeichert
				ResultSet resultSet = queryExecution.execSelect();

				if (resultSet != null){


				//DeleteQuery and InsertQuery
				try {

				String deleteQuery = "		PREFIX owl: <http://www.w3.org/2002/07/owl#>"
					+ " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#>"
					+ " DELETE { "
					+ "	    a ontology:Dokument, owl:NamedIndividual ;"
					+ "	    ontology:Dok_Name ' " +doc_name+ "';"
					+ "		ontology:Dok_ID '" +doc_id+ "';"
					+ "		ontology:Dok_Typ '"+doc_typ+"' ;"
					+ "		ontology:Dok_Erstelldatum '"+doc_creationTime+"' ;"
					+ "		ontology:Dok_Updatedatum '"+doc_updateTime+"' ;"
					+ "		ontology:Dok_Kontext '" +doc_have_context+"' ;"
					+ "		ontology:Dok_Keywords '"+keywords+"' ;"
					+ "		ontology:Dok_Ordner '"+doc_rootFolder+"' ;"
					+ "		ontology:Dok_URL '"+doc_url+"' ;"
					+ "		ontology:Dok_Version '"+doc_version+"' ;"
					+ "	 	ontology:Dokument_gehoert_zu_Projekt ontology:'"+doc_projectlink+"' ;"
					+ "		ontology:Dokument_hat_Dokumentenkategorie ontology:'"+doc_category+"' ;"
					+ "		ontology:Dokument_favorisiert_von_Person ontology:'"+doc_favorite+"' ;"
					+ "		ontology:Dokument_gehoert_zu_Phase ontology:'"+doc_stage+"' ;"
					+ "		ontology:Dokument_verfasst_von_Person ontology:'"+doc_autor+"' ;"
					+ " }"
					+ "INSERT DATA { "
					+ "     <http://www.semanticweb.org/sem-rep/ontology#>"
					+ "	    a ontology:Dokument, owl:NamedIndividual ;"
					+ " 	ontology:Dok_Name ' " +doc_name+ "';"
					+ "		ontology:Dok_ID '" +doc_id+ "';"
					+ "		ontology:Dok_Typ '"+doc_typ+"' ;"
					+ "		ontology:Dok_Erstelldatum '"+doc_creationTime+"' ;"
					+ "		ontology:Dok_Updatedatum '"+doc_updateTime+"' ;"
					+ "		ontology:Dok_Kontext '" +doc_have_context+"' ;"
					+ "		ontology:Dok_Keywords '"+keywords+"' ;"
					+ "		ontology:Dok_Ordner '"+doc_rootFolder+"' ;"
					+ "		ontology:Dok_URL '"+doc_url+"' ;"
					+ "		ontology:Dok_Version '"+doc_version+"' ;"
					+ "	 	ontology:Dokument_gehoert_zu_Projekt ontology:'"+doc_projectlink+"' ;"
					+ "		ontology:Dokument_hat_Dokumentenkategorie ontology:'"+doc_category+"' ;"
					+ "		ontology:Dokument_favorisiert_von_Person ontology:'"+doc_favorite+"' ;"
					+ "		ontology:Dokument_gehoert_zu_Phase ontology:'"+doc_stage+"' ;"
					+ "		ontology:Dokument_verfasst_von_Person ontology:'"+doc_autor+"' ;"
					+ " }"
					+ " WHERE  "
					+ " { "
					+ " ?x <http://www.semanticweb.org/sem-rep/ontology#DokumentenID> '"+doc_id+"' "
					+ " }";


					String uuID = UUID.randomUUID().toString();
					UpdateProcessor uP = UpdateExecutionFactory.createRemote(
						UpdateFactory.create(String.format(deleteQuery, uuID)), "http://35.187.45.171:3030/ontology/query");
					uP.execute();
				}

					catch (Exception e) {
					//("Fehler: Die Daten konnten nicht gespeichert werden" + e);
					response = false;
					}

				}
				else {

					try {

						//InsertQuery
						String insertQuery = "		PREFIX owl: <http://www.w3.org/2002/07/owl#>"
							+ " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#>"
							+ "INSERT DATA { "
							+ "		<http://www.semanticweb.org/sem-rep/ontology#2_TestDokument>"
							+ "		a ontology:Dokument, owl:NamedIndividual ;"
							+ "		ontology:Dok_Name ' " +doc_name+ "';"
							+ "		ontology:Dok_ID '" +doc_id+ "';"
							+ "		ontology:Dok_Typ '"+doc_typ+"' ;"
							+ "		ontology:Dok_Erstelldatum '"+doc_creationTime+"' ;"
							+ "		ontology:Dok_Updatedatum '"+doc_updateTime+"' ;"
							+ "		ontology:Dok_Kontext '" +doc_have_context+"' ;"
							+ "		ontology:Dok_Keywords '"+keywords+"' ;"
							+ "		ontology:Dok_Ordner '"+doc_rootFolder+"' ;"
							+ "		ontology:Dok_URL '"+doc_url+"' ;"
							+ "		ontology:Dok_Version '"+doc_version+"' ;"
							+ "	 	ontology:Dokument_gehoert_zu_Projekt ontology:'"+doc_projectlink+"' ;"
							+ "		ontology:Dokument_hat_Dokumentenkategorie ontology:'"+doc_category+"' ;"
							+ "		ontology:Dokument_favorisiert_von_Person ontology:'"+doc_favorite+"' ;"
							+ "		ontology:Dokument_gehoert_zu_Phase ontology:'"+doc_stage+"' ;"
							+ "		ontology:Dokument_verfasst_von_Person ontology:'"+doc_autor+"' ;"
							+ " }";


						String uuID = UUID.randomUUID().toString();
						UpdateProcessor uP = UpdateExecutionFactory.createRemote(UpdateFactory.create(
							String.format(insertQuery, uuID)), "http://35.187.45.171:3030/ontology/query");

						uP.execute();
						response = true;
						}
					  catch (Exception e) {
						//("Fehler: Die Daten konnten nicht gespeichert werden" + e);
						response = false;
					}
				}
			}
			 catch (Exception e) {
				System.out.println(e.getMessage());
				  response = false;
			 }
		}
		return response;
	}
}
