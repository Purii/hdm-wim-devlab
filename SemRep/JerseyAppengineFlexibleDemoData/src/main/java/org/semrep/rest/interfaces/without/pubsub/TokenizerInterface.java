package main.java.org.semprep.rest.interfaces.without.pubsub;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

 import main.java.org.semprep.rest.businessObjects.Abteilung;
import main.java.org.semprep.rest.businessObjects.Dokument;
import main.java.org.semprep.rest.businessObjects.Person;
import main.java.org.semprep.rest.businessObjects.Projekt;
import main.java.org.semrep.rest.helper.Constants;
import main.java.org.semrep.rest.helper.FusekiConfigConstants;

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
import org.json.simple.JSONObject;


@Path("/tokenizerInterface")
public class TokenizerInterface {

	private static JSONObject jsonObj;
	private static Logger loggger = Logger.getLogger(TokenizerInterface.class);

	public static String[] inputArray = null;
	public static ArrayList<String> personArrayList = null;
	public static LinkedHashMap<String, String> richTokenHashMap = null;
	public static LinkedHashMap<String, String> dokumentHashMap = null;

	// Standard Variablen
	public static String eventSessionID = "";
	public static String eventUniqueID = "'null'";
	public static int anzahlDokumente = 0;

	// ### time stamp
	private static Timestamp timestamp = null;
	private static long timestampLong;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTokenizerEvent")
	public static void TokenizerInterfaceMain() {
		//Path
//		/tokenizerInterface/getTokenizerInterfaceEvents
		setArrayData();
		getMetaData();
		getUserInformation();
		getDepartmentInformation();
		getProjectInformation();
		goThoughDocumentInstances();

	}

	public static void main(String[] args) {

		//Path
//		/tokenizerInterface/getTokenizerInterfaceEvents
		setArrayData();
		getMetaData();
		getUserInformation();
		getDepartmentInformation();
		getProjectInformation();
		goThoughDocumentInstances();

	}


	// produce
	// consume
	// produce ProjectInformationEvent
	@Produces(MediaType.APPLICATION_JSON)
	private static Response getProjectInformation() {

		JSONObject jsonObj = new JSONObject();

		Projekt projectInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : richTokenHashMap.keySet()) {
			if (key.equals("Projekt")) {
				projectInformationEventObject = new Projekt(eventSessionID, String.valueOf(timestampLong), eventUniqueID,
					richTokenHashMap.get(key).toString());
				System.out.println(projectInformationEventObject.toStringProjektObjekt());
			}

		}
		// return projectInformationEventObject.toStringProjektObjekt();
		jsonObj.put("ProjectInformationEvent", projectInformationEventObject.toStringProjektObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	@Produces(MediaType.APPLICATION_JSON)
	private static void goThoughDocumentInstances() {

		JSONObject jsonObj = new JSONObject();

		for (int goThoughNumDoks = 0; goThoughNumDoks < anzahlDokumente; goThoughNumDoks++) {
			getDocumentInformation(goThoughNumDoks);
		}

//		jsonObj.put("DocumentInformationEvent", richTokenHashMap.toString());
//		return Response.status(200).entity(jsonObj.toString()).build();

	}

	// produce
	// consume
	// produce DocumentInformationEvent
	@Produces(MediaType.APPLICATION_JSON)
	private static Response getDocumentInformation(int dokIndex) {

		JSONObject jsonObj = new JSONObject();

		Dokument documentInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : richTokenHashMap.keySet()) {

			if (key.equals("Dokument" + dokIndex)) {
				documentInformationEventObject = new Dokument(eventSessionID, String.valueOf(timestampLong), eventUniqueID,
					richTokenHashMap.get(key).toString());
				System.out.println(documentInformationEventObject.toStringDokumentObjekt());
				break;
			}

		}
		// return documentInformationEventObject.toStringDokumentObjekt();
		jsonObj.put("DocumentInformationEvent", documentInformationEventObject.toStringDokumentObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	// produce
	// consume
	// produce DepartmentInformationEvent
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getDepartmentInformation() {

		JSONObject jsonObj = new JSONObject();

		Abteilung departmentInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : richTokenHashMap.keySet()) {
			if (key.equals("Abteilung")) {
				departmentInformationEventObject = new Abteilung(eventSessionID, String.valueOf(timestampLong), eventUniqueID,
					richTokenHashMap.get(key).toString());
				System.out.println(departmentInformationEventObject.toStringAbteilungsObjekt());
			}

		}
		// return departmentInformationEventObject.toStringAbteilungsObjekt();
		jsonObj.put("DepartmentInformationEvent", departmentInformationEventObject.toStringAbteilungsObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	// produce
	// consume
	// produce UserInformationEvent
	@Produces(MediaType.APPLICATION_JSON)
	public static Response getUserInformation() {

		JSONObject jsonObj = new JSONObject();

		Person userInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : richTokenHashMap.keySet()) {

			if (key.equals("Person")) {
				userInformationEventObject = new Person(eventSessionID, String.valueOf(timestampLong), eventUniqueID,
					richTokenHashMap.get(key).toString());
				System.out.println(userInformationEventObject.toStringPersonObjekt());
			}

		}
		jsonObj.put("UserInformationEvent", userInformationEventObject.toStringPersonObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();
	}

	public static void setArrayData() {

		// inputArray = new String[4];
		// inputArray[0] = "793dnj"; // sessionID
		// inputArray[1] = "6"; // userID
		// inputArray[2] = "Videokonferenz"; // context
		// inputArray[3] = "milestone"; // keyword

		inputArray = new String[6];
		inputArray[0] = "1"; // sessionID
		inputArray[1] = "2"; // userID
		inputArray[2] = "HighNet_project"; // context
		inputArray[3] = "milestone"; // keyword
		inputArray[4] = "tasks"; // keyword
		inputArray[5] = "leading"; // keyword

		eventSessionID = inputArray[0].toString();
		eventUniqueID = UUID.randomUUID().toString();
		timestamp = new Timestamp(System.currentTimeMillis());
		timestampLong = timestamp.getTime();

	}

	public static LinkedHashMap<String, String> getMetaData() {

		// String filePath = "src/semRepServices/interfaces/Ontology.owl";
		// OntModel ontologyModel =
		// ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		HashMap<String, String> personHashMap = null;
		dokumentHashMap = new LinkedHashMap<String, String>();
		richTokenHashMap = new LinkedHashMap<String, String>();
		personArrayList = new ArrayList<String>();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		richTokenHashMap.put(Constants.PubSub.AttributeKey.SESSION_ID, inputArray[0]);
		richTokenHashMap.put(Constants.PubSub.AttributeKey.TIMESTAMP, timestamp.toString());

		Person personObj = null;
		Dokument dokumentObj = null;
		Projekt projektObj = null;
		Abteilung abteilungObj = null;

		try {
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
			String dok_folder = "";
			// projekt
			String projektIDStr = "";
			String projektNameStr = "";
			String projekt_gehoert_zu_UnternehmenStr = "";
			String projekt_gehoert_zu_AbteilungStr = "";
			String projekt_hat_ProjektmitgliedStr = "";
			String projekt_hat_DokumentStr = "";
			// abteilung
			String abteilung_IDStr = "";
			String abteilung_NameStr = "";
			String abteilung_KuerzelStr = "";
			String abteilung_hat_ProjektStr = "";
			String abteilung_hat_MitarbeiterStr = "";
			String abteilung_gehoert_zu_UnternehmenStr = "";

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

			String gehoertZuProjekt = "";

			// initialisiere Objekte
			// person
			personObj = new Person(personStr, idStr, klasseStr, vornameStr, nachnameStr, mailStr, projektStr,
				projektrolleStr, abteilungStr, dokumentStr, aufrufStr, favoritStr);
			// dokument
			dokumentObj = new Dokument(dok_NameStr, dok_IDStr, dok_URLStr, dok_erstelldatumStr, dok_VersionStr,
				dok_TypStr, dok_folder, dok_VerfasserStr, dok_ProjektStr, dok_favorisiertVonString,
				dok_KeywordsStr);
			// projekt
			projektObj = new Projekt(projektIDStr, projektNameStr, projekt_gehoert_zu_UnternehmenStr,
				projekt_gehoert_zu_AbteilungStr, projekt_hat_ProjektmitgliedStr, projekt_hat_DokumentStr);

			abteilungObj = new Abteilung(abteilung_IDStr, abteilung_NameStr, abteilung_KuerzelStr,
				abteilung_hat_ProjektStr, abteilung_hat_MitarbeiterStr, abteilung_gehoert_zu_UnternehmenStr);

			for (int y = 0; y <= inputArray.length; y++) {

				// Person
				if (y == 1) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
						+ "SELECT DISTINCT ?Person ?Dokument ?Dok_Name ?ID ?Klasse ?Vorname ?Nachname ?Mail ?Projekt ?Projektrolle ?Abteilung ?Dokument ?Aufruf ?Favorit_Dok "
						+ "WHERE { " + "?Person a ?Klasse . " + "?Person ontology:Person_ID ?ID . "
						+ "?Person ontology:Person_Vorname ?Vorname . "
						+ "?Person ontology:Person_Nachname ?Nachname . " + "?Person ontology:Person_Email ?Mail . "
						+ "?Person ontology:Person_arbeitet_an_Projekt ?Projekt . "
						+ "?Person ontology:Person_hat_Projektrolle ?Projektrolle . "
						+ "?Person ontology:Person_gehoert_zu_Abteilung ?Abteilung . "
						+ "?Person ontology:Person_hat_Dokument_verfasst ?Dokument ."
						+ "?Person ontology:Person_ruft_Dokument_auf ?Aufruf ."
						+ "?Person ontology:Person_favorisiert_Dokument ?Favorit_Dok . "
						+ "?Dokument ontology:Dok_Name ?Dok_Name . "
						// + "?Favorit_Dok ontology:Dok_Name ?Dok_Name . "
						// Eingrenzung auf userID
						+ "?Person ontology:Person_ID '" + inputArray[y].toString() + "' ." + "}";

				}
				// Dokumente
				// Kontext & Keyword
				if (y == 3 && y < inputArray.length) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
						+ "SELECT DISTINCT ?Dok_Name ?Kontext ?Dok_Keywords ?Dokument ?Verfasser "
						+ "?Projekt ?Dok_ID ?Dok_URL ?Erstelldatum "
						+ "?Dok_Version ?Dok_Typ ?Favorisiert_Von ?Dok_Ordner " + "WHERE { "
						+ "?Dokument ontology:Dokument_verfasst_von_Person ?Verfasser . "
						+ "?Dokument ontology:Dokument_gehoert_zu_Projekt ?Projekt . "
						+ "?Dokument ontology:Dok_Name ?Dok_Name . " + "?Dokument ontology:Dok_ID ?Dok_ID . "
						+ "?Dokument ontology:Dok_URL ?Dok_URL . "
						+ "?Dokument ontology:Dok_Erstelldatum ?Erstelldatum . "
						+ "?Dokument ontology:Dok_Version ?Dok_Version . "
						+ "?Dokument ontology:Dok_Typ ?Dok_Typ . "
						+ "?Dokument ontology:Dokument_favorisiert_von_Person ?Favorisiert_Von . "
						+ "?Dokument ontology:Dok_Kontext ?Kontext . "
						+ "?Dokument ontology:Dok_Keywords ?Dok_Keywords . "
						+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
						// + "?Dokument ontology:Dok_Keywords '"
						// + inputArray[y].toString() + "' . "
						// + "?Dokument ontology:Dok_Kontext '"
						// + inputArray[2].toString() + "' . "
						// + "}"
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
						+ " ' && '" + inputArray[4].toString() + "' " + ") " + "}" + "ORDER BY ?Dok_Name";

					// leer
				} else if (y < 1) {
					sparql = "";
				}
				// Abteilung
				if (y > 1 && y < 3) {

					String[] Person_gehoert_zu_AbteilungStringArr = personObj.getPerson_gehoert_zu_Abteilung()
						.toString().split(", ");
					String splittedAbteilungString = "";

					if (Person_gehoert_zu_AbteilungStringArr.length > 0) {
						splittedAbteilungString = Person_gehoert_zu_AbteilungStringArr[0];
					} else {
						splittedAbteilungString = personObj.getPerson_gehoert_zu_Abteilung();
					}

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
						+ "SELECT DISTINCT  ?Abteilung ?Abteilung_ID ?Abteilung_Name ?Abteilung_Kuerzel "
						+ "?Abteilung_hat_Projekt ?Abteilung_hat_Mitarbeiter ?Abteilung_gehoert_zu_Unternehmen "
						+ "WHERE { " + "?Projekt ontology:Projekt_ID ?ProjektID . "
						+ "?Abteilung ontology:Abteilung_ID ?Abteilung_ID . "
						+ "?Abteilung ontology:Abteilung_Name ?Abteilung_Name . "
						+ "?Abteilung ontology:Abteilung_Kuerzel ?Abteilung_Kuerzel . "
						+ "?Abteilung ontology:Abteilung_hat_Projekt ?Abteilung_hat_Projekt . "
						+ "?Abteilung ontology:Abteilung_hat_Mitarbeiter ?Abteilung_hat_Mitarbeiter . "
						+ "?Abteilung ontology:Abteilung_gehoert_zu_Unternehmen ?Abteilung_gehoert_zu_Unternehmen . "
						+ "?Abteilung ontology:Abteilung_Name '" + splittedAbteilungString + "' . " + "}";

				}

				// Projekte
				if (y == inputArray.length) {
					if (personObj.getPerson_arbeitet_an_Projekt() != "") {

						String[] Person_arbeitet_an_ProjektStringArr = personObj.getPerson_arbeitet_an_Projekt()
							.toString().split(", ");
						String splittedProjektString = "";

						if (Person_arbeitet_an_ProjektStringArr.length > 0) {
							splittedProjektString = Person_arbeitet_an_ProjektStringArr[0];
						} else {
							splittedProjektString = personObj.getPerson_arbeitet_an_Projekt();
						}

						sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Projekt ?ProjektID ?ProjektName ?Projekt_gehoert_zu_Unternehmen "
							+ "?Projekt_gehoert_zu_Abteilung ?Projekt_hat_Projektmitglied ?Projekt_hat_Dokument "
							+ "WHERE { " + "?Projekt ontology:Projekt_ID ?ProjektID . "
							+ "?Projekt ontology:Projekt_Name ?ProjektName . "
							+ "?Projekt ontology:Projekt_gehoert_zu_Unternehmen ?Projekt_gehoert_zu_Unternehmen . "
							+ "?Projekt ontology:Projekt_gehoert_zu_Abteilung ?Projekt_gehoert_zu_Abteilung . "
							+ "?Projekt ontology:Projekt_hat_Projektmitglied ?Projekt_hat_Projektmitglied . "
							+ "?Projekt ontology:Projekt_hat_Dokument ?Projekt_hat_Dokument . "

							+ "?Projekt ontology:Projekt_Name '" + splittedProjektString + "' . " + "}";
					}

				}

				if (sparql != "") {

					// // Initialisierung und Ausführung einer SPARQL-Query
					// Query query = QueryFactory.create(sparql);
					// QueryExecution queryExecution =
					// QueryExecutionFactory.create(query, ontologyModel);
					// Initialisierung und Ausführung einer SPARQL-Query
					Query query = QueryFactory.create(sparql);
					// queryExecution = QueryExecutionFactory.create(query,
					// ontologyModel);
					QueryExecution queryExecution = QueryExecutionFactory
						.sparqlService(FusekiConfigConstants.FUSEKI_ADDRESS, query);

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

					outerLoop:
					for (@SuppressWarnings("unused")
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
							// abteilung
							if (y > 1 && y < 3) {
								// einmaliges befüllen der nachfolgenden Werte
								if (((abteilungObj.getAbteilung_ID() == "") == true)
									|| ((abteilungObj.getAbteilung_Name() == "") == true)
									|| ((abteilungObj.getAbteilung_Kuerzel() == "") == true)
									|| ((abteilungObj.getAbteilung_hat_Projekt() == "") == true)
									|| ((abteilungObj.getAbteilung_hat_Mitarbeiter() == "") == true)
									|| ((abteilungObj.getAbteilung_gehoert_zu_Unternehmen() == "") == true)) {

									switch (results) {
										case "Abteilung_ID":
											abteilung_IDStr = rdfNode.toString().substring(0,
												rdfNode.toString().indexOf("^^"));
											abteilungObj.setAbteilung_ID(abteilung_IDStr);
											break;
										case "Abteilung_Name":
											abteilung_NameStr = splitResult;
											abteilungObj.setAbteilung_Name(abteilung_NameStr);
											break;
										case "Abteilung_Kuerzel":
											abteilung_KuerzelStr = splitResult;
											abteilungObj.setAbteilung_Kuerzel(abteilung_KuerzelStr);
											break;
										case "Abteilung_hat_Projekt":
											abteilung_hat_ProjektStr = splitResult;
											abteilungObj.setAbteilung_hat_Projekt(abteilung_hat_ProjektStr);
											break;
										case "Abteilung_hat_Mitarbeiter":
											abteilung_hat_MitarbeiterStr = splitResult;
											abteilungObj.setAbteilung_hat_Mitarbeiter(abteilung_hat_MitarbeiterStr);
											break;
										case "Abteilung_gehoert_zu_Unternehmen":
											abteilung_gehoert_zu_UnternehmenStr = splitResult;
											abteilungObj.setAbteilung_gehoert_zu_Unternehmen(
												abteilung_gehoert_zu_UnternehmenStr);
											break;
									}

								}
								// befülle dynamisch Projekteattribute
								else if (((abteilungObj.getAbteilung_hat_Projekt() == "") == false)
									|| ((abteilungObj.getAbteilung_hat_Mitarbeiter() == "") == false)
									|| ((abteilungObj.getAbteilung_gehoert_zu_Unternehmen() == "") == false)) {

									switch (results) {
										case "Abteilung_hat_Projekt":
											abteilung_hat_ProjektStr = splitResult;
											splitKeywordsList = Arrays
												.asList(abteilungObj.getAbteilung_hat_Projekt().toString().split(", "));

											if (splitKeywordsList.contains(abteilung_hat_ProjektStr)) {

												break;

											} else {

												abteilungObj
													.setAbteilung_hat_Projekt(abteilungObj.getAbteilung_hat_Projekt()
														+ ", " + abteilung_hat_ProjektStr);
												break;
											}
										case "Abteilung_hat_Mitarbeiter":
											abteilung_hat_MitarbeiterStr = splitResult;
											splitKeywordsList = Arrays.asList(
												abteilungObj.getAbteilung_hat_Mitarbeiter().toString().split(", "));

											if (splitKeywordsList.contains(abteilung_hat_MitarbeiterStr)) {

												break;

											} else {

												abteilungObj.setAbteilung_hat_Mitarbeiter(
													abteilungObj.getAbteilung_hat_Mitarbeiter() + ", "
														+ abteilung_hat_MitarbeiterStr);
												break;
											}
										case "Abteilung_gehoert_zu_Unternehmen":
											abteilung_gehoert_zu_UnternehmenStr = splitResult;
											splitKeywordsList = Arrays.asList(abteilungObj
												.getAbteilung_gehoert_zu_Unternehmen().toString().split(", "));

											if (splitKeywordsList.contains(abteilung_gehoert_zu_UnternehmenStr)) {

												break;

											} else {

												abteilungObj.setAbteilung_gehoert_zu_Unternehmen(
													abteilungObj.getAbteilung_gehoert_zu_Unternehmen() + ", "
														+ abteilung_gehoert_zu_UnternehmenStr);
												break;
											}

									}

								}

							}
							// dokumente
							if (y >= 3 && y < inputArray.length) {

								// Dok_Name noch nicht vorgekommen?
								if (results.equals("Dok_Name")) {
									// System.out.println(results.length());

									if (!rememberDokNameArrList.contains(splitResult)
										&& rememberDokNameArrList.size() != 0) {

										// speichere das letzte
										// Dokumenten-Objekt ab
										richTokenHashMap.put("Dokument" + countDokOffersInLoop,
											Constants.PubSub.AttributeKey.DOCUMENT_ID + ":" + dokumentObj.getDok_IDStr()
												+ ", " + Constants.PubSub.AttributeKey.DOCUMENT_NAME + ":"
												+ dokumentObj.getDok_NameStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_URL + ":"
												+ dokumentObj.getDok_URLStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_ADDED + ":"
												+ dokumentObj.getDok_erstelldatumStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_VERSION + ":"
												+ dokumentObj.getDok_VersionStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_TYPE + ":"
												+ dokumentObj.getDok_TypStr() + ", "
												+ Constants.PubSub.AttributeKey.DOCUMENT_WRITTEN_BY + ":"
												+ dokumentObj.getDokument_verfasst_von_Person() + ", "
												+ Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT + ":"
												+ dokumentObj.getDokument_gehoert_zu_Projekt() + ", "
												+ Constants.PubSub.AttributeKey.DOCUMENT_FAVORED_BY + ":"
												+ dokumentObj.getDokument_favorisiert_von_Person() + ", "
												+ Constants.PubSub.AttributeKey.DOCUMENT_KEYWORDS + ":" + dokumentObj.getDokument_hat_Keyword() + ", "
												+ Constants.PubSub.AttributeKey.DOCUMENT_FOLDER + ":" + dokumentObj.getDok_folder());

										countDokOffersInLoop = countDokOffersInLoop + 1;

										// leere das Dokumenten-Objekt, um den
										// Speicher für das nächste Objekt frei
										// zu machen
										dokumentObj.flushDokumentObjekt();

									}

								}

								// einmaliges befüllen der nachfolgenden Werte
								if (((dokumentObj.getDok_NameStr() == "") == true)
									|| ((dokumentObj.getDok_IDStr() == "") == true)
									|| ((dokumentObj.getDok_URLStr() == "") == true)
									|| ((dokumentObj.getDok_erstelldatumStr() == "") == true)
									|| ((dokumentObj.getDok_VersionStr() == "") == true)
									|| ((dokumentObj.getDok_TypStr() == "") == true)
									|| ((dokumentObj.getDokument_verfasst_von_Person() == "") == true)
									|| ((dokumentObj.getDokument_gehoert_zu_Projekt() == "") == true)
									|| ((dokumentObj.getDokument_favorisiert_von_Person() == "") == true)
									|| ((dokumentObj.getDokument_hat_Keyword() == "") == true)
									|| ((dokumentObj.getDok_folder() == "") == true)) {

									switch (results) {
										case "Verfasser":
											dok_VerfasserStr = splitResult;
											dokumentObj.setDokument_verfasst_von_Person(dok_VerfasserStr);
											break;
										case "Projekt":
											dok_ProjektStr = splitResult;
											dokumentObj.setDokument_gehoert_zu_Projekt(dok_ProjektStr);
											gehoertZuProjekt = dok_ProjektStr;
											break;
										case "Dok_Name":
											dok_NameStr = splitResult;
											dokumentObj.setDok_NameStr(dok_NameStr);
											rememberDokNameArrList.add(countDokOffersInLoop, dok_NameStr);
											countDokNumber = countDokNumber + 1;
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
										case "Dok_Keywords":
											dok_KeywordsStr = splitResult;
											dokumentObj.setDokument_hat_Keyword(dok_KeywordsStr);
											break;
										case "Dok_Version":
											dok_VersionStr = rdfNode.toString().substring(0,
												rdfNode.toString().indexOf("^^"));
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
										case "Dok_Ordner":
											dok_folder = splitResult;
											dokumentObj.setDok_folder(dok_folder);
											break;
									}

								}
								// befülle dynamische Anzahl der Dokumente und
								// Aufrufe
								else if (((dokumentObj.getDokument_verfasst_von_Person() == "") == false)
									|| ((dokumentObj.getDokument_gehoert_zu_Projekt() == "") == false)
									|| ((dokumentObj.getDokument_hat_Keyword() == "") == false)
									|| ((dokumentObj.getDokument_favorisiert_von_Person() == "") == false)) {

									switch (results) {
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
										case "Projekt":
											dok_ProjektStr = splitResult;
											splitKeywordsList = Arrays.asList(
												dokumentObj.getDokument_gehoert_zu_Projekt().toString().split(", "));

											if (splitKeywordsList.contains(dok_ProjektStr)) {

												break;

											} else {

												dokumentObj.setDokument_gehoert_zu_Projekt(
													dokumentObj.getDokument_gehoert_zu_Projekt() + ", "
														+ dok_ProjektStr);
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
											splitKeywordsList = Arrays.asList(dokumentObj
												.getDokument_favorisiert_von_Person().toString().split(", "));

											if (splitKeywordsList.contains(dok_favorisiertVonString)) {

												break;

											} else {

												dokumentObj.setDokument_favorisiert_von_Person(
													dokumentObj.getDokument_favorisiert_von_Person() + ", "
														+ dok_favorisiertVonString);
												break;
											}

									}

								}

							} // Projekte
							if (y == inputArray.length) {

								// einmaliges befüllen der nachfolgenden Werte
								if (((projektObj.getProjektID() == "") == true)
									|| ((projektObj.getProjektName() == "") == true)
									|| ((projektObj.getProjekt_gehoert_zu_Unternehmen() == "") == true)
									|| ((projektObj.getProjekt_gehoert_zu_Abteilung() == "") == true)
									|| ((projektObj.getProjekt_hat_Projektmitglied() == "") == true)
									|| ((projektObj.getProjekt_hat_Dokument() == "") == true)) {

									switch (results) {
										case "ProjektID":
											projektIDStr = rdfNode.toString().substring(0,
												rdfNode.toString().indexOf("^^"));
											projektObj.setProjektID(projektIDStr);
											break;
										case "ProjektName":
											projektNameStr = splitResult;
											projektObj.setProjektName(projektNameStr);
											break;
										case "Projekt_gehoert_zu_Unternehmen":
											projekt_gehoert_zu_UnternehmenStr = splitResult;
											projektObj.setProjekt_gehoert_zu_Unternehmen(projekt_gehoert_zu_UnternehmenStr);
											break;
										case "Projekt_gehoert_zu_Abteilung":
											projekt_gehoert_zu_AbteilungStr = splitResult;
											projektObj.setProjekt_gehoert_zu_Abteilung(projekt_gehoert_zu_AbteilungStr);
											break;
										case "Projekt_hat_Projektmitglied":
											projekt_hat_ProjektmitgliedStr = splitResult;
											projektObj.setProjekt_hat_Projektmitglied(projekt_hat_ProjektmitgliedStr);
											break;
										case "Projekt_hat_Dokument":
											projekt_hat_DokumentStr = splitResult;
											projektObj.setProjekt_hat_Dokument(projekt_hat_DokumentStr);
											break;
									}

								}
								// befülle dynamisch Projekteattribute
								else if (((projektObj.getProjekt_gehoert_zu_Unternehmen() == "") == false)
									|| ((projektObj.getProjekt_gehoert_zu_Abteilung() == "") == false)
									|| ((projektObj.getProjekt_hat_Projektmitglied() == "") == false)
									|| ((projektObj.getProjekt_hat_Dokument() == "") == false)) {

									switch (results) {
										case "Projekt_gehoert_zu_Unternehmen":
											projekt_gehoert_zu_UnternehmenStr = splitResult;
											splitKeywordsList = Arrays.asList(
												projektObj.getProjekt_gehoert_zu_Unternehmen().toString().split(", "));

											if (splitKeywordsList.contains(projekt_gehoert_zu_UnternehmenStr)) {

												break;

											} else {

												projektObj.setProjekt_gehoert_zu_Unternehmen(
													projektObj.getProjekt_gehoert_zu_Unternehmen() + ", "
														+ projekt_gehoert_zu_UnternehmenStr);
												break;
											}
										case "Projekt_gehoert_zu_Abteilung":
											projekt_gehoert_zu_AbteilungStr = splitResult;
											splitKeywordsList = Arrays.asList(
												projektObj.getProjekt_gehoert_zu_Abteilung().toString().split(", "));

											if (splitKeywordsList.contains(projekt_gehoert_zu_AbteilungStr)) {

												break;

											} else {

												projektObj.setProjekt_gehoert_zu_Abteilung(
													projektObj.getProjekt_gehoert_zu_Abteilung() + ", "
														+ projekt_gehoert_zu_AbteilungStr);
												break;
											}
										case "Projekt_hat_Projektmitglied":
											projekt_hat_ProjektmitgliedStr = splitResult;
											splitKeywordsList = Arrays.asList(
												projektObj.getProjekt_hat_Projektmitglied().toString().split(", "));

											if (splitKeywordsList.contains(projekt_hat_ProjektmitgliedStr)) {

												break;

											} else {

												projektObj.setProjekt_hat_Projektmitglied(
													projektObj.getProjekt_hat_Projektmitglied() + ", "
														+ projekt_hat_ProjektmitgliedStr);
												break;
											}
										case "Projekt_hat_Dokument":
											projekt_hat_DokumentStr = splitResult;
											splitKeywordsList = Arrays
												.asList(projektObj.getProjekt_hat_Dokument().toString().split(", "));

											if (splitKeywordsList.contains(projekt_hat_DokumentStr)) {

												break;

											} else {

												projektObj.setProjekt_hat_Dokument(projektObj.getProjekt_hat_Dokument()
													+ ", " + projekt_hat_DokumentStr);
												break;
											}

									}

								}

							}

						} //end inner loop

					} // end outer loop

					// bei Person
					if (y == 1) {

						richTokenHashMap.put("Person",
							Constants.PubSub.AttributeKey.USER_ID + ":" + personObj.getId() + ", " + Constants.PubSub.AttributeKey.FIRST_NAME + ":" + personObj.getVorname() + ", "
								+ Constants.PubSub.AttributeKey.LAST_NAME + ":" + personObj.getNachname() + ", " + Constants.PubSub.AttributeKey.EMAIL + ":" + personObj.getMail()
								+ ", " + Constants.PubSub.AttributeKey.USER_WORKS_ON_PROJECTS + ":" + personObj.getPerson_arbeitet_an_Projekt() + ", "
								+ Constants.PubSub.AttributeKey.USER_HAS_PROJECTROLE + ":" + personObj.getPerson_hat_Projektrolle() + ", " + Constants.PubSub.AttributeKey.USER_BELONGS_TO_DEPARTMENT + ":"
								+ personObj.getPerson_gehoert_zu_Abteilung() + ", " + Constants.PubSub.AttributeKey.USER_WRITES_DOCUMENT + ":"
								+ personObj.getPerson_hat_Dokument_verfasst() + ", " + Constants.PubSub.AttributeKey.USER_CALLS_DOCUMENT + ":"
								+ personObj.getPerson_ruft_Dokument_auf() + ", " + Constants.PubSub.AttributeKey.USER_FAVOURS_DOCUMENT + ":"
								+ personObj.getPerson_favorisiert_Dokument());

					}
					// bei Abteilung
					if (y > 1 && y < 3) {

						richTokenHashMap.put("Abteilung", Constants.PubSub.AttributeKey.DEPARTMENT_ID + ":" + abteilungObj.getAbteilung_ID() + ", "
							+ Constants.PubSub.AttributeKey.DEPARTMENT_NAME + ":" + abteilungObj.getAbteilung_Name() + ", " + Constants.PubSub.AttributeKey.DEPARTMENT_SHORT + ":"
							+ abteilungObj.getAbteilung_Kuerzel() + ", " + Constants.PubSub.AttributeKey.DEPARTMENT_HAS_PROJECT + ":"
							+ abteilungObj.getAbteilung_hat_Projekt() + ", " + Constants.PubSub.AttributeKey.DEPARTMENT_HAS_WORKER + ":"
							+ abteilungObj.getAbteilung_hat_Mitarbeiter() + ", " + Constants.PubSub.AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY + ":"
							+ abteilungObj.getAbteilung_gehoert_zu_Unternehmen());

						abteilungObj.flushAbteilungsObjekt();

					}
					// bei Projekt
					if (y == inputArray.length) {

						richTokenHashMap.put("Projekt",
							Constants.PubSub.AttributeKey.PROJECT_ID + ":" + "=" + projektObj.getProjektID() + ", "
								+ Constants.PubSub.AttributeKey.PROJECT_NAME + ":" + projektObj.getProjektName() + ", "
								+ Constants.PubSub.AttributeKey.PROJECT_BELONGS_TO_COMPANY + ":"
								+ projektObj.getProjekt_gehoert_zu_Unternehmen() + ", " + Constants.PubSub.AttributeKey.PROJECT_BELONGS_TO_DEPARTMENT + ":"
								+ projektObj.getProjekt_gehoert_zu_Abteilung() + ", " + Constants.PubSub.AttributeKey.PROJECT_HAS_MEMBERS + ":"
								+ projektObj.getProjekt_hat_Projektmitglied() + ", " + Constants.PubSub.AttributeKey.PROJECT_HAS_DOCUMENTS + ":"
								+ projektObj.getProjekt_hat_Dokument());

						projektObj.flushProjektObjekt();

					}

					// letztes Dokument-Objekt
					if (rememberDokNameArrList.size() != 0 && rememberDokNameArrList.size() == countDokNumber) {
						// speichere das letzte Dokumenten-Objekt ab
						richTokenHashMap.put("Dokument" + countDokOffersInLoop,
							Constants.PubSub.AttributeKey.DOCUMENT_ID + ":" + dokumentObj.getDok_IDStr()
								+ ", " + Constants.PubSub.AttributeKey.DOCUMENT_NAME + ":"
								+ dokumentObj.getDok_NameStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_URL + ":"
								+ dokumentObj.getDok_URLStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_ADDED + ":"
								+ dokumentObj.getDok_erstelldatumStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_VERSION + ":"
								+ dokumentObj.getDok_VersionStr() + ", " + Constants.PubSub.AttributeKey.DOCUMENT_TYPE + ":"
								+ dokumentObj.getDok_TypStr() + ", "
								+ Constants.PubSub.AttributeKey.DOCUMENT_WRITTEN_BY + ":"
								+ dokumentObj.getDokument_verfasst_von_Person() + ", "
								+ Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT + ":"
								+ dokumentObj.getDokument_gehoert_zu_Projekt() + ", "
								+ Constants.PubSub.AttributeKey.DOCUMENT_FAVORED_BY + ":"
								+ dokumentObj.getDokument_favorisiert_von_Person() + ", "
								+ Constants.PubSub.AttributeKey.DOCUMENT_KEYWORDS + ":" + dokumentObj.getDokument_hat_Keyword() + ", "
								+ Constants.PubSub.AttributeKey.DOCUMENT_FOLDER + ":" + dokumentObj.getDok_folder());

						anzahlDokumente = (countDokOffersInLoop + 1);

						// leere das Dokumenten-Objekt, um den
						// Speicher für das nächste Objekt frei zu machen
						dokumentObj.flushDokumentObjekt();
					}

					queryExecution.close();

				}
			}

		} catch (Exception e) {
			loggger.error("Fehler in TokenizerInterface: " + e);
		}

		// // drucke alles im richTokenHashMap aus
		// for (String key : richTokenHashMap.keySet()) {
		// System.out.println(key + ": " + richTokenHashMap.get(key));
		// }

		return richTokenHashMap;

	}

}
