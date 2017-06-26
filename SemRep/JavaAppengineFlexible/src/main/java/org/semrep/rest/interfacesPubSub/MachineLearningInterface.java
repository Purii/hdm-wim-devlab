package org.semrep.rest.interfacesPubSub;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.log4j.Logger;
import org.semrep.rest.helper.EventNameConstants;
import org.semrep.rest.helper.FusekiConfigConstants;
import org.semrep.rest.helper.InitializeArrayData;

import java.util.UUID;


/**
 * @author Kristjan Alliaj
 * The type Machine learning interface.
 *
 * Insert-Methoden für die Use Cases mit ML
 */
public class MachineLearningInterface {

	private static Logger loggger = Logger.getLogger(EventInterface.class.getName());
	private static InitializeArrayData initializeArrayData = new InitializeArrayData();
	private static String sessionIDStr = "";

	/**
	 * Insert new document call.
	 *
	 * @throws Exception the exception
	 *
	 * Input-Parameter: sessionID, personVorname, personNachname, dokName
	 *
	 * Fügt einen neuen Dokumentenaufruf in den Fueski-Server ein
	 */
	public static void insertNewDocumentCall() throws Exception {

		String eventTypeStr = EventNameConstants.DOCUMENT_CALL_EVENT;
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		sessionIDStr = inputArray[0].toString();
		String personVorname = inputArray[1].toString();
		String personNachname = inputArray[2].toString();
		String dokName = inputArray[3].toString();


		try {
			// initialisiere Variablen
			// sparql
			String insertSparql = "";

			insertSparql = "";

			String uuID = UUID.randomUUID().toString();

			// füge neue Dokumentaufruf an Dokumentinstanz an
			insertSparql = " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
				" PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> " +
				" INSERT DATA " +
				"{ " +
				"ontology:" + personVorname + "_" + personNachname + " " +
				//"ontology:Person_Dok_Aufruf '" + dokName + "_" + uuID + "' " +
				"ontology:Person_Dok_Aufruf '" + dokName + "(" + uuID + ")" + "' " +
				//"ontology:Person_Dok_Aufruf ' ' " +
				"} ";
			//"WHERE {  ?Person ontology:Person_ID '873267' }";


			if (insertSparql != "") {

				//String uuID = UUID.randomUUID().toString();
				UpdateProcessor uP = UpdateExecutionFactory.createRemote(
					UpdateFactory.create(String.format(insertSparql, uuID)), FusekiConfigConstants.FUSEKI_INSERT_ADDRESS);
				uP.execute();

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

	}

	/**
	 * Insert new favorite document.
	 *
	 * @throws Exception the exception
	 *
	 * Input-Parameter: personVorname, personNachname, dokName
	 * Umwandlung von: userID, dokID, favor (true/false)
	 * 2 select statements für userID & dokID (für Vor- & Nachname & DokName)
	 *
	 * Fügt einen neues Favoriten-Dokument in den Fueski-Server ein
	 */
	public static void insertNewFavoriteDocument() throws Exception {

		String eventTypeStr = EventNameConstants.LEARN_EVENT;
		String[] inputArray = initializeArrayData.initializeArrayDemoData(eventTypeStr);
		String personVorname = inputArray[0].toString();
		String personNachname = inputArray[1].toString();
		String dokName = inputArray[2].toString();

		try {
			// initialisiere Variablen
			// sparql
			String insertSparql = "";

			// füge neue Dokumentfavoriten an Dokumentinstanz an
			insertSparql = " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
				" PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> " +
				" INSERT DATA " +
				"{ " +
				"ontology:" + personVorname + "_" + personNachname + " " +
				"ontology:Person_favorisiert_Dokument ontology:" + dokName + " " +
				"}";

			if (insertSparql != "") {

				String uuID = UUID.randomUUID().toString();
				UpdateProcessor uP = UpdateExecutionFactory.createRemote(
					UpdateFactory.create(String.format(insertSparql, uuID)), FusekiConfigConstants.FUSEKI_INSERT_ADDRESS);
				uP.execute();

			}

		} catch (Exception e) {
			loggger.error("Fehler in EventInterface: " + e);
		}

	}

}
