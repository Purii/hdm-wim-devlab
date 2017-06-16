package semRepServices.interfaces;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

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

import semRepServices.businessObjects.Abteilung;
import semRepServices.businessObjects.Dokument;
import semRepServices.businessObjects.Dokumentvorschlag;
import semRepServices.businessObjects.Person;
import semRepServices.businessObjects.Projekt;

public class EventInterface {
	
	public static String[] inputArray = null;
	public static LinkedHashMap<String, String> personHashMap = null;
	public static String eventSessionID = "";
	public static String eventUniqueID = "";
	
	public static void main(String[] args) {
		setArrayData();
		getUserInformation();
		produceUserInformationEvent();
	}
	
	public static void setArrayData() {

		inputArray = new String[2];
		inputArray[0] = "793dnj"; // sessionID
		inputArray[1] = "0"; // userID

		eventSessionID = inputArray[0].toString();
		eventUniqueID = UUID.randomUUID().toString();

	}
	
	
	private static String produceUserInformationEvent() {

		Person userInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : personHashMap.keySet()) {

			if (key.equals("Person")) {
				userInformationEventObject = new Person(eventSessionID, eventUniqueID,
						personHashMap.get(key).toString());
				System.out.println(userInformationEventObject.toStringPersonObjekt());
			}

		}
		return userInformationEventObject.toStringPersonObjekt();
	}

	public static LinkedHashMap<String, String> getUserInformation() {

		String filePath = "src/semRepServices/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		personHashMap = new LinkedHashMap<String, String>();

		Person personObj = null;

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";
			// person
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
			String favoritStr = "";

			// initalisiere HashMap
			// person
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
			personHashMap.put("Favorit_Dok", "");

			String gehoertZuProjekt = "";

			// initialisiere Objekte
			// person
			personObj = new Person(personStr, idStr, klasseStr, vornameStr, nachnameStr, mailStr, projektStr,
					projektrolleStr, abteilungStr, dokumentStr, aufrufStr, favoritStr);

			for (int y = 0; y <= inputArray.length; y++) {

				// Person
				if (y == 1) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Person ?ID ?Klasse ?Vorname ?Nachname ?Mail ?Projekt ?Projektrolle ?Abteilung ?Dokument ?Aufruf ?Favorit_Dok "
							+ "WHERE { " + "?Person a ?Klasse . " + "?Person ontology:Person_ID ?ID . "
							+ "?Person ontology:Person_Vorname ?Vorname . "
							+ "?Person ontology:Person_Nachname ?Nachname . " + "?Person ontology:Person_Email ?Mail . "
							+ "?Person ontology:Person_arbeitet_an_Projekt ?Projekt . "
							+ "?Person ontology:Person_hat_Projektrolle ?Projektrolle . "
							+ "?Person ontology:Person_gehoert_zu_Abteilung ?Abteilung . "
							+ "?Person ontology:Person_hat_Dokument_verfasst ?Dokument ."
							+ "?Person ontology:Person_ruft_Dokument_auf ?Aufruf ."
							+ "?Person ontology:Person_favorisiert_Dokument ?Favorit_Dok ."
							// Eingrenzung auf userID
							+ "?Person ontology:Person_ID '" + inputArray[y].toString() + "' ." + "}";

				} else {
					sparql = "";
				}

				if (sparql != "") {

					// Initialisierung und Ausführung einer SPARQL-Query
					Query query = QueryFactory.create(sparql);
					QueryExecution queryExecution = QueryExecutionFactory.create(query, ontologyModel);
					// QueryExecution queryExecution =
					// QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query",
					// query);

					// Initialisierung von Resultset für Ergebniswerte der
					// SPARQL-Query
					ResultSet resultSet = queryExecution.execSelect();

					// initialisiere Variablen
					String splitResult = "";
					int indexOfToSplitCharacter;
					int countDokOffersInLoop = 0;
					ArrayList<String> rememberDokNameArrList = new ArrayList<String>();

					List<String> splitKeywordsList = null;

					int countDokNumber = 0;

					outerLoop: for (@SuppressWarnings("unused")
					int i = 0; resultSet.hasNext() == true; i++) {
						QuerySolution querySolution = resultSet.nextSolution();
						for (int j = 0; j < resultSet.getResultVars().size(); j++) {
							String results = resultSet.getResultVars().get(j).toString();
							RDFNode rdfNode = querySolution.get(results);

							indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
							splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

							// Person: Befülle HashMap, wenn die userID
							// durchlaufen
							// wird
							if (y == 1) {

								// einmaliges befüllen der nachfolgenden Werte
								if (((personObj.getPerson() == "") == true)
										|| ((personObj.getId() == "") == true)
										|| ((personObj.getKlasse() == "") == true)
										|| ((personObj.getVorname() == "") == true)
										|| ((personObj.getNachname() == "") == true)
										|| ((personObj.getMail() == "") == true)
										|| ((personObj.getPerson_arbeitet_an_Projekt() == "") == true)
										|| ((personObj.getPerson_hat_Projektrolle() == "") == true)
										|| ((personObj.getPerson_gehoert_zu_Abteilung() == "") == true)
										|| ((personObj.getPerson_hat_Dokument_verfasst() == "") == true)
										|| ((personObj.getPerson_ruft_Dokument_auf() == "") == true)
										|| ((personObj.getPerson_favorisiert_Dokument() == "") == true)) {

									switch (results) {
									case "Person":
										personStr = splitResult;
										personObj.setPerson(personStr);
										break;
									case "ID":
										idStr = splitResult;
										personObj.setId(idStr);
										break;
									case "Klasse":
										klasseStr = splitResult;
										personObj.setKlasse(klasseStr);
										break;
									case "Vorname":
										vornameStr = splitResult;
										personObj.setVorname(vornameStr);
										break;
									case "Nachname":
										nachnameStr = splitResult;
										personObj.setNachname(nachnameStr);
										break;
									case "Mail":
										mailStr = splitResult;
										personObj.setMail(mailStr);
										break;
									case "Projekt":
										projektStr = splitResult;
										personObj.setPerson_arbeitet_an_Projekt(projektStr);
										break;
									case "Projektrolle":
										projektrolleStr = splitResult;
										personObj.setPerson_hat_Projektrolle(projektrolleStr);
										break;
									case "Abteilung":
										abteilungStr = splitResult;
										personObj.setPerson_gehoert_zu_Abteilung(abteilungStr);
										break;
									case "Dokument":
										dokumentStr = splitResult;
										personHashMap.put("Dokumente", dokumentStr);
										personObj.setPerson_hat_Dokument_verfasst(dokumentStr);
										break;
									case "Aufruf":
										aufrufStr = splitResult;
										personObj.setPerson_ruft_Dokument_auf(aufrufStr);
										break;
									case "Favorit_Dok":
										favoritStr = splitResult;
										personObj.setPerson_favorisiert_Dokument(favoritStr);
										break;
									}

								}
								// befülle dynamische Anzahl der Dokumente und
								// Aufrufe
								else if (((personHashMap.get("Dokumente") == "") == false)
										|| ((personHashMap.get("Aufrufe") == "") == false)
										|| ((personHashMap.get("Projekt") == "") == false)
										|| ((personHashMap.get("Projektrolle") == "") == false)
										|| ((personHashMap.get("Abteilung") == "") == false)
										|| ((personHashMap.get("Klasse") == "") == false)
										|| ((personHashMap.get("Favorit_Dok") == "") == false)) {

									switch (results) {
									case "Klasse":
										klasseStr = splitResult;
										splitKeywordsList = Arrays.asList(personObj.getKlasse().toString().split(", "));

										if (splitKeywordsList.contains(klasseStr)) {

											break;

										} else {

											personHashMap.put("Klasse", personHashMap.get("Klasse") + ", " + klasseStr);
											personObj.setKlasse(personObj.getKlasse() + ", " + klasseStr);
											break;
										}
									case "Projekt":
										projektStr = splitResult;
										splitKeywordsList = Arrays.asList(
												personObj.getPerson_arbeitet_an_Projekt().toString().split(", "));

										if (splitKeywordsList.contains(projektStr)) {

											break;

										} else {

											personHashMap.put("Projekt",
													personHashMap.get("Projekt") + ", " + projektStr);
											personObj.setPerson_arbeitet_an_Projekt(
													personObj.getPerson_arbeitet_an_Projekt() + ", " + projektStr);
											break;
										}
									case "Projektrolle":
										projektrolleStr = splitResult;
										splitKeywordsList = Arrays
												.asList(personObj.getPerson_hat_Projektrolle().toString().split(", "));

										if (splitKeywordsList.contains(projektrolleStr)) {

											break;

										} else {

											personHashMap.put("Projektrolle",
													personHashMap.get("Projektrolle") + ", " + projektrolleStr);
											personObj.setPerson_hat_Projektrolle(
													personObj.getPerson_hat_Projektrolle() + ", " + projektrolleStr);
											break;
										}
									case "Abteilung":
										abteilungStr = splitResult;
										splitKeywordsList = Arrays.asList(
												personObj.getPerson_gehoert_zu_Abteilung().toString().split(", "));

										if (splitKeywordsList.contains(abteilungStr)) {

											break;

										} else {

											personHashMap.put("Abteilung",
													personHashMap.get("Abteilung") + ", " + abteilungStr);
											personObj.setPerson_gehoert_zu_Abteilung(
													personObj.getPerson_gehoert_zu_Abteilung() + ", " + abteilungStr);
											break;
										}
									case "Dokument":
										dokumentStr = splitResult;
										splitKeywordsList = Arrays.asList(
												personObj.getPerson_hat_Dokument_verfasst().toString().split(", "));

										if (splitKeywordsList.contains(dokumentStr)) {

											break;

										} else {

											personHashMap.put("Dokumente",
													personHashMap.get("Dokumente") + ", " + dokumentStr);
											personObj.setPerson_hat_Dokument_verfasst(
													personObj.getPerson_hat_Dokument_verfasst() + ", " + dokumentStr);
											break;
										}
									case "Aufruf":
										aufrufStr = splitResult;
										splitKeywordsList = Arrays
												.asList(personObj.getPerson_ruft_Dokument_auf().toString().split(", "));

										if (splitKeywordsList.contains(aufrufStr)) {

											break;

										} else {

											personHashMap.put("Aufrufe",
													personHashMap.get("Aufrufe") + ", " + aufrufStr);
											personObj.setPerson_ruft_Dokument_auf(
													personObj.getPerson_ruft_Dokument_auf() + ", " + aufrufStr);
											break;
										}
									case "Favorit_Dok":
										favoritStr = splitResult;
										splitKeywordsList = Arrays.asList(
												personObj.getPerson_favorisiert_Dokument().toString().split(", "));

										if (splitKeywordsList.contains(favoritStr)) {

											break;

										} else {

											personHashMap.put("Favorit_Dok",
													personHashMap.get("Favorit_Dok") + ", " + favoritStr);
											personObj.setPerson_favorisiert_Dokument(
													personObj.getPerson_favorisiert_Dokument() + ", " + favoritStr);
											break;
										}
									}

								}

							}

						}

					}

					// bei Person
					if (y == 1) {

						personHashMap.put("Person",
								"UserID=" + personObj.getId() + ", " + "Vorname=" + personObj.getVorname() + ", "
										+ "Nachname=" + personObj.getNachname() + ", " + "Mail=" + personObj.getMail()
										+ ", " + "Projekt=" + personObj.getPerson_arbeitet_an_Projekt() + ", "
										+ "Projektrolle=" + personObj.getPerson_hat_Projektrolle() + ", " + "Abteilung="
										+ personObj.getPerson_gehoert_zu_Abteilung() + ", " + "Dok_Autor="
										+ personObj.getPerson_hat_Dokument_verfasst() + ", " + "Dok_Aufrufe="
										+ personObj.getPerson_ruft_Dokument_auf() + ", " + "Dok_Favorit="
										+ personObj.getPerson_favorisiert_Dokument());

					}

					queryExecution.close();

				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// // drucke alles im richTokenHashMap aus
		// for (String key : richTokenHashMap.keySet()) {
		// System.out.println(key + ": " + richTokenHashMap.get(key));
		// }

		return personHashMap;

	}	

}
