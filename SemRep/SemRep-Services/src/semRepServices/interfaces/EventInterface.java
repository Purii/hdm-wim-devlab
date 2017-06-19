package semRepServices.interfaces;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
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

import semRepServices.businessObjects.Dokumentvorschlag;
import semRepServices.businessObjects.Person;

public class EventInterface {
	
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
	
	// ### initialisiere globale Objekte
	private static Dokumentvorschlag dokumentvorschlagObj = null;
	private static Person personObj = null;
	public static Person personFavDokObj = null;
	
	// ### initialisiere globale Variablen
	// inputArray
	//public static String[] inputArray = null;
	// Dokument-Objekt bezogen
	private static String sessionIDStr = "";
	public static String eventUniqueID = "";
	private static String timeStampStr = "";
	private static String dok_IDStr = "";
	private static String dok_NameStr = "";
	private static String prioStr = "";
	private static String dok_TypStr = "";
	private static String dok_URLStr = "";
	private static String dok_folder = "";
	//FavoritDok-Objekt bezogen
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
	
	public static void main(String[] args) {
		produceUserInformationEvent();
	}
	
	public static void initializeInputArray(){
		
	}
	
	public static void executeSparql(String sparql){
		// Initialisierung und Ausführung einer SPARQL-Query
		Query query = QueryFactory.create(sparql);
		queryExecution = QueryExecutionFactory.create(query, ontologyModel);

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

				if ((y == 0 || y == 2 || y == 3) && eventType == "DocumentInformationEvent") {

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
				// ermittle das favorisierte Dokument zu einer Person
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
								splitKeywordsList = Arrays.asList(
										personObj.getPerson_arbeitet_an_Projekt().toString().split(", "));

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
								splitKeywordsList = Arrays.asList(
										personObj.getPerson_gehoert_zu_Abteilung().toString().split(", "));

								if (splitKeywordsList.contains(abteilungStr)) {

									break;

								} else {

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
								splitKeywordsList = Arrays.asList(
										personObj.getPerson_favorisiert_Dokument().toString().split(", "));

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
			if (y == 0) {
				// prio bei allen Dokumenten irrelevant (0)
				prioStr = "0";
				dokumentvorschlagObj.setPrio(prioStr);
				dokOfferLinkedHashMap.put("Dokument" + i, "SessionID=" + dokumentvorschlagObj.getSessionID() + ", "
						+ "TimeStamp=" + dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
						+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
						+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio=" + dokumentvorschlagObj.getPrio()
						+ ", " + "DokTyp=" + dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
						+ dokumentvorschlagObj.getDok_URLStr() + ", " + "DokOrdner="
						+ dokumentvorschlagObj.getDok_folder());

				dokumentvorschlagObj.flushDokumentvorschlag();
			} else if (y == 1 && eventType == "UserInformationEvent") {
				// bei Person
				eventLinkedHashMap.put("Person",
						"UserID=" + personObj.getId() + ", " + "Vorname=" + personObj.getVorname() + ", "
								+ "Nachname=" + personObj.getNachname() + ", " + "Mail=" + personObj.getMail()
								+ ", " + "Projekt=" + personObj.getPerson_arbeitet_an_Projekt() + ", "
								+ "Projektrolle=" + personObj.getPerson_hat_Projektrolle() + ", " + "Abteilung="
								+ personObj.getPerson_gehoert_zu_Abteilung() + ", " + "DokAutor="
								+ personObj.getPerson_hat_Dokument_verfasst() + ", " + "DokAufrufe="
								+ personObj.getPerson_ruft_Dokument_auf() + ", " + "DokFavorit="
								+ personObj.getPerson_favorisiert_Dokument());

			} else if (y == 3) {
				// prio bei Dokumentvorschlägen relevant (1)
				prioStr = "1";
				dokumentvorschlagObj.setPrio(prioStr);
				dokOfferHashMap.put("Dokumentvorschlag_" + countDokOffersInLoop, "SessionID="
						+ dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
						+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID=" + dokumentvorschlagObj.getDok_IDStr()
						+ ", " + "DokName=" + dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
						+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp=" + dokumentvorschlagObj.getDok_TypStr()
						+ ", " + "DokURL=" + dokumentvorschlagObj.getDok_URLStr() + ", " + "DokOrdner="
						+ dokumentvorschlagObj.getDok_folder());

				countDokOffersInLoop = countDokOffersInLoop + 1;
				dokumentvorschlagObj.flushDokumentvorschlag();
			}

		}

		queryExecution.close();

		return eventLinkedHashMap;

	}
	
	public static String produceUserInformationEvent() {

		eventLinkedHashMap = new LinkedHashMap<String, String>();

		timestamp = new Timestamp(System.currentTimeMillis());
		timeStampStr = timestamp.toString();
		
		String eventTypeStr = "UserInformationEvent";
		String[] inputArray = InitializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();
		eventUniqueID = UUID.randomUUID().toString();

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";

			// initialisiere Objekte
			//person
			personObj = new Person(personStr, idStr, klasseStr, vornameStr, nachnameStr, 
					mailStr, projektStr, projektrolleStr, abteilungStr, dokumentStr, aufrufStr, favoritStr);
			
			for (int z = 0; z <= inputArray.length; z++) {

				//ermittle UserInformation
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
								//+ "?Favorit_Dok ontology:Dok_Name ?Dok_Name . "
								// Eingrenzung auf userID
								+ "?Person ontology:Person_ID '" + inputArray[z].toString() + "' . " + "}";
			
				}
			
				if(sparql != ""){
					
					executeSparql(sparql);
					eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
						
				}
			
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		personObj.flushPersonObj();
		
		Person userInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("Person")) {
				userInformationEventObject = new Person(sessionIDStr, eventUniqueID, eventLinkedHashMap.get(key).toString());
				System.out.println(userInformationEventObject.toStringPersonObjekt());
			}

		}
		return personObj.toStringPersonObjekt();

	}

	public static String produceDocumentInformationEvent() {

		eventLinkedHashMap = new LinkedHashMap<String, String>();
		
		String eventTypeStr = "DocumentInformationEvent";
		String[] inputArray = InitializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();
		eventUniqueID = UUID.randomUUID().toString();

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";

			// initialisiere Objekte
			//person
			personObj = new Person(personStr, idStr, klasseStr, vornameStr, nachnameStr, 
					mailStr, projektStr, projektrolleStr, abteilungStr, dokumentStr, aufrufStr, favoritStr);
			
			for (int z = 0; z <= inputArray.length; z++) {

				//ermittle UserInformation
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
								//+ "?Favorit_Dok ontology:Dok_Name ?Dok_Name . "
								// Eingrenzung auf userID
								+ "?Person ontology:Person_ID '" + inputArray[z].toString() + "' . " + "}";
			
				}
			
				if(sparql != ""){
					
					executeSparql(sparql);
					eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
						
				}
			
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		personObj.flushPersonObj();
		
		Person userInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("Person")) {
				userInformationEventObject = new Person(sessionIDStr, eventUniqueID, eventLinkedHashMap.get(key).toString());
				System.out.println(userInformationEventObject.toStringPersonObjekt());
			}

		}
		return personObj.toStringPersonObjekt();

	}
	
}
