package semRepServices.interfaces;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

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

import semRepServices.businessObjects.Dokument2;
import semRepServices.businessObjects.Person;

public class TokenizerInterface2 {

	public static String[] inputArray = null;
	public static ArrayList<String> personArrayList = null;
	public static HashMap<String, String> richTokenHashMap = null;

	public static void main(String[] args) {

		setArrayDemoDataSzenario3();
		getDocumentMetaData();

	}
	public static void setArrayDemoDataSzenario1() {
		inputArray = new String[2];
		inputArray[0] = "2";
		inputArray[1] = "activities_concerning_the_HighNet_project"; //Kontext
	}
	
	public static void setArrayDemoDataSzenario2() {
		inputArray = new String[3];
		inputArray[0] = "2";
		inputArray[1] = "activities_concerning_the_HighNet_project"; //Kontext
		inputArray[2] = "milestone"; //Keyword
		
		//Kontext muss noch geliefert werden vom speech z.B.: Thema wird grad über Highnet geredet
		// 2 filter pro sparql
		//speech gibt keywords und kontext videokonferenz

	}

	public static void setArrayDemoDataSzenario3() {
//		inputArray = new String[4];
//		inputArray[0] = "2";
//		inputArray[1] = "Aufgaben";
//		inputArray[2] = "KickOff";
//		inputArray[3] = "Meilensteine";
		
		inputArray = new String[4];
		inputArray[0] = "793dnj";		// sessionID
		inputArray[1] = "2"; 			// userID
		inputArray[2] = ""; 		// context
		inputArray[3] = "milestone";	// keyword
		
		//Kontext muss noch geliefert werden vom speech z.B.: Thema wird grad über Highnet geredet
		// 2 filter pro sparql
		//speech gibt keywords und kontext videokonferenz

	}

	public static HashMap<String, String> getDocumentMetaData() {

		String filePath = "src/semRepServices/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		HashMap<String, String> personHashMap = null;
		HashMap<String, String> dokumentHashMap = null;
		richTokenHashMap = new HashMap<String, String>();
		personArrayList = new ArrayList<String>();
		Person personObj = null;
		Dokument2 dokumentObj = null;

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
			// dokument
			String dok_Str = "";
			String dok_KlasseStr = "";
			String dok_NameStr = "";
			String dok_IDStr = "";
			String dok_URLStr = "";
			String dok_erstelldatumStr = "";
			String dok_UpdatedatumStr = "";
			String dok_VersionStr = "";
			String dok_TypStr = "";
			String dok_VerfasserStr = "";
			String dok_PhaseStr = "";
			String dok_kategorieStr = "";
			String dok_ProjektStr = "";
			String dok_favorisiertVonString = "";
			String dok_Kontext = "";
			String dok_KeywordsStr = "";

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
			personHashMap.put("Favorit_Dok", "");

			// initialisiere Objekte
			// person
			personObj = new Person(personStr, idStr, klasseStr, vornameStr, nachnameStr, mailStr, projektStr,
					projektrolleStr, abteilungStr, dokumentStr, aufrufStr, favoritStr);
			// dokument
			dokumentObj = new Dokument2(dok_Str, dok_KlasseStr, dok_NameStr, dok_IDStr, dok_URLStr, dok_erstelldatumStr,
					dok_UpdatedatumStr, dok_VersionStr, dok_TypStr, dok_VerfasserStr, dok_PhaseStr,
					dok_kategorieStr, dok_ProjektStr, dok_favorisiertVonString, dok_Kontext, dok_KeywordsStr);

			for (int y = 0; y < inputArray.length; y++) {

				if (y == 0) {

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

					// nur Kontext ohne Keywords
				} if (y == 1 && y == (inputArray.length - 1)) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Klasse ?Kontext ?Dok_Keywords ?Dokument ?Verfasser ?Phase ?Dokumentkategorie "
							+ "?Projekt ?Dok_Name ?Dok_ID ?Dok_URL ?Erstelldatum ?Dok_Updatedatum "
							+ "?Dok_Version ?Dok_Typ ?Favorisiert_Von " 					
							+ "WHERE { " 
							+ "?Dokument a ?Klasse . "
							+ "?Dokument ontology:Dokument_verfasst_von_Person ?Verfasser . "
							+ "?Dokument ontology:Dokument_gehoert_zu_Phase ?Phase . "
							+ "?Dokument ontology:Dokument_hat_Dokumentenkategorie ?Dokumentkategorie . "
							+ "?Dokument ontology:Dokument_gehoert_zu_Projekt ?Projekt . "
							+ "?Dokument ontology:Dok_Name ?Dok_Name . " + "?Dokument ontology:Dok_ID ?Dok_ID . "
							+ "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Erstelldatum ?Erstelldatum . "
							+ "?Dokument ontology:Dok_Updatedatum ?Dok_Updatedatum . "
							+ "?Dokument ontology:Dok_Version ?Dok_Version . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . "
							+ "?Dokument ontology:Dokument_favorisiert_von_Person ?Favorisiert_Von . "
							+ "?Dokument ontology:Dokument_hat_Kontext ?Kontext . "
							+ "?Dokument ontology:Dokument_hat_Keyword ?Dok_Keywords . "
							+ "?Dokument ontology:Dokument_hat_Kontext ?" + inputArray[y].toString() + " . "
							+ "}";

					//Kontext plus keywords
				} if (y > 1) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Klasse ?Kontext ?Dokument ?Verfasser ?Phase ?Dokumentkategorie "
							+ "?Projekt ?Dok_Name ?Dok_ID ?Dok_URL ?Erstelldatum ?Dok_Updatedatum ?Dok_Keywords "
							+ "?Dok_Version ?Dok_Typ ?Favorisiert_Von " 					
							+ "WHERE { " 
							+ "?Dokument a ?Klasse . "
							+ "?Dokument ontology:Dokument_verfasst_von_Person ?Verfasser . "
							+ "?Dokument ontology:Dokument_gehoert_zu_Phase ?Phase . "
							+ "?Dokument ontology:Dokument_hat_Dokumentenkategorie ?Dokumentkategorie . "
							+ "?Dokument ontology:Dokument_gehoert_zu_Projekt ?Projekt . "
							+ "?Dokument ontology:Dok_Name ?Dok_Name . " + "?Dokument ontology:Dok_ID ?Dok_ID . "
							+ "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Erstelldatum ?Erstelldatum . "
							+ "?Dokument ontology:Dok_Updatedatum ?Dok_Updatedatum . "
							//+ "?Dokument ontology:Dok_Keywords ?Dok_Keywords . "
							+ "?Dokument ontology:Dok_Version ?Dok_Version . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . "
							+ "?Dokument ontology:Dokument_favorisiert_von_Person ?Favorisiert_Von . "
							+ "?Dokument ontology:Dokument_hat_Kontext ?Kontext . "
							+ "?Dokument ontology:Dokument_hat_Keyword ?Dok_Keywords . "
							// Eingrenzung auf keyword
							//+ "?Dokument ontology:Dok_Keywords '" + inputArray[y].toString() + "' . " + "}";
							+ "?Dokument ontology:Dokument_hat_Kontext ?" + inputArray[1].toString() + " . "
							+ "?Dokument ontology:Dokument_hat_Keyword ontology:" + inputArray[y].toString() + " ."
							+ "}";

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

				List<String> splitKeywordsList = null;

				// Ergebniswerte werden für Konsolendarstellung aufbereitet
				outerloop: for (@SuppressWarnings("unused")
				int i = 0; resultSet.hasNext() == true; i++) {
					QuerySolution querySolution = resultSet.nextSolution();
					for (int j = 0; j < resultSet.getResultVars().size(); j++) {
						String results = resultSet.getResultVars().get(j).toString();
						RDFNode rdfNode = querySolution.get(results);

						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
						splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

						// Person: Befülle HashMap, wenn die userID durchlaufen
						// wird
						if (y == 0) {

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
									|| ((personHashMap.get("Aufrufe") == "") == true)
									|| ((personHashMap.get("Favorit_Dok") == "") == true)) {

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
								case "Favorit_Dok":
									favoritStr = splitResult;
									personHashMap.put("Favorit_Dok", favoritStr);
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
									splitKeywordsList = Arrays
											.asList(personObj.getPerson_arbeitet_an_Projekt().toString().split(", "));

									if (splitKeywordsList.contains(projektStr)) {

										break;

									} else {

										personHashMap.put("Projekt", personHashMap.get("Projekt") + ", " + projektStr);
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
									splitKeywordsList = Arrays
											.asList(personObj.getPerson_gehoert_zu_Abteilung().toString().split(", "));

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
									splitKeywordsList = Arrays
											.asList(personObj.getPerson_hat_Dokument_verfasst().toString().split(", "));

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

										personHashMap.put("Aufrufe", personHashMap.get("Aufrufe") + ", " + aufrufStr);
										personObj.setPerson_ruft_Dokument_auf(
												personObj.getPerson_ruft_Dokument_auf() + ", " + aufrufStr);
										break;
									}
								case "Favorit_Dok":
									favoritStr = splitResult;
									splitKeywordsList = Arrays
											.asList(personObj.getPerson_favorisiert_Dokument().toString().split(", "));

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

							// Dokument Keywords
						} else {

							// einmaliges befüllen der nachfolgenden Werte
							if (((dokumentObj.getDok_Str() == "") == true)
									|| ((dokumentObj.getDok_KlasseStr() == "") == true)
									|| ((dokumentObj.getDok_NameStr() == "") == true)
									|| ((dokumentObj.getDok_IDStr() == "") == true)
									|| ((dokumentObj.getDok_URLStr() == "") == true)
									|| ((dokumentObj.getDok_erstelldatumStr() == "") == true)
									|| ((dokumentObj.getDok_UpdatedatumStr() == "") == true)
									|| ((dokumentObj.getDok_VersionStr() == "") == true)
									|| ((dokumentObj.getDok_TypStr() == "") == true)
									|| ((dokumentObj.getDokument_verfasst_von_Person() == "") == true)
									|| ((dokumentObj.getDokument_gehoert_zu_Phase() == "") == true)
									|| ((dokumentObj.getDokument_hat_Dokumentenkategorie() == "") == true)
									|| ((dokumentObj.getDokument_gehoert_zu_Projekt() == "") == true)
									|| ((dokumentObj.getDokument_favorisiert_von_Person() == "") == true)
									|| ((dokumentObj.getDokument_hat_Keyword() == "") == true)
									|| ((dokumentObj.getDokument_hat_Kontext() == "") == true)) {

								switch (results) {
								case "Dokument":
									dok_Str = splitResult;
									dokumentObj.setDok_Str(dok_Str);
									break;
								case "Klasse":
									dok_KlasseStr = splitResult;
									dokumentObj.setDok_KlasseStr(dok_KlasseStr);
									break;
								case "Verfasser":
									dok_VerfasserStr = splitResult;
									dokumentObj.setDokument_verfasst_von_Person(dok_VerfasserStr);
									break;
								case "Phase":
									dok_PhaseStr = splitResult;
									dokumentObj.setDokument_gehoert_zu_Phase(dok_PhaseStr);
									break;
								case "Dokumentkategorie":
									dok_kategorieStr = splitResult;
									dokumentObj.setDokument_hat_Dokumentenkategorie(dok_kategorieStr);
									break;
								case "Projekt":
									dok_ProjektStr = splitResult;
									dokumentObj.setDokument_gehoert_zu_Projekt(dok_ProjektStr);
									break;
								case "Dok_Name":
									dok_NameStr = splitResult;
									dokumentObj.setDok_NameStr(dok_NameStr);
									break;
								case "Dok_ID":
									dok_IDStr = splitResult;
									dokumentObj.setDok_IDStr(dok_IDStr);
									break;
								case "Dok_URL":
									dok_URLStr = splitResult;
									dokumentObj.setDok_URLStr(dok_URLStr);
									break;
								case "Erstelldatum":
									dok_erstelldatumStr = rdfNode.toString().substring(0,
											rdfNode.toString().indexOf("^^"));
									dokumentObj.setDok_erstelldatumStr(dok_erstelldatumStr);
									break;
								case "Dok_Updatedatum":
									dok_UpdatedatumStr = rdfNode.toString().substring(0,
											rdfNode.toString().indexOf("^^"));
									dokumentObj.setDok_UpdatedatumStr(dok_UpdatedatumStr);
									break;
								case "Dok_Keywords":
									dok_KeywordsStr = splitResult;
									dokumentObj.setDokument_hat_Keyword(dok_KeywordsStr);
									break;
								case "Dok_Version":
									dok_VersionStr = rdfNode.toString().substring(0, rdfNode.toString().indexOf("^^"));
									dokumentObj.setDok_VersionStr(dok_VersionStr);
									break;
								case "Dok_Typ":
									dok_TypStr = splitResult;
									dokumentObj.setDok_TypStr(dok_TypStr);
									break;
								case "Favorisiert_Von":
									dok_favorisiertVonString = splitResult;
									dokumentObj.setDokument_favorisiert_von_Person(dok_favorisiertVonString);
									break;
								case "Kontext":
									dok_Kontext = splitResult;
									dokumentObj.setDokument_hat_Kontext(dok_Kontext);
									break;
								}

							}
							// befülle dynamische Anzahl der Dokumente und
							// Aufrufe
							else if (((dokumentObj.getDokument_verfasst_von_Person() == "") == false)
									|| ((dokumentObj.getDokument_hat_Dokumentenkategorie() == "") == false)
									|| ((dokumentObj.getDokument_gehoert_zu_Projekt() == "") == false)
									|| ((dokumentObj.getDokument_hat_Keyword() == "") == false)
									|| ((dokumentObj.getDok_KlasseStr() == "") == false)
									|| ((dokumentObj.getDokument_favorisiert_von_Person() == "") == false)
									|| ((dokumentObj.getDokument_hat_Kontext() == "") == false)) {

								switch (results) {
								case "Klasse":
									dok_KlasseStr = splitResult;
									splitKeywordsList = Arrays
											.asList(dokumentObj.getDok_KlasseStr().toString().split(", "));

									if (splitKeywordsList.contains(dok_KlasseStr)) {

										break;

									} else {

										dokumentObj.setDok_KlasseStr(
												dokumentObj.getDok_KlasseStr() + ", " + dok_KlasseStr);
										break;
									}
								case "Verfasser":
									dok_VerfasserStr = splitResult;
									splitKeywordsList = Arrays.asList(
											dokumentObj.getDokument_verfasst_von_Person().toString().split(", "));

									if (splitKeywordsList.contains(dok_VerfasserStr)) {

										break;

									} else {

										dokumentObj.setDokument_verfasst_von_Person(
												dokumentObj.getDokument_verfasst_von_Person() + ", "
														+ dok_VerfasserStr);
										break;
									}
								case "Dokumentkategorie":
									dok_kategorieStr = splitResult;
									splitKeywordsList = Arrays.asList(
											dokumentObj.getDokument_hat_Dokumentenkategorie().toString().split(", "));

									if (splitKeywordsList.contains(dok_kategorieStr)) {

										break;

									} else {

										dokumentObj.setDokument_hat_Dokumentenkategorie(
												dokumentObj.getDokument_hat_Dokumentenkategorie() + ", "
														+ dok_kategorieStr);
										break;
									}
								case "Projekt":
									dok_ProjektStr = splitResult;
									splitKeywordsList = Arrays.asList(
											dokumentObj.getDokument_gehoert_zu_Projekt().toString().split(", "));

									if (splitKeywordsList.contains(dok_ProjektStr)) {

										break;

									} else {

										dokumentObj.setDokument_gehoert_zu_Projekt(
												dokumentObj.getDokument_gehoert_zu_Projekt() + ", " + dok_ProjektStr);
										break;
									}
								case "Dok_Keywords":
									dok_KeywordsStr = splitResult;
									splitKeywordsList = Arrays
											.asList(dokumentObj.getDokument_hat_Keyword().toString().split(", "));

									if (splitKeywordsList.contains(dok_KeywordsStr)) {

										break;

									} else {

										dokumentObj.setDokument_hat_Keyword(
												dokumentObj.getDokument_hat_Keyword() + ", " + dok_KeywordsStr);
										break;
									}
								case "Favorisiert_Von":
									dok_favorisiertVonString = splitResult;
									splitKeywordsList = Arrays.asList(
											dokumentObj.getDokument_favorisiert_von_Person().toString().split(", "));

									if (splitKeywordsList.contains(dok_favorisiertVonString)) {

										break;

									} else {

										dokumentObj.setDokument_favorisiert_von_Person(
												dokumentObj.getDokument_favorisiert_von_Person() + ", "
														+ dok_favorisiertVonString);
										break;
									}
								case "Kontext":
									dok_Kontext = splitResult;
									splitKeywordsList = Arrays
											.asList(dokumentObj.getDokument_hat_Kontext().toString().split(", "));

									if (splitKeywordsList.contains(dok_Kontext)) {

										break;

									} else {

										dokumentObj.setDokument_hat_Kontext(
												dokumentObj.getDokument_hat_Kontext() + ", " + dok_Kontext);
										break;
									}

								}

							}

						}

					}

				}

				// bei Person
				if (y == 0) {

					richTokenHashMap.put("Person", "Person=" + personObj.getPerson() + ", " + "ID=" + personObj.getId()
							+ ", " + "Klasse=" + personObj.getKlasse() + ", " + "Vorname=" + personObj.getVorname()
							+ ", " + "Nachname=" + personObj.getNachname() + ", " + "Mail=" + personObj.getMail() + ", "
							+ "Person_arbeitet_an_Projekt=" + personObj.getPerson_arbeitet_an_Projekt() + ", "
							+ "Person_hat_Projektrolle=" + personObj.getPerson_hat_Projektrolle() + ", "
							+ "Person_gehoert_zu_Abteilung=" + personObj.getPerson_gehoert_zu_Abteilung() + ", "
							+ "Person_hat_Dokument_verfasst=" + personObj.getPerson_hat_Dokument_verfasst() + ", "
							+ "Person_ruft_Dokument_auf=" + personObj.getPerson_ruft_Dokument_auf() + ", "
							+ "Person_favorisiert_Dokument=" + personObj.getPerson_favorisiert_Dokument());

					// bei Dokumenten
				} else {

					richTokenHashMap.put("Dokument_" + y, "Dokument_" + y + "=" + dokumentObj.getDok_Str() + ", "
							+ "Klasse=" + dokumentObj.getDok_KlasseStr() + ", " + "Dok_Name="
							+ dokumentObj.getDok_NameStr() + ", " + "Dok_ID=" + dokumentObj.getDok_IDStr() + ", "
							+ "Dok_URL=" + dokumentObj.getDok_URLStr() + ", " + "Dok_Erstelldatum="
							+ dokumentObj.getDok_erstelldatumStr() + ", " + "Dok_Updatedatum="
							+ dokumentObj.getDok_UpdatedatumStr() + ", " + "Dok_Version=" + dokumentObj.getDok_VersionStr()
							+ ", " + "Dok_Typ=" + dokumentObj.getDok_TypStr() + ", " + "Dokument_verfasst_von_Person="
							+ dokumentObj.getDokument_verfasst_von_Person() + ", " + "Dokument_gehoert_zu_Phase="
							+ dokumentObj.getDokument_gehoert_zu_Phase() + ", " + "Dokument_hat_Dokumentenkategorie="
							+ dokumentObj.getDokument_hat_Dokumentenkategorie() + ", " + "Dokument_gehoert_zu_Projekt="
							+ dokumentObj.getDokument_gehoert_zu_Projekt() + ", " + "Dokument_favorisiert_von_Person="
							+ dokumentObj.getDokument_favorisiert_von_Person() + ", " + "Dok_Keywords="
							+ dokumentObj.getDokument_hat_Keyword() + ", " + "Kontext="
							+ dokumentObj.getDokument_hat_Kontext());

					dokumentObj.flushDokumentObjekt();

				}

				queryExecution.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// drucke alles im richTokenHashMap aus
		for (String key : richTokenHashMap.keySet()) {
			System.out.println(key + ": " + richTokenHashMap.get(key));
		}

		return richTokenHashMap;

	}

}
