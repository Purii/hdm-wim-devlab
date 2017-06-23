package org.semrep.rest.interfaces;

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

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
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
/*
import com.google.appengine.labs.repackaged.org.json.JSONException;
*/

import org.semrep.rest.businessObjects.*;

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
	private static Projektrolle projektRolleObj = null;
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
	// projektrolle
	private static String rollenBezeichnungStr = "";

	private static InitializeArrayData initializeArrayData = new InitializeArrayData();

	public static void main(String[] args) {
		// produceUserInformationEvent();
		try {
			getAllProjectRoles();
		} catch (Exception e) {
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
				// ermittle Person-Infos
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
					// befülle dynamische Person-Infos
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

				} // befülle Projekt-Infos
				if (y == 1 && eventType == "ProjectInformationEvent") {

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

					} // befülle dynamisch Projekteattribute
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
				// abteilung
				if (y == 1 && eventType == "DepartmentInformationEvent") {
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
				// AllProjects
				if (y == 0 && eventType == "AllProjectsEvent") {

					if (((projektObj.getProjektName() == "") == true)) {

						switch (results) {
							case "ProjektName":
								projektNameStr = splitResult;
								projektObj.setProjektName(projektNameStr);
								break;
						}

					} else if (((projektObj.getProjektName() == "") == false)) {

						switch (results) {
							case "ProjektName":
								projektNameStr = splitResult;
								splitKeywordsList = Arrays.asList(
									projektObj.getProjektName().toString().split(", "));

								if (splitKeywordsList.contains(projektNameStr)) {

									break;

								} else {

									projektObj.setProjektName(
										projektObj.getProjektName() + ", "
											+ projektNameStr);
									break;
								}
						}

					}

				}
				// AllProjectRoles
				if (y == 0 && eventType == "AllProjectRolesEvent") {

					if (((projektRolleObj.getProjektrolle() == "") == true)) {

						switch (results) {
							case "Bezeichnung":
								projektrolleStr = splitResult;
								projektRolleObj.setProjektrolle(projektrolleStr);
								break;
						}

					} else if (((projektRolleObj.getProjektrolle() == "") == false)) {

						switch (results) {
							case "Bezeichnung":
								projektrolleStr = splitResult;
								splitKeywordsList = Arrays.asList(
									projektRolleObj.getProjektrolle().toString().split(", "));

								if (splitKeywordsList.contains(projektrolleStr)) {

									break;

								} else {

									projektRolleObj.setProjektrolle(
										projektRolleObj.getProjektrolle() + ", "
											+ projektrolleStr);
									break;
								}
						}

					}

				}
				// AllDepartments
				if (y == 0 && eventType == "AllDepartmentsEvent") {

					if (((abteilungObj.getAbteilung_Name() == "") == true)) {

						switch (results) {
							case "Abteilung_Name":
								abteilung_NameStr = splitResult;
								abteilungObj.setAbteilung_Name(abteilung_NameStr);
								break;
						}

					} else if (((abteilungObj.getAbteilung_Name() == "") == false)) {

						switch (results) {
							case "Abteilung_Name":
								abteilung_NameStr = splitResult;
								splitKeywordsList = Arrays
									.asList(abteilungObj.getAbteilung_Name().toString().split(", "));

								if (splitKeywordsList.contains(abteilung_NameStr)) {

									break;

								} else {

									abteilungObj
										.setAbteilung_Name(abteilungObj.getAbteilung_Name()
											+ ", " + abteilung_NameStr);
									break;
								}
						}

					}

				}
				// AllCompanies
				if (y == 0 && eventType == "AllCompaniesEvent") {

				}

			}
			// end of inner loop

			// fill HashMap
			if (y == 1 && eventType == "DocumentInformationEvent") {

				dokumentObj.setPrio("0");
				// Dokumente
				eventLinkedHashMap.put("Dokument",
					"DokID=" + dokumentObj.getDok_IDStr() + ", " + "DokName=" + dokumentObj.getDok_NameStr() + ", "
						+ "DokPrio=" + dokumentObj.getPrio() + ", " + "DokTyp=" + dokumentObj.getDok_TypStr()
						+ ", " + "DokURL=" + dokumentObj.getDok_URLStr() + ", " + "DokOrdner="
						+ dokumentObj.getDok_folder());

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

			} else if (y == 1 && eventType == "ProjectInformationEvent") {
				// bei Projekt
				eventLinkedHashMap.put("Projekt", "ProjektID" + "=" + projektObj.getProjektID() + ", "
					+ "ProjektName=" + projektObj.getProjektName() + ", " + "ProjektGehoertZuUnternehmen="
					+ projektObj.getProjekt_gehoert_zu_Unternehmen() + ", " + "ProjektGehoertZuAbteilung="
					+ projektObj.getProjekt_gehoert_zu_Abteilung() + ", " + "ProjektHatProjektmitglied="
					+ projektObj.getProjekt_hat_Projektmitglied() + ", " + "ProjektHatDokument="
					+ projektObj.getProjekt_hat_Dokument());

  			} else if (y == 1 && eventType == "DepartmentInformationEvent") {

				// bei Abteilung
				eventLinkedHashMap.put("Abteilung",
					"AbteilungID=" + abteilungObj.getAbteilung_ID() + ", "
					+ "AbteilungName=" + abteilungObj.getAbteilung_Name() + ", " + "AbteilungKuerzel="
					+ abteilungObj.getAbteilung_Kuerzel() + ", " + "AbteilungHatProjekt="
					+ abteilungObj.getAbteilung_hat_Projekt() + ", " + "AbteilungHatMitarbeiter="
					+ abteilungObj.getAbteilung_hat_Mitarbeiter() + ", " + "AbteilungGehoertZuUnternehmen="
					+ abteilungObj.getAbteilung_gehoert_zu_Unternehmen());

			} else if (y == 0 && eventType == "AllProjectsEvent") {

				// alle Projektnamen
				eventLinkedHashMap.put("AllProjectsEvent",
					"ProjektName=" + projektObj.getProjektName());

			} else if (y == 0 && eventType == "AllProjectRolesEvent") {

				// alle Projektrollen
				eventLinkedHashMap.put("AllProjectRolesEvent",
					"Projektrollen=" + projektRolleObj.getProjektrolle());

			} else if (y == 0 && eventType == "AllDepartmentsEvent") {

				// alle Abteilungsnamen
				eventLinkedHashMap.put("AllDepartmentsEvent",
					"AbteilungName=" + abteilungObj.getAbteilung_Name());

			} else if (y == 0 && eventType == "AllCompaniesEvent") {

				// alle Unternehmensnamen
				eventLinkedHashMap.put("AllCompaniesEvent",
					"UnternehmenName=" + unternehmenObj.getUnternehmensName());

			}

		}
		// end of outer loop

		queryExecution.close();

		return eventLinkedHashMap;

	}

	// produce
	// consume
	// produce UserInformationEvent
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserInformation")
	public static Response getUserInformation() throws Exception {

		// @Path: /rest/eventInterface/getUserInformation

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

		// publishen geht überall
		// subcriben nur im PubSubHandler
		IEvent iEvent = new Event();
		iEvent.setData("Test_Data");
		PublishHelper publishHelper = new PublishHelper(false); // zum testen true wenns lokal ist

		publishHelper.Publish(iEvent, Constants.PubSub.Topic.TOPIC_1, true);
		// return personObj.toStringPersonObjekt();
		jsonObj.put("UserInformationEvent", userInformationEventObject.toStringUserInformationEvent());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	// produce
	// consume
	// produce DocumentInformationEvent
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDocumentInformation")
	public static Response getDocumentInformation() throws Exception {

		// @Path: /rest/eventInterface/getDocumentInformation

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
						+ "SELECT DISTINCT ?Dokument ?Dok_ID ?Dok_Name ?Dok_Typ ?Dok_URL ?Dok_Ordner " + "WHERE { "
						+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
						+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
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
								+ ", " + "DokPrio=" + dokumentObj.getPrio() + ", " + "DokTyp="
								+ dokumentObj.getDok_TypStr() + ", " + "DokURL=" + dokumentObj.getDok_URLStr()
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

	// produce
	// consume
	// produce ProjectInformationEvent
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getProjectInformation")
	public static Response getProjectInformation() throws Exception {

		// @Path: /rest/eventInterface/getProjectInformation

		jsonObj = new JSONObject();

		eventLinkedHashMap = new LinkedHashMap<String, String>();

		// timestamp = new Timestamp(System.currentTimeMillis());
		timestamp = new Timestamp(System.currentTimeMillis());
		timestampLong = timestamp.getTime();

		String eventTypeStr = "ProjectInformationEvent";
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();

		try {
			// initialisiere Variablen
			// sparql
			String sparql = "";

			String prioStr = "0";
			// initialisiere Objekte
			// dokument
			projektObj = new Projekt(projektIDStr, projektNameStr, projekt_gehoert_zu_UnternehmenStr,
				projekt_gehoert_zu_AbteilungStr, projekt_hat_ProjektmitgliedStr, projekt_hat_DokumentStr);

			for (int z = 0; z < inputArray.length; z++) {

				// ermittle UserInformation
				if (z == 1) {

					String projID = inputArray[z].toString();

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
						+ "SELECT DISTINCT ?Projekt ?ProjektID ?ProjektName ?Projekt_gehoert_zu_Unternehmen "
						+ "?Projekt_gehoert_zu_Abteilung ?Projekt_hat_Projektmitglied ?Projekt_hat_Dokument "
						+ "WHERE { " + "?Projekt ontology:Projekt_ID ?ProjektID . "
						+ "?Projekt ontology:Projekt_Name ?ProjektName . "
						+ "?Projekt ontology:Projekt_gehoert_zu_Unternehmen ?Projekt_gehoert_zu_Unternehmen . "
						+ "?Projekt ontology:Projekt_gehoert_zu_Abteilung ?Projekt_gehoert_zu_Abteilung . "
						+ "?Projekt ontology:Projekt_hat_Projektmitglied ?Projekt_hat_Projektmitglied . "
						+ "?Projekt ontology:Projekt_hat_Dokument ?Projekt_hat_Dokument . "

						+ "?Projekt ontology:Projekt_ID " + projID + " . " + "}";

				}

				if (sparql != "") {

					executeSparql(sparql);

					if (resultSet.hasNext() == true) {
						eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
					} else {
						projektObj.setProjektID("'null'");
						projektObj.setProjektName("'null'");
						projektObj.setProjekt_gehoert_zu_Unternehmen("'null'");
						projektObj.setProjekt_gehoert_zu_Abteilung("'null'");
						projektObj.setProjekt_hat_Projektmitglied("'null'");
						projektObj.setProjekt_hat_Dokument("'null'");

						eventLinkedHashMap.put("Projekt",
							"ProjektID" + "=" + projektObj.getProjektID()
								+ ", " + "ProjektName=" + projektObj.getProjektName()
								+ ", " + "ProjektGehoertZuUnternehmen=" + projektObj.getProjekt_gehoert_zu_Unternehmen()
								+ ", " + "ProjektGehoertZuAbteilung=" + projektObj.getProjekt_gehoert_zu_Abteilung()
								+ ", " + "ProjektHatProjektmitglied=" + projektObj.getProjekt_hat_Projektmitglied()
								+ ", " + "ProjektHatDokument=" + projektObj.getProjekt_hat_Dokument());

						projektObj.flushProjektObjekt();

					}

				}

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

		Projekt projectInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("Projekt")) {
				projectInformationEventObject = new Projekt(sessionIDStr, String.valueOf(timestampLong),
					eventUniqueID, eventLinkedHashMap.get(key).toString());
				System.out.println(projectInformationEventObject.toStringProjektObjekt());
				break;
			}

		}

		// return personObj.toStringPersonObjekt();
		jsonObj.put("ProjectInformationEvent", projectInformationEventObject.toStringProjektObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getDepartmentInformation")
	public static Response getDepartmentInformation() throws JsonProcessingException {

		// @Path: /rest/eventInterface/getProjectInformation

		jsonObj = new JSONObject();

		eventLinkedHashMap = new LinkedHashMap<String, String>();

		// timestamp = new Timestamp(System.currentTimeMillis());
		timestamp = new Timestamp(System.currentTimeMillis());
		timestampLong = timestamp.getTime();

		String eventTypeStr = "DepartmentInformationEvent";
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();

		try {
			// initialisiere Variablen
			// sparql
			String sparql = "";

			String prioStr = "0";
			// initialisiere Objekte
			// dokument
			abteilungObj = new Abteilung(abteilung_IDStr, abteilung_NameStr, abteilung_KuerzelStr,
				abteilung_hat_ProjektStr, abteilung_hat_MitarbeiterStr, abteilung_gehoert_zu_UnternehmenStr);

			for (int z = 0; z < inputArray.length; z++) {

				// ermittle UserInformation
				if (z == 1) {

					String abteilungName = inputArray[z].toString();

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
						+ "?Abteilung ontology:Abteilung_Name '" + abteilungName + "' . "
						+ "}";

				}

				if (sparql != "") {

					executeSparql(sparql);

					if (resultSet.hasNext() == true) {
						eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
					} else {
						abteilungObj.setAbteilung_ID("'null'");
						abteilungObj.setAbteilung_Name("'null'") ;
						abteilungObj.setAbteilung_Kuerzel("'null'");
						abteilungObj.setAbteilung_hat_Projekt("'null'");
						abteilungObj.setAbteilung_hat_Mitarbeiter("'null'");
						abteilungObj.setAbteilung_gehoert_zu_Unternehmen("'null'");

						eventLinkedHashMap.put("Abteilung",
							"AbteilungID=" + abteilungObj.getAbteilung_ID() + ", "
								+ "AbteilungName=" + abteilungObj.getAbteilung_Name() + ", " + "AbteilungKuerzel="
								+ abteilungObj.getAbteilung_Kuerzel() + ", " + "AbteilungHatProjekt="
								+ abteilungObj.getAbteilung_hat_Projekt() + ", " + "AbteilungHatMitarbeiter="
								+ abteilungObj.getAbteilung_hat_Mitarbeiter() + ", " + "AbteilungGehoertZuUnternehmen="
								+ abteilungObj.getAbteilung_gehoert_zu_Unternehmen());

						abteilungObj.flushAbteilungsObjekt();

					}

				}

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

		Abteilung departmentInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("Abteilung")) {
				departmentInformationEventObject = new Abteilung(sessionIDStr, String.valueOf(timestampLong),
					eventUniqueID, eventLinkedHashMap.get(key).toString());
				System.out.println(departmentInformationEventObject.toStringAbteilungsObjekt());
				break;
			}

		}

		// return personObj.toStringPersonObjekt();
		jsonObj.put("DepartmentInformationEvent", departmentInformationEventObject.toStringAbteilungsObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllProjects")
	public static Response getAllProjects() throws Exception {

		// @Path: /rest/eventInterface/getProjectInformation

		jsonObj = new JSONObject();

		eventLinkedHashMap = new LinkedHashMap<String, String>();

		// timestamp = new Timestamp(System.currentTimeMillis());
		timestamp = new Timestamp(System.currentTimeMillis());
		timestampLong = timestamp.getTime();

		String eventTypeStr = "AllProjectsEvent";
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();

		try {
			// initialisiere Variablen
			// sparql
			String sparql = "";

			String prioStr = "0";
			// initialisiere Objekte
			// dokument
			projektObj = new Projekt(projektNameStr);

			for (int z = 0; z < inputArray.length; z++) {

				// ermittle UserInformation
				if (z == 0) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
						+ "SELECT DISTINCT ?Projekt ?ProjektName "
						+ "WHERE { "
						+ "?Projekt ontology:Projekt_Name ?ProjektName . "
						+ "}";

				} else {
					sparql = "";
				}

				if (sparql != "") {

					executeSparql(sparql);

					if (resultSet.hasNext() == true) {
						eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
					} else {
						projektObj.setProjektName("'null'");

						eventLinkedHashMap.put("AllProjectsEvent",
								"ProjektName=" + projektObj.getProjektName());

					}

				}

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

		Projekt AllProjectsInformationEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("AllProjectsEvent")) {
				AllProjectsInformationEventObject = new Projekt(sessionIDStr, String.valueOf(timestampLong),
					eventUniqueID, eventLinkedHashMap.get(key).toString());
				System.out.println(AllProjectsInformationEventObject.toStringProjektObjekt());
				break;
			}

		}

		// return personObj.toStringPersonObjekt();
		jsonObj.put("AllProjectsEvent", AllProjectsInformationEventObject.toStringProjektObjekt());
		return Response.status(200).entity(jsonObj.toString()).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllProjectRoles")
	public static Response getAllProjectRoles() throws Exception {

		// @Path: /rest/eventInterface/getProjectInformation

		jsonObj = new JSONObject();

		eventLinkedHashMap = new LinkedHashMap<String, String>();

		// timestamp = new Timestamp(System.currentTimeMillis());
		timestamp = new Timestamp(System.currentTimeMillis());
		timestampLong = timestamp.getTime();

		String eventTypeStr = "AllProjectRolesEvent";
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();

		try {
			// initialisiere Variablen
			// sparql
			String sparql = "";

			String prioStr = "0";
			// initialisiere Objekte
			// dokument
			projektRolleObj = new Projektrolle(rollenBezeichnungStr);

			for (int z = 0; z < inputArray.length; z++) {

				// ermittle UserInformation
				if (z == 0) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
						+ "SELECT DISTINCT ?Projektrolle ?Bezeichnung "
						+ "WHERE { "
						+ "?Projektrolle ontology:Projektrolle_Bezeichnung ?Bezeichnung . "
						+ "}";

				} else {
					sparql = "";
				}

				if (sparql != "") {

					executeSparql(sparql);

					if (resultSet.hasNext() == true) {
						eventLinkedHashMap = loopThroughResults(z, eventTypeStr);
					} else {
						projektRolleObj.setProjektrolle("'null'");

						eventLinkedHashMap.put("AllProjectRolesEvent",
							"Projektrollen=" + projektRolleObj.getProjektrolle());

					}

				}

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

		Projektrolle AllProjectRolesEventObject = null;

		// drucke alles im richTokenHashMap aus
		for (String key : eventLinkedHashMap.keySet()) {

			if (key.equals("AllProjectRolesEvent")) {
				AllProjectRolesEventObject = new Projektrolle(sessionIDStr, String.valueOf(timestampLong),
					eventUniqueID, eventLinkedHashMap.get(key).toString());
				System.out.println(AllProjectRolesEventObject.toStringProjektrolleObj());
				break;
			}

		}

		// return personObj.toStringPersonObjekt();
		jsonObj.put("AllProjectRolesEvent", AllProjectRolesEventObject.toStringProjektrolleObj());
		return Response.status(200).entity(jsonObj.toString()).build();

	}


}
