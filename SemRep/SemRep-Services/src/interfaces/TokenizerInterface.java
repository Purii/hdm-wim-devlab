package interfaces;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.*;

import org.apache.jena.base.Sys;
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

public class TokenizerInterface {

	public static String[] inputArray = new String[4];
	public static HashMap<String, String> personHashMap;
	public static ArrayList<String> list = new ArrayList<String>();

	public static void main(String[] args) {

		setArrayDemoData();
		getDocumentMetaData();
	}

	public static void setArrayDemoData() {
		inputArray[0] = "4";
		inputArray[1] = "activities";
		inputArray[2] = "HighNet";
		inputArray[3] = "project";
	}

	/*
	 * PersonArray - x ProjektArray - leer/Alternative b DokumentArray - x
	 */

	public static ArrayList<String> getDocumentMetaData() {

		String filePath = "src/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");
			personHashMap = new HashMap<String, String>();

			String sparql = "";

			String personStr = "";
			String idStr = "";
			String klasseStr = "";
			String vornameStr = "";
			String nachnameStr = "";
			String mailStr = "";
			String projektStr = "";
			String projektrolleStr = "";
			String abteilungStr = "";
			String dokumentStr = "";
			String aufrufStr = "";

			for (int y = 0; y < inputArray.length; y++) {

				if (y == 0) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
							+ "SELECT DISTINCT ?Person ?ID ?Klasse ?Vorname ?Nachname ?Mail ?Status ?Projekt ?Projektrolle ?Abteilung ?Dokument ?Aufruf "
							+ "WHERE { " + "?Person a ?Klasse . " + "?Person ontology:Person_ID ?ID . "
							+ "?Person ontology:Person_Vorname ?Vorname . "
							+ "?Person ontology:Person_Nachname ?Nachname . " + "?Person ontology:Person_Email ?Mail . "
							+ "?Person ontology:Person_Mitarbeiter ?Status ."
							+ "?Person ontology:Person_arbeitet_an_Projekt ?Projekt . "
							+ "?Person ontology:Person_hat_Projektrolle ?Projektrolle . "
							+ "?Person ontology:Person_gehoert_zu_Abteilung ?Abteilung . "
							+ "?Person ontology:Person_hat_Dokument_verfasst ?Dokument ."
							+ "?Person ontology:Person_ruft_Dokument_auf ?Aufruf ."
							// + "?individual rdf:type
							// ontology:Externer_Mitarbeiter . "
							// Eingrenzung auf userID
							+ "?Person ontology:Person_ID '" + inputArray[y].toString() + "' ." + "}";

					// initalisiere HashMap
					personHashMap.put("Person", "");
					personHashMap.put("ID", "");
					personHashMap.put("Klasse", "");
					personHashMap.put("Vorname", "");
					personHashMap.put("Nachname", "");
					personHashMap.put("Mail", "");
					personHashMap.put("Projekt", "");
					personHashMap.put("Projektrolle", "");
					personHashMap.put("Abteilung", "");
					personHashMap.put("Dokumente", "");
					personHashMap.put("Aufrufe", "");

				} else {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
							+ "SELECT DISTINCT ?Klasse ?Person ?ID ?Vorname ?Nachname ?mail ?Status ?Projekt ?Projektrolle ?Abteilung ?Dokument ?Aufruf "
							+ "WHERE { " + "?Person a ?Klasse . " + "?Person ontology:Person_ID ?ID . "
							+ "?Person ontology:Person_Vorname ?Vorname . "
							+ "?Person ontology:Person_Nachname ?Nachname . " + "?Person ontology:Person_Email ?mail . "
							+ "?Person ontology:Person_Mitarbeiter ?Status ."
							+ "?Person ontology:Person_arbeitet_an_Projekt ?Projekt . "
							+ "?Person ontology:Person_hat_Projektrolle ?Projektrolle . "
							+ "?Person ontology:Person_gehoert_zu_Abteilung ?Abteilung . "
							+ "?Person ontology:Person_hat_Dokument_verfasst ?Dokument ."
							+ "?Person ontology:Person_ruft_Dokument_auf ?Aufruf ."
							// + "?individual rdf:type
							// ontology:Externer_Mitarbeiter . "
							// Eingrenzung auf userID
							+ "?Person ontology:Person_ID '" + inputArray[y].toString() + "' ." + "}";

				}

				// Initialisierung und Ausführung einer SPARQL-Query
				Query query = QueryFactory.create(sparql);
				QueryExecution queryExecution = QueryExecutionFactory.create(query, ontologyModel);

				// Initialisierung von Resultset für Ergebniswerte der
				// SPARQL-Query
				ResultSet resultSet = queryExecution.execSelect();

				// initialisiere Variablen
				String splitResult = "";
				int indexOfToSplitCharacter;

				// Ergebniswerte werden für Konsolendarstellung aufbereitet
				outerloop: for (@SuppressWarnings("unused")
				int i = 0; resultSet.hasNext() == true; i++) {
					QuerySolution querySolution = resultSet.nextSolution();
					for (int j = 0; j < resultSet.getResultVars().size(); j++) {
						String results = resultSet.getResultVars().get(j).toString();
						RDFNode rdfNode = querySolution.get(results);

						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
						splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

						// befülle HashMap, wenn die userID durchlaufen wird
						if (y == 0) {

							if (results.equals("Klasse") && splitResult.equals("NamedIndividual")) {

								break outerloop;

							} else {

								// einmaliges befüllen der nachfolgenden Werte
								if (((personHashMap.get("Person") == "") == true) || ((personHashMap.get("ID") == "") == true)
										|| ((personHashMap.get("Klasse") == "") == true) || ((personHashMap.get("Vorname") == "") == true)
										|| ((personHashMap.get("Nachname") == "") == true) || ((personHashMap.get("Mail") == "") == true)
										|| ((personHashMap.get("Projekt") == "") == true)
										|| ((personHashMap.get("Projektrolle") == "") == true)
										|| ((personHashMap.get("Abteilung") == "") == true)
										|| ((personHashMap.get("Dokumente") == "") == true)
										|| ((personHashMap.get("Aufrufe") == "") == true)) {

									// //iteriere durch die HashMap
									// for (String key : personHashMap.keySet())
									// {
									//
									// System.out.println("key: " + key + "
									// value: " + personHashMap.get(key));
									//
									// }

									
									switch (results) {
									case "Person":
										personStr = splitResult;
										personHashMap.put("Person", personStr);
										break;
									case "ID":
										idStr = splitResult;
										personHashMap.put("ID", idStr);
										break;
									case "Klasse":
										klasseStr = splitResult;
										personHashMap.put("Klasse", klasseStr);
										break;
									case "Vorname":
										vornameStr = splitResult;
										personHashMap.put("Vorname", vornameStr);
										break;
									case "Nachname":
										nachnameStr = splitResult;
										personHashMap.put("Nachname", nachnameStr);
										break;
									case "Mail":
										mailStr = splitResult;
										personHashMap.put("Mail", mailStr);
										break;
									case "Projekt":
										projektStr = splitResult;
										personHashMap.put("Projekt", projektStr);
										break;
									case "Projektrolle":
										projektrolleStr = splitResult;
										personHashMap.put("Projektrolle", projektrolleStr);
										break;
									case "Abteilung":
										abteilungStr = splitResult;
										personHashMap.put("Abteilung", abteilungStr);
										break;
									case "Dokument":
										dokumentStr = splitResult;
										personHashMap.put("Dokumente", dokumentStr);
										break;
									case "Aufruf":
										aufrufStr = splitResult;
										personHashMap.put("Aufrufe", aufrufStr);
										break;
									}

								}
								// befülle dynamische Anzahl der Dokumente und
								// Aufrufe
								else if (((personHashMap.get("Dokumente") == "") == false) || ((personHashMap.get("Aufrufe") != "") == false)) {

									switch (results) {
									case "Dokument":
										dokumentStr = splitResult;
										personHashMap.put("Dokumente",
												personHashMap.get("Dokumente") + ", " + dokumentStr);
										break;
									case "Aufruf":
										aufrufStr = splitResult;
										personHashMap.put("Aufrufe", personHashMap.get("Aufrufe") + ", " + aufrufStr);
										break;
									}
									
								}

								//System.out.println(results + ": " + splitResult);

							}

						}

					}

					//System.out.print("\n");

				}
				 
				queryExecution.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		//iteriere durch die HashMap
		for (String key : personHashMap.keySet()) {
		
			System.out.println("key: " + key + " value: " + personHashMap.get(key));
		
		}
		
		return null;

	}

	public ArrayList<String> generateKeywordList(String keyword) {

		return null;

	}

	public void sortKeywords() {

	}

	public HashMap<String, String> generateRichTokenHashMap(String keyword) {
		return null;

	}

}
