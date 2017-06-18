package semRepServices.interfaces;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
import semRepServices.businessObjects.Dokument;
import semRepServices.businessObjects.Dokumentvorschlag;
import semRepServices.businessObjects.FavoritDokument;
import semRepServices.businessObjects.Person;

import java.sql.Timestamp;

public class UXInterface {

	// Am Anfang: alle dokumente
	// methode1

	// Pro Vorschläge: Alle Dokumente (Attributiert mit "nicht relevant") &&
	// vorschläge (Attributiert mit "relevant")
	// methode2

	// Ordnername in google drive in ontology

	public static String filePath = "src/semRepServices/interfaces/Ontology.owl";
	public static OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
	public static ResultSet resultSet;
	public static QueryExecution queryExecution;
	public static String[] inputArray = null;
	public static LinkedHashMap<String, String> dokOfferLinkedHashMap = null;
	public static LinkedHashMap<String, String> alleDokumenteLinkedHashMap = null;
	public static HashMap<String, String> dokOfferHashMap = null;
	public static HashMap<String, String> tmpDokOfferHashMap = null;
	public static Timestamp timestamp = null;
	
	public static Dokumentvorschlag dokumentvorschlagObj = null;
	public static Person personObj = null;
	public static Person personFavDokObj = null;
	
	// dokument
	public static String sessionIDStr = "";
	public static String timeStampStr = "";
	public static String dok_IDStr = "";
	public static String dok_NameStr = "";
	public static String prioStr = "";
	public static String dok_TypStr = "";
	public static String dok_URLStr = "";
	public static String dok_folder = "";
	//favoritDok
	public static String personName_Str = "";
	public static int numFavDoks = 0;

	public static void main(String[] args) {

		setArrayDemoData();
		getDocumentOffers();
		// getAllDocuments();
	}

	public static void setArrayDemoData() {

		// richToken
		inputArray = new String[6];
		inputArray[0] = "1"; // sessionID
		inputArray[1] = "6"; // userID
		inputArray[2] = "HighNet_project"; // context
		inputArray[3] = "milestone"; // keyword
		inputArray[4] = "tasks"; // keyword
		inputArray[5] = "leading"; //keyword
		// inputArray[4] = "kickoff";
		
		sessionIDStr = inputArray[0].toString();

		// Kontext wird von Event berechnet, aus Projekt- oder
		// Keywordschnittmenge der Dokumentvorschläge

	}
	
	public static void executeSparql(String sparql){
		// Initialisierung und Ausführung einer SPARQL-Query
		Query query = QueryFactory.create(sparql);
		queryExecution = QueryExecutionFactory.create(query, ontologyModel);

		// Initialisierung von Resultset für Ergebniswerte der
		// SPARQL-Query
		resultSet = queryExecution.execSelect();
	}
	
	public static void loopThroughResults(int y){
		
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

				if (y == 0 || y == 3) {

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
				//ermittle das favorisierte Dokument zu einer Person
				if (y == 1) {
					
					// einmaliges befüllen der nachfolgenden Werte
					if (((personFavDokObj.getName() == "") == true)) {

						switch (results) {
						case "PersonVorname":
							personName_Str = splitResult;
							personFavDokObj.setName(personName_Str);
							numFavDoks = numFavDoks + 1;
							break;
						case "PersonNachname":
							personName_Str = splitResult;
							personFavDokObj.setName(personName_Str);
							numFavDoks = numFavDoks + 1;
							break;
						}

					}
					// mehr als ein Vorkommen
					else if (((personFavDokObj.getName() == "") == false)) {

						switch (results) {
						case "Dok_Name":
							personName_Str = splitResult;
							splitKeywordsList = Arrays.asList(
									personFavDokObj.getName().toString().split(", "));

							if (splitKeywordsList.contains(personName_Str)) {

								break;

							} else {

								personFavDokObj.setName(
										personFavDokObj.getName() + ", " + personName_Str);
								numFavDoks = numFavDoks + 1;
								break;
							}
						}

					}
					
				}
				if (y == 2) {
					
				}

			}

			if (y == 0) {
				// prio bei allen Dokumenten irrelevant (0)
				prioStr = "0";
				dokumentvorschlagObj.setPrio(prioStr);
				dokOfferLinkedHashMap.put("Dokument" + i,
						"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
								+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
								+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
								+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
								+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
								+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
								+ dokumentvorschlagObj.getDok_URLStr() + ", " + "DokOrdner="
								+ dokumentvorschlagObj.getDok_folder());

				dokumentvorschlagObj.flushDokumentvorschlag();
			} else if (y == 2) {
				
			} else if (y == 3) {
				// prio bei Dokumentvorschlägen relevant (1)
				prioStr = "1";
				dokumentvorschlagObj.setPrio(prioStr);
				dokOfferHashMap.put("Dokumentvorschlag_" + countDokOffersInLoop,
						"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
								+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
								+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
								+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
								+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
								+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
								+ dokumentvorschlagObj.getDok_URLStr() + ", " + "DokOrdner="
								+ dokumentvorschlagObj.getDok_folder());

				countDokOffersInLoop = countDokOffersInLoop + 1;
				dokumentvorschlagObj.flushDokumentvorschlag();
			} 

		}

		queryExecution.close();

		dokumentvorschlagObj.flushDokumentvorschlag();
		
	}
	

	public static LinkedHashMap<String, String> getDocumentOffers() {

//		String filePath = "src/semRepServices/interfaces/Ontology.owl";
//		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		dokOfferLinkedHashMap = new LinkedHashMap<String, String>();
		dokOfferHashMap = new LinkedHashMap<String, String>();
		tmpDokOfferHashMap = new LinkedHashMap<String, String>();

		timestamp = new Timestamp(System.currentTimeMillis());
		timeStampStr = timestamp.toString();

//		Dokumentvorschlag dokumentvorschlagObj = null;
//		Person personObj = null;
//		FavoritDokument favDokObj = null;

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";

//			// dokument
//			String sessionIDStr = inputArray[0].toString();
//			String timeStampStr = timestamp.toString();
//			String dok_IDStr = "";
//			String dok_NameStr = "";
//			String prioStr = "";
//			String dok_TypStr = "";
//			String dok_URLStr = "";
//			String dok_folder = "";
//			//favoritDok
//			String favDok_Str = "";

			// initialisiere Objekte
			// dokument
			dokumentvorschlagObj = new Dokumentvorschlag(sessionIDStr, timeStampStr, dok_IDStr, dok_NameStr, prioStr,
					dok_TypStr, dok_URLStr, dok_folder);
			//favoritDok
			personFavDokObj = new Person(personName_Str);

			for (int z = 0; z <= inputArray.length; z++) {

				if (z == 0) {

					// Alle Dokumente abfragen
					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ ?Dok_URL ?Dok_Ordner " + "WHERE { "
							+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . " + "}";

				}
				//ermittle FavoritDok
				if (z == 1) {
					sparql = "PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?PersonNachname ?PersonVorname "
							+ "WHERE { "
							+ "?Person ontology:Person_Vorname ?PersonVorname . "
							+ "?Person ontology:Person_Nachname ?PersonNachname . "
							+ "?Person ontology:Person_ID '" + inputArray[z].toString() + "' . "
							+ "}";
				}
				if (z == 2) {

						String[] favDokStringArr = personFavDokObj.getName()
								.toString().split(", ");
						String splittedFavDokString = "";

					for (int loopFavDoks = 0; loopFavDoks < favDokStringArr.length; loopFavDoks++) {
							
							splittedFavDokString = favDokStringArr[loopFavDoks];
	
							sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
									+ "SELECT DISTINCT ?Kontext ?Dokument ?Dok_ID ?Dok_Name ?Dok_Typ "
									+ "?Dok_URL ?Dok_Keywords ?Dok_Ordner " 
									+ "WHERE { "
									+ "?Dokument ontology:Dok_ID ?Dok_ID . " 
									+ "?Dokument ontology:Dok_Name ?Dok_Name . "
									+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " 
									+ "?Dokument ontology:Dok_URL ?Dok_URL . "
									+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
									+ "?Dokument ontology:Dok_Kontext ?Kontext . "
									+ "?Dokument ontology:Dok_Keywords ?Dok_Keywords . "
									+ "?Dokument ontology:Dokument_favorisiert_von_Person ontology:" + personFavDokObj.getName() +" ."
									+ "}";
					}
					
				}
				if (z == 3) {

//					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
//							+ "SELECT DISTINCT ?Kontext ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ "
//							+ "?Dok_URL ?Dok_Keywords ?Dok_Ordner " + "WHERE { "
//							+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
//							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
//							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
//							+ "?Dokument ontology:Dokument_hat_Kontext ?Kontext . "
//							+ "?Dokument ontology:Dokument_hat_Keyword ?Dok_Keywords . "
//							+ "?Dokument ontology:Dokument_hat_Keyword ontology:" + inputArray[y].toString() + " . "
//							+ "?Dokument ontology:Dokument_hat_Kontext ontology:" + inputArray[2].toString() + " . "
//							+ "}";
					
					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Kontext ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ "
							+ "?Dok_URL ?Dok_Keywords ?Dok_Ordner " + "WHERE { "
							+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
							+ "?Dokument ontology:Dok_Kontext ?Kontext . "
							+ "?Dokument ontology:Dok_Keywords ?Dok_Keywords . "
							//+ "?Dokument ontology:Dok_Keywords '" + inputArray[y].toString().toLowerCase() + "' . "
							+ "?Dokument ontology:Dok_Kontext '" + inputArray[2].toString() + "' . "
							+ "FILTER ( "
							+ "?Dok_Keywords = '" + inputArray[3].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[4].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[5].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[4].toString() + " ' && '" + inputArray[3].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[3].toString() + " ' && '" + inputArray[4].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[4].toString() + " ' && '" + inputArray[5].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[5].toString() + " ' && '" + inputArray[4].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[3].toString() + " ' && '" + inputArray[5].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[5].toString() + " ' && '" + inputArray[3].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[4].toString() + " ' && '" + inputArray[3].toString() + " ' && '" + inputArray[5].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[4].toString() + " ' && '" + inputArray[5].toString() + " ' && '" + inputArray[3].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[3].toString() + " ' && '" + inputArray[4].toString() + " ' && '" + inputArray[5].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[3].toString() + " ' && '" + inputArray[5].toString() + " ' && '" + inputArray[4].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[5].toString() + " ' && '" + inputArray[4].toString() + " ' && '" + inputArray[3].toString() + "' || "
							+ "?Dok_Keywords = '" + inputArray[5].toString() + " ' && '" + inputArray[3].toString() + " ' && '" + inputArray[4].toString() + "' "
							+ ") "
							+ "}";

//					" + inputArray[3].toString() + " // milestone
//					" + inputArray[4].toString() + " // tasks
//					" + inputArray[5].toString() + " // leading
					
					// Kontext plus keywords
				}
				if (z > 3 && z <= inputArray.length) {
					sparql = "";
				}

				if (sparql != "") {
					
					executeSparql(sparql);
					loopThroughResults(z);
					
					// Initialisierung und Ausführung einer SPARQL-Query
//					Query query = QueryFactory.create(sparql);
//					QueryExecution queryExecution = QueryExecutionFactory.create(query, ontologyModel);
//
//					// Initialisierung von Resultset für Ergebniswerte der
//					// SPARQL-Query
//					ResultSet resultSet = queryExecution.execSelect();

//					// initialisiere Variablen
//					String splitResult = "";
//					int indexOfToSplitCharacter;
//					int countLoop = 0;
//					int countDokOffersInLoop = 0;
//					
//					List<String> splitKeywordsList = null;
//
//					// Ergebniswerte werden für Konsolendarstellung aufbereitet
//					for (@SuppressWarnings("unused")
//					int i = 0; resultSet.hasNext() == true; i++) {
//						countLoop = countLoop + i;
//						QuerySolution querySolution = resultSet.nextSolution();
//						for (int j = 0; j < resultSet.getResultVars().size(); j++) {
//							String results = resultSet.getResultVars().get(j).toString();
//							RDFNode rdfNode = querySolution.get(results);
//
//							indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
//							splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);
//
//							if (y == 0 || y == 3) {
//
//								// einmaliges befüllen der nachfolgenden Werte
//								if (((dokumentvorschlagObj.getDok_IDStr() == "") == true)
//										|| ((dokumentvorschlagObj.getDok_NameStr() == "") == true)
//										|| ((dokumentvorschlagObj.getDok_TypStr() == "") == true)
//										|| ((dokumentvorschlagObj.getDok_URLStr() == "") == true)
//										|| ((dokumentvorschlagObj.getDok_folder() == "") == true)) {
//
//									switch (results) {
//									case "Dok_ID":
//										dok_IDStr = splitResult;
//										dokumentvorschlagObj.setDok_IDStr(dok_IDStr);
//										break;
//									case "Dok_Name":
//										dok_NameStr = splitResult;
//										dokumentvorschlagObj.setDok_NameStr(dok_NameStr);
//										break;
//									case "Dok_Typ":
//										dok_TypStr = splitResult;
//										dokumentvorschlagObj.setDok_TypStr(dok_TypStr);
//										break;
//									case "Dok_URL":
//										dok_URLStr = splitResult;
//										dokumentvorschlagObj.setDok_URLStr(dok_URLStr);
//										break;
//									case "Dok_Ordner":
//										dok_folder = splitResult;
//										dokumentvorschlagObj.setDok_folder(dok_folder);
//										break;
//									}
//
//								}
//
//							}
//							//ermittle das favorisierte Dokument zu einer Person
//							if (y == 1) {
//								
//								// einmaliges befüllen der nachfolgenden Werte
//								if (((favDokObj.getFavoritDokNameStr() == "") == true)) {
//
//									switch (results) {
//									case "Dok_Name":
//										favDok_Str = splitResult;
//										favDokObj.setFavoritDokNameStr(favDok_Str);
//										break;
//									}
//
//								}
//								// mehr als ein Vorkommen
//								else if (((favDokObj.getFavoritDokNameStr() == "") == false)) {
//
//									switch (results) {
//									case "Dok_Name":
//										favDok_Str = splitResult;
//										splitKeywordsList = Arrays.asList(
//												favDokObj.getFavoritDokNameStr().toString().split(", "));
//
//										if (splitKeywordsList.contains(favDok_Str)) {
//
//											break;
//
//										} else {
//
//											favDokObj.setFavoritDokNameStr(
//													favDokObj.getFavoritDokNameStr() + ", " + favDok_Str);
//											break;
//										}
//									}
//
//								}
//								
//							}
//							if (y == 2) {
//								
//							}
//
//						}
//
//						if (y == 0) {
//							// prio bei allen Dokumenten irrelevant (0)
//							prioStr = "0";
//							dokumentvorschlagObj.setPrio(prioStr);
//							dokOfferLinkedHashMap.put("Dokument" + i,
//									"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
//											+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
//											+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
//											+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
//											+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
//											+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
//											+ dokumentvorschlagObj.getDok_URLStr() + ", " + "DokOrdner="
//											+ dokumentvorschlagObj.getDok_folder());
//
//							dokumentvorschlagObj.flushDokumentvorschlag();
//						} else if (y == 2) {
//							
//						} else if (y == 3) {
//							// prio bei Dokumentvorschlägen relevant (1)
//							prioStr = "1";
//							dokumentvorschlagObj.setPrio(prioStr);
//							dokOfferHashMap.put("Dokumentvorschlag_" + countDokOffersInLoop,
//									"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
//											+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
//											+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
//											+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
//											+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
//											+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
//											+ dokumentvorschlagObj.getDok_URLStr() + ", " + "DokOrdner="
//											+ dokumentvorschlagObj.getDok_folder());
//
//							countDokOffersInLoop = countDokOffersInLoop + 1;
//							dokumentvorschlagObj.flushDokumentvorschlag();
//						} 
//
//					}
//
//					queryExecution.close();
//
//					dokumentvorschlagObj.flushDokumentvorschlag();

				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		dokumentvorschlagObj.flushAllDokumentvorschlag();

		// checke Duplikate
		int countOffers = 0;
		String offerStr = "";
		for (HashMap.Entry<String, String> entry : dokOfferHashMap.entrySet()) {
			if (!tmpDokOfferHashMap.containsValue(entry.getValue())) {
				offerStr = entry.getKey().toString().split("_")[0];
				tmpDokOfferHashMap.put(offerStr + countOffers, entry.getValue());
				countOffers = countOffers + 1;
			}
		}
		for (HashMap.Entry<String, String> entry : tmpDokOfferHashMap.entrySet()) {
			dokOfferLinkedHashMap.put(entry.getKey(), entry.getValue());
		}

		for (String key : dokOfferLinkedHashMap.keySet()) {
			System.out.println(key + ": " + dokOfferLinkedHashMap.get(key) + ", ");
		}

		return dokOfferLinkedHashMap;

	}
	
	//REST-Pfad hier annotieren
	public static LinkedHashMap<String, String> getAllDocuments() {

		alleDokumenteLinkedHashMap = new LinkedHashMap<String, String>();

		Dokumentvorschlag dokumentvorschlagObj = null;

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";

			// dokument
			String sessionIDStr = inputArray[0];
			String timeStampStr = "";
			String dok_IDStr = "";
			String dok_NameStr = "";
			String prioStr = "";
			String dok_TypStr = "";
			String dok_URLStr = "";
			String dok_folder = "";

			// initialisiere Objekte
			// dokument
			dokumentvorschlagObj = new Dokumentvorschlag(sessionIDStr, timeStampStr, dok_IDStr, dok_NameStr, prioStr,
					dok_TypStr, dok_URLStr, dok_folder);

			for (int y = 0; y < inputArray.length; y++) {

				if (y == 0) {

					// Alle Dokumente abfragen
					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ ?Dok_URL ?Dok_Ordner " + "WHERE { "
							+ "?Dokument ontology:Dok_ID ?Dok_ID . " + "?Dokument ontology:Dok_Name ?Dok_Name . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " + "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . " + "}";

					// nur Kontext ohne Keywords
					// } if ((y == 3 && y == (inputArray.length - 1))) {
				}

				// Initialisierung und Ausführung einer SPARQL-Query
//				Query query = QueryFactory.create(sparql);
//				QueryExecution queryExecution = QueryExecutionFactory.create(query, ontologyModel);
//
//				// Initialisierung von Resultset für Ergebniswerte der
//				// SPARQL-Query
//				ResultSet resultSet = queryExecution.execSelect();
				executeSparql(sparql);

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

						if (y == 0 || y >= 3) {

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

					}

					if (y == 0) {
						// prio bei allen Dokumenten irrelevant (0)
						prioStr = "0";
						dokumentvorschlagObj.setPrio(prioStr);
						timestamp = new Timestamp(System.currentTimeMillis());
						timeStampStr = timestamp.toString();
						dokumentvorschlagObj.setTimeStamp(timeStampStr);

						alleDokumenteLinkedHashMap.put("Dokument_" + i,
								"SessionID=" + dokumentvorschlagObj.getSessionID() + ", " + "TimeStamp="
										+ dokumentvorschlagObj.getTimeStamp() + ", " + "DokID="
										+ dokumentvorschlagObj.getDok_IDStr() + ", " + "DokName="
										+ dokumentvorschlagObj.getDok_NameStr() + ", " + "DokPrio="
										+ dokumentvorschlagObj.getPrio() + ", " + "DokTyp="
										+ dokumentvorschlagObj.getDok_TypStr() + ", " + "DokURL="
										+ dokumentvorschlagObj.getDok_URLStr() + ", " + "DokOrdner="
										+ dokumentvorschlagObj.getDok_folder());

						dokumentvorschlagObj.flushDokumentvorschlag();
					}

				}

				queryExecution.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		dokumentvorschlagObj.flushAllDokumentvorschlag();

		// drucke alles im richTokenHashMap aus
		for (String key : alleDokumenteLinkedHashMap.keySet()) {
			System.out.println(key + ": " + alleDokumenteLinkedHashMap.get(key) + ", ");
		}

		return alleDokumenteLinkedHashMap;

	}

}
