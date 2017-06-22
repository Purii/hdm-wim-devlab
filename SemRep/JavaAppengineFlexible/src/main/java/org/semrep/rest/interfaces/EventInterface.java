package main.java.org.semrep.rest.interfaces;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.utils.URLEncodedUtils;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.appengine.labs.repackaged.org.json.JSONException;

import main.java.org.semrep.rest.businessObjects.Abteilung;
import main.java.org.semrep.rest.businessObjects.Dokument;
import main.java.org.semrep.rest.businessObjects.Dokumentvorschlag;
import main.java.org.semrep.rest.businessObjects.Person;
import main.java.org.semrep.rest.businessObjects.Projekt;
import main.java.org.semrep.rest.businessObjects.Unternehmen;

@Path("/eventInterface")
public class EventInterface {

	// Constants constant = new Constants();

	private static JSONObject jsonObj;
	private static Logger loggger = Logger.getLogger(Main.class.getName());

	// ### initialisiere globale Jena-Variablen
	public static String filePath = "src/semRepServices/interfaces/Ontology.owl";
	public static OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
	public static ResultSet resultSet;
	public static QueryExecution queryExecution;

	// ### initialisiere globale HashMaps
	private static LinkedHashMap<String, String> eventLinkedHashMap = null;

	public static LinkedHashMap<String, String> dokOfferLinkedHashMap = null;
	public static LinkedHashMap<String, String> alleDokumenteLinkedHashMap = null;
	public static HashMap<String, String> dokOfferHashMap = null;
	public static HashMap<String, String> tmpDokOfferHashMap = null;
	public static HashMap<String, String> favDokHashMap = null;
	public static HashMap<String, String> tmpFavDokHashMap = null;

	// ### time stamp
	private static Timestamp timestamp = null;
	private static long timestampLong;

	// ### initialisiere globale Objekte
	private static Person personObj = null;
	public static Person personFavDokObj = null;
	public static Dokument dokumentObj = null;
	public static Projekt projektObj = null;
	public static Abteilung abteilungObj = null;
	public static Unternehmen unternehmenObj = null;

	// ### initialisiere globale Variablen
	// Standard Variablen
	private static String sessionIDStr = "";
	public static String eventUniqueID = "'null'";
	private static String timeStampStr = "";
	// Dokument-Objekt bezogen
	private static String dok_IDStr = "";
	private static String dok_NameStr = "";
	private static String prioStr = "";
	private static String dok_TypStr = "";
	private static String dok_URLStr = "";
	private static String dok_folder = "";
	private static String dok_Str = "";
	private static String dok_KlasseStr = "";
	private static String dok_erstelldatumStr = "";
	private static String dok_UpdatedatumStr = "";
	private static String dok_VersionStr = "";
	private static String dok_VerfasserStr = "";
	private static String dok_PhaseStr = "";
	private static String dok_kategorieStr = "";
	private static String dok_ProjektStr = "";
	private static String dok_favorisiertVonString = "";
	private static String dok_Kontext = "";
	private static String dok_KeywordsStr = "";
	// FavoritDok-Objekt bezogen
	private static String personVorname_Str = "";
	private static String personNachname_Str = "";
	private static String personName_Str = "";
	private static int numFavDoks = 0;
	// person
	private static String personStr = "";
	private static String idStr = "";
	private static String klasseStr = "";
	private static String vornameStr = "";
	private static String nachnameStr = "";
	private static String mailStr = "";
	private static String projektStr = "";
	private static String projektrolleStr = "";
	private static String abteilungStr = "";
	private static String dokumentStr = "";
	private static String aufrufStr = "";
	private static String favoritStr = "";
	// projekt
	private static String projektIDStr = "";
	private static String projektNameStr = "";
	private static String projekt_gehoert_zu_UnternehmenStr = "";
	private static String projekt_gehoert_zu_AbteilungStr = "";
	private static String projekt_hat_ProjektmitgliedStr = "";
	private static String projekt_hat_DokumentStr = "";
	// abteilung
	private static String abteilung_IDStr = "";
	private static String abteilung_NameStr = "";
	private static String abteilung_KuerzelStr = "";
	private static String abteilung_hat_ProjektStr = "";
	private static String abteilung_hat_MitarbeiterStr = "";
	private static String abteilung_gehoert_zu_UnternehmenStr = "";
	// unternehmen
	private static String unternehmensNameStr = "";

	private static InitializeArrayData initializeArrayData = new InitializeArrayData();

	public static void main(String[] args) {
		// produceUserInformationEvent();
		try {
			produceDocumentInformationEvent();
		} catch (JsonProcessingException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void executeSparql(String sparql) {
		// Initialisierung und Ausführung einer SPARQL-Query
		// Query query = QueryFactory.create(sparql);
		// queryExecution = QueryExecutionFactory.create(query, ontologyModel);

		Query query = QueryFactory.create(sparql);
		// queryExecution = QueryExecutionFactory.create(query,
		// ontologyModel);
		queryExecution = QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/20170621newOntology/query",
				query);

		// Initialisierung von Resultset für Ergebniswerte der SPARQL-Query
		resultSet = queryExecution.execSelect();
	}

	public static LinkedHashMap<String, String> loopThroughResults(int y, String eventType) {

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

				if (y == 1 && eventType == "DocumentInformationEvent") {

					// einmaliges befüllen der nachfolgenden Werte
					if (((dokumentObj.getDok_IDStr() == "") == true) || ((dokumentObj.getDok_NameStr() == "") == true)
							|| ((dokumentObj.getDok_TypStr() == "") == true)
							|| ((dokumentObj.getDok_URLStr() == "") == true)
							|| ((dokumentObj.getDok_folder() == "") == true)) {

						switch (results) {
						case "Dok_ID":
							dok_IDStr = splitResult;
							dokumentObj.setDok_IDStr(dok_IDStr);
							break;
						case "Dok_Name":
							dok_NameStr = splitResult;
							dokumentObj.setDok_NameStr(dok_NameStr);
							break;
						case "Dok_Typ":
							dok_TypStr = splitResult;
							dokumentObj.setDok_TypStr(dok_TypStr);
							break;
						case "Dok_URL":
							dok_URLStr = splitResult;
							dokumentObj.setDok_URLStr(dok_URLStr);
							break;
						case "Dok_Ordner":
							dok_folder = splitResult;
							dokumentObj.setDok_folder(dok_folder);
							break;
						}

					}

				}
				// ermittle das favorisierte Dokument zu einer Person
				if (y == 1 && eventType == "UserInformationEvent") {

					// einmaliges befüllen der nachfolgenden Werte
					if (((personObj.getPerson() == "") == true) || ((personObj.getId() == "") == true)
							|| ((personObj.getKlasse() == "") == true) || ((personObj.getVorname() == "") == true)
							|| ((personObj.getNachname() == "") == true) || ((personObj.getMail() == "") == true)
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
					else if (((personObj.getPerson_hat_Dokument_verfasst() == "") == false)
							|| ((personObj.getPerson_ruft_Dokument_auf() == "") == false)
							|| ((personObj.getPerson_arbeitet_an_Projekt() == "") == false)
							|| ((personObj.getPerson_hat_Projektrolle() == "") == false)
							|| ((personObj.getPerson_gehoert_zu_Abteilung() == "") == false)
							|| ((personObj.getKlasse() == "") == false)
							|| ((personObj.getPerson_favorisiert_Dokument() == "") == false)) {

						switch (results) {
						case "Klasse":
							klasseStr = splitResult;
							splitKeywordsList = Arrays.asList(personObj.getKlasse().toString().split(", "));

							if (splitKeywordsList.contains(klasseStr)) {

								break;

							} else {

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

								personObj.setPerson_favorisiert_Dokument(
										personObj.getPerson_favorisiert_Dokument() + ", " + favoritStr);
								break;
							}
						}

					}

				}

			}

			// ablegen eines vollständigen Objekts in einer der entsprechenden
			// Maps
			if (y == 1 && eventType == "DocumentInformationEvent") {
				
				dokumentObj.setPrio("0"); 
				// Dokumente
				eventLinkedHashMap.put("Dokument",
						"DokID=" + dokumentObj.getDok_IDStr() + ", " + "DokName=" + dokumentObj.getDok_NameStr() 
								+ ", " + "DokPrio=" + dokumentObj.getPrio() 
								+ ", " + "DokTyp=" + dokumentObj.getDok_TypStr() 
								+ ", " + "DokURL=" + dokumentObj.getDok_URLStr() 
								+ ", " + "DokOrdner=" + dokumentObj.getDok_folder());

			} else if (y == 1 && eventType == "UserInformationEvent") {
				// bei Person
				eventLinkedHashMap.put("Person",
						"UserID=" + personObj.getId() + ", " + "Vorname=" + personObj.getVorname() + ", " + "Nachname="
								+ personObj.getNachname() + ", " + "Mail=" + personObj.getMail() + ", " + "Projekt="
								+ personObj.getPerson_arbeitet_an_Projekt() + ", " + "Projektrolle="
								+ personObj.getPerson_hat_Projektrolle() + ", " + "Abteilung="
								+ personObj.getPerson_gehoert_zu_Abteilung() + ", " + "DokAutor="
								+ personObj.getPerson_hat_Dokument_verfasst() + ", " + "DokAufrufe="
								+ personObj.getPerson_ruft_Dokument_auf() + ", " + "DokFavorit="
								+ personObj.getPerson_favorisiert_Dokument());

			} else if (y == 3) {
				// Abteilung
				// dokOfferHashMap.put("Dokumentvorschlag_" +
				// countDokOffersInLoop, "SessionID="
				// + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
				// + dokumentvorschlagObj.getTimeStamp() + ", " + "DokID=" +
				// dokumentvorschlagObj.getDok_IDStr()
				// + ", " + "DokName=" + dokumentvorschlagObj.getDok_NameStr() +
				// ", " + "DokPrio="
				// + dokumentvorschlagObj.getPrio() + ", " + "DokTyp=" +
				// dokumentvorschlagObj.getDok_TypStr()
				// + ", " + "DokURL=" + dokumentvorschlagObj.getDok_URLStr() +
				// ", " + "DokOrdner="
				// + dokumentvorschlagObj.getDok_folder());
				//
				// countDokOffersInLoop = countDokOffersInLoop + 1;
				// dokumentvorschlagObj.flushDokumentvorschlag();
			}

		}

		queryExecution.close();

		return eventLinkedHashMap;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/produceUserInformationEvent")
	public static Response produceUserInformationEvent() {

		// @Path: /rest/eventInterface/produceUserInformationEvent

		jsonObj = new JSONObject();

		eventLinkedHashMap = new LinkedHashMap<String, String>();

		// timestamp = new Timestamp(System.currentTimeMillis());
		timestamp = new Timestamp(System.currentTimeMillis());
		timestampLong = timestamp.getTime();

		String eventTypeStr = "UserInformationEvent";
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();

		try {
			// initialisiere Variablen
			// sparql
			String sparql = "";

			// initialisiere Objekte
			// person
			personObj = new Person(personStr, idStr, klasseStr, vornameStr, nachnameStr, mailStr, projektStr,
					projektrolleStr, abteilungStr, dokumentStr, aufrufStr, favoritStr);

			for (int z = 0; z < inputArray.length; z++) {

				// ermittle UserInformation
				if (z == 1) {

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
							+ "?Person ontology:Person_ID '" + inputArray[z].toString() + "' . " + "}";

				}

				if (sparql != "") {

					executeSparql(sparql);

					if (resultSet.hasNext() == true) {
						eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
					} else {
						personObj.setId("'null'");
						personObj.setVorname("'null'");
						personObj.setNachname("'null'");
						personObj.setMail("'null'");
						personObj.setPerson_arbeitet_an_Projekt("'null'");
						personObj.setPerson_hat_Projektrolle("'null'");
						personObj.setPerson_gehoert_zu_Abteilung("'null'");
						personObj.setPerson_hat_Dokument_verfasst("'null'");
						personObj.setPerson_ruft_Dokument_auf("'null'");
						personObj.setPerson_favorisiert_Dokument("'null'");

						// eventLinkedHashMap.put("Person",
						// "UserID=" + personObj.getId() + ", " + "Vorname=" +
						// personObj.getVorname() + ", "
						// + "Nachname=" + personObj.getNachname() + ", " +
						// "Mail=" + personObj.getMail()
						// + ", " + "Projekt=" +
						// personObj.getPerson_arbeitet_an_Projekt() + ", "
						// + "Projektrolle=" +
						// personObj.getPerson_hat_Projektrolle() + ", " +
						// "Abteilung="
						// + personObj.getPerson_gehoert_zu_Abteilung() + ", " +
						// "DokAutor="
						// + personObj.getPerson_hat_Dokument_verfasst() + ", "
						// + "DokAufrufe="
						// + personObj.getPerson_ruft_Dokument_auf() + ", " +
						// "DokFavorit="
						// + personObj.getPerson_favorisiert_Dokument());

					}

				}

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

		personObj.flushPersonObj();

		Person userInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("Person")) {
				userInformationEventObject = new Person(sessionIDStr, String.valueOf(timestampLong), eventUniqueID,
						eventLinkedHashMap.get(key).toString());
				System.out.println(userInformationEventObject.toStringPersonObjekt());
			}

		}

		// return personObj.toStringPersonObjekt();
		jsonObj.put("UserInformationEvent", userInformationEventObject.toStringUserInformationEvent());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/produceDocumentInformationEvent")
	public static Response produceDocumentInformationEvent() throws JSONException, JsonProcessingException {

		// @Path: /rest/eventInterface/produceDocumentInformationEvent
		
		jsonObj = new JSONObject();

		eventLinkedHashMap = new LinkedHashMap<String, String>();

		// timestamp = new Timestamp(System.currentTimeMillis());
		timestamp = new Timestamp(System.currentTimeMillis());
		timestampLong = timestamp.getTime();

		String eventTypeStr = "DocumentInformationEvent";
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();

		try {
			// initialisiere Variablen
			// sparql
			String sparql = "";

			String prioStr = "0";
			// initialisiere Objekte
			// dokument
			dokumentObj = new Dokument(dok_IDStr, dok_NameStr, prioStr, dok_TypStr, dok_URLStr, dok_folder);

			for (int z = 0; z < inputArray.length; z++) {

				// ermittle UserInformation
				if (z == 1) {

					String dokID = inputArray[z].toString();

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Dokument ?Dok_ID ?Dok_Name ?Dok_Typ ?Dok_URL ?Dok_Ordner " 
							+ "WHERE { "
							+ "?Dokument ontology:Dok_ID ?Dok_ID . "
							+ "?Dokument ontology:Dok_Name ?Dok_Name . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . "
							+ "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
							// Eingrenzung auf userID
							// + "?Dokument ontology:Dok_ID '" +
							// inputArray[z].toString() + "' . " + "}";
							+ " Filter (?Dok_ID = \"" + dokID + "\" ) . "
							// + "FILTER regex(str(?Dok_ID), '" + inputArray[z]
							// + "') . "
							+ "}";

				}

				if (sparql != "") {

					ClassLoader loader = URLEncodedUtils.class.getClassLoader();
					loggger.info(loader.getResource("org.apache.http.client.utils‌​.URLEncodedUtils.cla‌​ss"));
					executeSparql(sparql);

					if (resultSet.hasNext() == true) {
						eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
					} else {
						dokumentObj.setDok_IDStr("'null'");
						dokumentObj.setPrio("0");
						dokumentObj.setDok_NameStr("'null'");
						dokumentObj.setDok_TypStr("'null'");
						dokumentObj.setDok_URLStr("'null'");
						dokumentObj.setDok_folder("'null'");

						eventLinkedHashMap.put("Dokument",
								"DokID=" + dokumentObj.getDok_IDStr() + ", " + "DokName=" + dokumentObj.getDok_NameStr() 
										+ ", " + "DokPrio=" + dokumentObj.getPrio() 
										+ ", " + "DokTyp=" + dokumentObj.getDok_TypStr() 
										+ ", " + "DokURL=" + dokumentObj.getDok_URLStr() 
										+ ", " + "DokOrdner=" + dokumentObj.getDok_folder());

						dokumentObj.flushDokumentObjekt();
					}

				}

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

		Dokument documentInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("Dokument")) {
				documentInformationEventObject = new Dokument(sessionIDStr, String.valueOf(timestampLong),
						eventUniqueID, eventLinkedHashMap.get(key).toString());
				System.out.println(documentInformationEventObject.toStringDokumentObjekt());
				break;
			}

		}

		// return personObj.toStringPersonObjekt();
		jsonObj.put("DocumentInformationEvent", documentInformationEventObject.toStringDokumentObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

}