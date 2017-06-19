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
		initialUserInformationObject();
		produceUserInformationEvent();
	}
	
	public static void setArrayData() {

		inputArray = new String[2];
		inputArray[0] = "793dnj"; // sessionID
		inputArray[1] = "0"; // userID

		eventSessionID = inputArray[0].toString();
		eventUniqueID = UUID.randomUUID().toString();

	}
	
	//hier REST-Pfad annotieren
	public static void initialUserInformationObject() {
		
		getObjectInformation("UserInformationObject");
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
	
	public static LinkedHashMap<String, String> getObjectInformation(String objectType) {
		
	}


	
}
