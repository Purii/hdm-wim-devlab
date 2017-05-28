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

import businessObjects.Dokument;
import businessObjects.Person;

public class TokenizerInterface {

	public static String[] inputArray = new String[4];

	public static ArrayList<String> personArrayList = null;
	public static HashMap<String, String> richTokenHashMap = null;

	public static void main(String[] args) {

		setArrayDemoData();
		getDocumentMetaData();
	}

	public static void setArrayDemoData() {
		inputArray[0] = "6";
		inputArray[1] = "Aufgaben";
		inputArray[2] = "KickOff";
		inputArray[3] = "Meilensteine";
	}

	/*
	 * PersonArray - x ProjektArray - leer/Alternative b DokumentArray - x
	 */

	public static ArrayList<String> getDocumentMetaData() {

		String filePath = "src/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		HashMap<String, String> personHashMap = null;
		HashMap<String, String> dokumentHashMap = null;
		richTokenHashMap = new HashMap<String, String>();
		personArrayList = new ArrayList<String>();
		Person personObj = null;
		Dokument dokumentObj = null;

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
			// dokument
			String dok_Str = "";
			String dok_KlasseStr = "";
			String dok_NameStr = "";
			String dok_IDStr = "";
			String dok_URLStr = "";
			String dok_erstelldatumStr = "";
			String dok_UpdatedatumStr = "";
			String dok_KeywordsStr = "";
			String dok_VersionStr = "";
			String dok_TypStr = "";
			String dok_VerfasserStr = "";
			String dok_PhaseStr = "";
			String dok_kategorieStr = "";
			String dok_ProjektStr = "";

			// initalisiere HashMap
			// person
			personHashMap = new HashMap<String, String>();
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

			// initialisiere Objekte
			// person
			personObj = new Person(personStr, idStr, klasseStr, vornameStr, nachnameStr, mailStr, projektStr,
					projektrolleStr, abteilungStr, dokumentStr, aufrufStr);
			// dokument
			dokumentObj = new Dokument(dok_Str, dok_KlasseStr, dok_NameStr, dok_IDStr, dok_URLStr, dok_erstelldatumStr,
					dok_UpdatedatumStr, dok_KeywordsStr, dok_VersionStr, dok_TypStr, dok_VerfasserStr, dok_PhaseStr,
					dok_kategorieStr, dok_ProjektStr);

			for (int y = 0; y < inputArray.length; y++) {

				if (y == 0) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
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
							// Eingrenzung auf userID
							+ "?Person ontology:Person_ID '" + inputArray[y].toString() + "' ." + "}";

				} else {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Dokument ?Klasse ?Verfasser ?Phase ?Dokumentkategorie "
							+ "?Projekt ?Dok_Name ?Dok_ID ?Dok_URL ?Erstelldatum ?Dok_Updatedatum ?Dok_Keywords "
							+ "?Dok_Version ?Dok_Typ " + "WHERE { " + "?Dokument a ?Klasse . "
							+ "?Dokument ontology:Dok_Name ?Dok_Name . " + "?Dokument ontology:Dok_ID ?Dok_ID . "
							+ "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Erstelldatum ?Erstelldatum . "
							+ "?Dokument ontology:Dok_Updatedatum ?Dok_Updatedatum . "
							+ "?Dokument ontology:Dok_Keywords ?Dok_Keywords . "
							+ "?Dokument ontology:Dok_Version ?Dok_Version . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . "
							+ "?Dokument ontology:Dokument_verfasst_von_Person ?Verfasser . "
							+ "?Dokument ontology:Dokument_gehoert_zu_Phase ?Phase . "
							+ "?Dokument ontology:Dokument_hat_Dokumentenkategorie ?Dokumentkategorie . "
							+ "?Dokument ontology:Dokument_gehoert_zu_Projekt ?Projekt . "
							// Eingrenzung auf keyword
							+ "?Dokument ontology:Dok_Keywords '" + inputArray[y].toString() + "' . " + "}";

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
								if (((personHashMap.get("Person") == "") == true)
										|| ((personHashMap.get("ID") == "") == true)
										|| ((personHashMap.get("Klasse") == "") == true)
										|| ((personHashMap.get("Vorname") == "") == true)
										|| ((personHashMap.get("Nachname") == "") == true)
										|| ((personHashMap.get("Mail") == "") == true)
										|| ((personHashMap.get("Projekt") == "") == true)
										|| ((personHashMap.get("Projektrolle") == "") == true)
										|| ((personHashMap.get("Abteilung") == "") == true)
										|| ((personHashMap.get("Dokumente") == "") == true)
										|| ((personHashMap.get("Aufrufe") == "") == true)) {

									switch (results) {
									case "Person":
										personStr = splitResult;
										personHashMap.put("Person", personStr);
										personObj.setPerson(personStr);
										break;
									case "ID":
										idStr = splitResult;
										personHashMap.put("ID", idStr);
										personObj.setId(idStr);
										break;
									case "Klasse":
										klasseStr = splitResult;
										personHashMap.put("Klasse", klasseStr);
										personObj.setKlasse(klasseStr);
										break;
									case "Vorname":
										vornameStr = splitResult;
										personHashMap.put("Vorname", vornameStr);
										personObj.setVorname(vornameStr);
										break;
									case "Nachname":
										nachnameStr = splitResult;
										personHashMap.put("Nachname", nachnameStr);
										personObj.setNachname(nachnameStr);
										break;
									case "Mail":
										mailStr = splitResult;
										personHashMap.put("Mail", mailStr);
										personObj.setMail(mailStr);
										break;
									case "Projekt":
										projektStr = splitResult;
										personHashMap.put("Projekt", projektStr);
										personObj.setPerson_arbeitet_an_Projekt(projektStr);
										break;
									case "Projektrolle":
										projektrolleStr = splitResult;
										personHashMap.put("Projektrolle", projektrolleStr);
										personObj.setPerson_hat_Projektrolle(projektrolleStr);
										break;
									case "Abteilung":
										abteilungStr = splitResult;
										personHashMap.put("Abteilung", abteilungStr);
										personObj.setPerson_gehoert_zu_Abteilung(abteilungStr);
										break;
									case "Dokument":
										dokumentStr = splitResult;
										personHashMap.put("Dokumente", dokumentStr);
										personObj.setPerson_hat_Dokument_verfasst(dokumentStr);
										break;
									case "Aufruf":
										aufrufStr = splitResult;
										personHashMap.put("Aufrufe", aufrufStr);
										personObj.setPerson_ruft_Dokument_auf(aufrufStr);
										break;
									}

								}
								// befülle dynamische Anzahl der Dokumente und
								// Aufrufe
								else if (((personHashMap.get("Dokumente") == "") == false)
										|| ((personHashMap.get("Aufrufe") == "") == false)) {

									switch (results) {
									case "Dokument":
										dokumentStr = splitResult;
										if (!dokumentStr
												.equals(personObj.getPerson_hat_Dokument_verfasst().toString())) {
											personHashMap.put("Dokumente",
													personHashMap.get("Dokumente") + ", " + dokumentStr);
											System.out.println(personObj.getPerson_hat_Dokument_verfasst().toString());
											personObj.setPerson_hat_Dokument_verfasst(
													personObj.getPerson_hat_Dokument_verfasst() + ", " + dokumentStr);
										}
										break;
									case "Aufruf":
										aufrufStr = splitResult;
										if (!aufrufStr.equals(personObj.getPerson_ruft_Dokument_auf())) {
											personHashMap.put("Aufrufe",
													personHashMap.get("Aufrufe") + ", " + aufrufStr);
											personObj.setPerson_ruft_Dokument_auf(
													personObj.getPerson_ruft_Dokument_auf() + ", " + aufrufStr);
										}
										break;
									}

								}

							}

						}

					}

				}

				queryExecution.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		/*
		 * //iteriere durch die HashMap for (String key :
		 * personHashMap.keySet()) {
		 * 
		 * System.out.println("key: " + key + " value: " +
		 * personHashMap.get(key));
		 * 
		 * }
		 */

		richTokenHashMap.put("Person",
				"Person=" + personObj.getPerson() + ", " + "ID=" + personObj.getId() + ", " + "Klasse="
						+ personObj.getKlasse() + ", " + "Vorname=" + personObj.vorname + ", " + "Nachname="
						+ personObj.nachname + ", " + "Mail=" + personObj.mail + ", " + "Person_arbeitet_an_Projekt="
						+ personObj.person_arbeitet_an_Projekt + ", " + "Person_hat_Projektrolle="
						+ personObj.person_hat_Projektrolle + ", " + "Person_gehoert_zu_Abteilung="
						+ personObj.person_gehoert_zu_Abteilung + ", " + "Person_hat_Dokument_verfasst="
						+ personObj.person_hat_Dokument_verfasst + ", " + "Person_ruft_Dokument_auf="
						+ personObj.person_ruft_Dokument_auf);

		System.out.println(richTokenHashMap.get("Person"));

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
