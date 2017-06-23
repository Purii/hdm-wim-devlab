package org.semrep.rest.interfaces;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.log4j.Logger;
import org.json.simple.*;
import org.semrep.rest.businessObjects.Dokumentvorschlag;
import org.semrep.rest.businessObjects.Person;


@Path("/main")
public class Main {
	private static JSONObject jsonObj;	 
	private static Logger loggger = Logger.getLogger(Main.class.getName() );
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDocumentOffers") 
	public static Response getDocumentOffers() {
		JSONObject jsonObj = new JSONObject();

			ResultSet resultSet;
			QueryExecution queryExecution;


			// ### initialisiere globale Objekte
			Dokumentvorschlag dokumentvorschlagObj = null;

			//Person personObj = null;
			Person personFavDokObj = null;

			// ### initialisiere globale Variablen
			// inputArray
			
			String[] inputArray = null;
			//inputArray = uxInterface.setArrayDemoData();
			inputArray = new String[6];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "HighNet_project"; // context
			inputArray[3] = "milestone"; // keyword
			inputArray[4] = "tasks"; // keyword
			inputArray[5] = "leading"; //keyword
			

			// Dokument-Objekt bezogen
			String sessionIDStr = "";
			sessionIDStr = inputArray[0].toString();
			String dok_IDStr = "";
			String dok_NameStr = "";
			String prioStr = "";
			String dok_TypStr = "";
			String dok_URLStr = "";
			String dok_folder = "";
			// FavoritDok-Objekt bezogen
			String personVorname_Str = "";
			String personNachname_Str = "";
			String personName_Str = "";
			int numFavDoks = 0;


			LinkedHashMap<String, String> dokOfferLinkedHashMap = null;
			HashMap<String, String> dokOfferHashMap = null;
			HashMap<String, String> tmpDokOfferHashMap = null;
			HashMap<String, String> favDokHashMap = null;
			HashMap<String, String> tmpFavDokHashMap = null;

			dokOfferLinkedHashMap = new LinkedHashMap<String, String>();
			dokOfferHashMap = new LinkedHashMap<String, String>();
			tmpDokOfferHashMap = new LinkedHashMap<String, String>();
			favDokHashMap = new LinkedHashMap<String, String>();
			tmpFavDokHashMap = new LinkedHashMap<String, String>();

			Timestamp timestamp = null;
			timestamp = new Timestamp(System.currentTimeMillis());
			String timeStampStr = timestamp.toString();

			try {
//				File file = new File(filePath);
//				FileReader fileReader = new FileReader(file);
//				ontologyModel.read(fileReader, null, "TTL");

				// initialisiere Variablen
				// sparql
				String sparql = "";

				// initialisiere Objekte
				// dokument
				dokumentvorschlagObj = new Dokumentvorschlag(sessionIDStr, timeStampStr, "'null'", dok_IDStr, dok_NameStr, prioStr,
						dok_TypStr, dok_URLStr, dok_folder);
				// favoritDok
				personFavDokObj = new Person(personName_Str);
				
				//personObj = new Person();

				for (int y = 0; y <= inputArray.length; y++) {

					if (y == 0) {

						// Alle Dokumente abfragen
						sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
								+ "SELECT DISTINCT ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ ?Dok_URL ?Dok_Ordner " + "WHERE { "
								+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
								+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
								+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . " + "}";
					}
					// ermittle FavoritDok
					if (y == 1) {
						sparql = "PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
								+ "SELECT DISTINCT ?PersonNachname ?PersonVorname " + "WHERE { "
								+ "?Person ontology:Person_Vorname ?PersonVorname . "
								+ "?Person ontology:Person_Nachname ?PersonNachname . " + "?Person ontology:Person_ID '"
								+ inputArray[y].toString() + "' . " + "}";
					}
					if (y == 2) {
						sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
								+ "SELECT DISTINCT ?Kontext ?Dokument ?Dok_ID ?Dok_Name ?Dok_Typ "
								+ "?Dok_URL ?Dok_Keywords ?Dok_Ordner " + "WHERE { "
								+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
								+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
								+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
								+ "?Dokument ontology:Dok_Kontext ?Kontext . "
								+ "?Dokument ontology:Dok_Keywords ?Dok_Keywords . "
								+ "?Dokument ontology:Dokument_favorisiert_von_Person ontology:" + personFavDokObj.getName()
								+ " ." + "}";
					}
					if (y == 3) {

						sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
								+ "SELECT DISTINCT ?Kontext ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ "
								+ "?Dok_URL ?Dok_Keywords ?Dok_Ordner " + "WHERE { "
								+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
								+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
								+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
								+ "?Dokument ontology:Dok_Kontext ?Kontext . "
								+ "?Dokument ontology:Dok_Keywords ?Dok_Keywords . "
								// + "?Dokument ontology:Dok_Keywords '" +
								// inputArray[y].toString().toLowerCase() + "' . "
								+ "?Dokument ontology:Dok_Kontext '" + inputArray[2].toString() + "' . " + "FILTER ( "
								+ "?Dok_Keywords = '" + inputArray[3].toString() + "' || " + "?Dok_Keywords = '"
								+ inputArray[4].toString() + "' || " + "?Dok_Keywords = '" + inputArray[5].toString()
								+ "' || " + "?Dok_Keywords = '" + inputArray[4].toString() + " ' && '"
								+ inputArray[3].toString() + "' || " + "?Dok_Keywords = '" + inputArray[3].toString()
								+ " ' && '" + inputArray[4].toString() + "' || " + "?Dok_Keywords = '"
								+ inputArray[4].toString() + " ' && '" + inputArray[5].toString() + "' || "
								+ "?Dok_Keywords = '" + inputArray[5].toString() + " ' && '" + inputArray[4].toString()
								+ "' || " + "?Dok_Keywords = '" + inputArray[3].toString() + " ' && '"
								+ inputArray[5].toString() + "' || " + "?Dok_Keywords = '" + inputArray[5].toString()
								+ " ' && '" + inputArray[3].toString() + "' || " + "?Dok_Keywords = '"
								+ inputArray[4].toString() + " ' && '" + inputArray[3].toString() + " ' && '"
								+ inputArray[5].toString() + "' || " + "?Dok_Keywords = '" + inputArray[4].toString()
								+ " ' && '" + inputArray[5].toString() + " ' && '" + inputArray[3].toString() + "' || "
								+ "?Dok_Keywords = '" + inputArray[3].toString() + " ' && '" + inputArray[4].toString()
								+ " ' && '" + inputArray[5].toString() + "' || " + "?Dok_Keywords = '"
								+ inputArray[3].toString() + " ' && '" + inputArray[5].toString() + " ' && '"
								+ inputArray[4].toString() + "' || " + "?Dok_Keywords = '" + inputArray[5].toString()
								+ " ' && '" + inputArray[4].toString() + " ' && '" + inputArray[3].toString() + "' || "
								+ "?Dok_Keywords = '" + inputArray[5].toString() + " ' && '" + inputArray[3].toString()
								+ " ' && '" + inputArray[4].toString() + "' " + ") " + "}";
					}
					if (y > 3 && y <= inputArray.length) {
						sparql = "";
					}

					if (sparql != "") {

						// Initialisierung und Ausführung einer SPARQL-Query
						Query query = QueryFactory.create(sparql);
						//queryExecution = QueryExecutionFactory.create(query, ontologyModel);
						queryExecution = QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/20170621newOntology/query",
						 query);


						// Initialisierung von Resultset für Ergebniswerte der SPARQL-Query
						resultSet = queryExecution.execSelect();
						
						// initialisiere Variablen
						String splitResult = "";
						int indexOfToSplitCharacter;
						int countLoop = 0;
						int countDokOffersInLoop = 0;
						
						List<String> splitKeywordsList = null;

						// Ergebniswerte werden für Konsolendarstellung aufbereitet
						for (@SuppressWarnings("unused")
						int i = 0; resultSet.hasNext() == true; i++) {
							countLoop = countLoop + i;
							QuerySolution querySolution = resultSet.nextSolution();
							for (int j = 0; j < resultSet.getResultVars().size(); j++) {
								String results = resultSet.getResultVars().get(j).toString();
								RDFNode rdfNode = querySolution.get(results);

								indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
								splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

								if (y == 0 || y == 2 || y == 3) {

									// einmaliges befüllen der nachfolgenden Werte
									if (((dokumentvorschlagObj.getDok_IDStr() == "") == true)
											|| ((dokumentvorschlagObj.getDok_NameStr() == "") == true)
											|| ((dokumentvorschlagObj.getDok_TypStr() == "") == true)
											|| ((dokumentvorschlagObj.getDok_URLStr() == "") == true)
											|| ((dokumentvorschlagObj.getDok_folder() == "") == true)) {

										switch (results) {
										case "Dok_ID":
											dok_IDStr = splitResult;
											dokumentvorschlagObj.setDok_IDStr(dok_IDStr);
											break;
										case "Dok_Name":
											dok_NameStr = splitResult;
											dokumentvorschlagObj.setDok_NameStr(dok_NameStr);
											break;
										case "Dok_Typ":
											dok_TypStr = splitResult;
											dokumentvorschlagObj.setDok_TypStr(dok_TypStr);
											break;
										case "Dok_URL":
											dok_URLStr = splitResult;
											dokumentvorschlagObj.setDok_URLStr(dok_URLStr);
											break;
										case "Dok_Ordner":
											dok_folder = splitResult;
											dokumentvorschlagObj.setDok_folder(dok_folder);
											break;
										}

									}

								}
								//ermittle das favorisierte Dokument zu einer Person
								if (y == 1) {
									
									// einmaliges befüllen der nachfolgenden Werte
									if (((personName_Str == "") == true)) {

										switch (results) {
										case "PersonVorname":
											personVorname_Str = splitResult;
											break;
										case "PersonNachname":
											personNachname_Str = splitResult;
											break;
										}
										
										if (personVorname_Str != "" && personNachname_Str != ""){
											personFavDokObj.setName(personVorname_Str + "_" + personNachname_Str);
											personName_Str = personFavDokObj.getName();
										}

									}
													
								}

							}
							
							//ablegen eines vollständigen Objekts in einer der entsprechenden Maps
							if (y == 0) {
								// prio bei allen Dokumenten irrelevant (0)
								prioStr = "0";
								dokumentvorschlagObj.setPrio(prioStr);
								dokOfferLinkedHashMap.put("Dokument" + i,
										"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
												+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
												+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
												+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
												+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
												+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
												+ dokumentvorschlagObj.getDok_URLStr().toString() + ", " + "DokOrdner="
												+ dokumentvorschlagObj.getDok_folder());

								dokumentvorschlagObj.flushDokumentvorschlag();
							} else if (y == 2) {
								// prio bei Dokumentvorschlägen relevant (1)
								prioStr = "1";
								dokumentvorschlagObj.setPrio(prioStr);
								favDokHashMap.put("Favorit_" + countDokOffersInLoop,
										"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
												+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
												+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
												+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
												+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
												+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
												+ dokumentvorschlagObj.getDok_URLStr().toString() + ", " + "DokOrdner="
												+ dokumentvorschlagObj.getDok_folder());

								countDokOffersInLoop = countDokOffersInLoop + 1;
								dokumentvorschlagObj.flushDokumentvorschlag();
							} else if (y == 3) {
								// prio bei Dokumentvorschlägen relevant (1)
								prioStr = "1";
								dokumentvorschlagObj.setPrio(prioStr);
								dokOfferHashMap.put("Dokumentvorschlag_" + countDokOffersInLoop,
										"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
												+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
												+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
												+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
												+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
												+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
												+ dokumentvorschlagObj.getDok_URLStr().toString() + ", " + "DokOrdner="
												+ dokumentvorschlagObj.getDok_folder());

								countDokOffersInLoop = countDokOffersInLoop + 1;
								dokumentvorschlagObj.flushDokumentvorschlag();
							} 

						}

						queryExecution.close();
						
						

					}

				}

			} catch (Exception e) {
				loggger.error( "Fehler in Main: " + e);
			}
			dokumentvorschlagObj.flushAllDokumentvorschlag(); 
			
		/*	dokumentvorschlagObj.setDok_IDStr("");
			dokumentvorschlagObj.setDok_NameStr("");
			dokumentvorschlagObj.setPrio("");
			dokumentvorschlagObj.setDok_TypStr("");
			dokumentvorschlagObj.setDok_URLStr("");
			dokumentvorschlagObj.setDok_folder("");
			dokumentvorschlagObj.setSessionID("");
			dokumentvorschlagObj.setTimeStamp("");
			 */
			

			// ### checke Duplikate ###
			int countOffers = 0;
			String offerStr = "";
			// favoriten
			for (HashMap.Entry<String, String> entry : favDokHashMap.entrySet()) {
				if (!tmpFavDokHashMap.containsValue(entry.getValue())) {
					offerStr = entry.getKey().toString().split("_")[0];
					tmpFavDokHashMap.put(offerStr + countOffers, entry.getValue());
					countOffers = countOffers + 1;
				}
			}
			for (HashMap.Entry<String, String> entry : tmpFavDokHashMap.entrySet()) {
				dokOfferLinkedHashMap.put(entry.getKey(), entry.getValue());
			}

			countOffers = 0;
			offerStr = "";
			// dokumentvorschläge
			for (HashMap.Entry<String, String> entry : dokOfferHashMap.entrySet()) {
				if (!tmpDokOfferHashMap.containsValue(entry.getValue())) {
					offerStr = entry.getKey().toString().split("_")[0];
					tmpDokOfferHashMap.put(offerStr + countOffers, entry.getValue());
					countOffers = countOffers + 1;
				}
			}
			for (HashMap.Entry<String, String> entry : tmpDokOfferHashMap.entrySet()) {
				dokOfferLinkedHashMap.put(entry.getKey(), entry.getValue());
			}

//			// drucke kompletes OfferEvent aus
//			for (String key : dokOfferLinkedHashMap.keySet()) {
//				System.out.println(key + ": " + dokOfferLinkedHashMap.get(key) + ", ");
//			}
		 
//			dokOfferLinkedHashMap.put("test", "Fuckkkk");
		//String jsonInString;
		jsonObj.put("test", dokOfferLinkedHashMap.toString());
		//String jsonInString = dokOfferLinkedHashMap.toString();
	 	return Response.status(200).entity(jsonObj.toString()).build();
		//return dokOfferLinkedHashMap;

	}
		
}
