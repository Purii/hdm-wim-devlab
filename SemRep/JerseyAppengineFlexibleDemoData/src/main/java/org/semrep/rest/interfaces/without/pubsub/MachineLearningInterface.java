package main.java.org.semrep.rest.interfaces.without.pubsub;

import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;


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


import java.sql.Timestamp;

import main.java.org.semrep.rest.businessObjects.Dokumentvorschlag;
import main.java.org.semrep.rest.businessObjects.Person;
import main.java.org.semrep.rest.helper.*;

@Path("/machineLearningInterface")
public class MachineLearningInterface {

	private static Logger loggger = Logger.getLogger(MachineLearningInterface.class.getName());
	private static main.java.org.semrep.rest.helper.InitializeArrayData initializeArrayData = new main.java.org.semrep.rest.helper.InitializeArrayData();
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

		String eventTypeStr = main.java.org.semrep.rest.helper.EventNameConstants.DOCUMENT_CALL_EVENT;
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
