package semRepServices.interfaces;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
import semRepServices.businessObjects.Dokumentvorschlag;

import java.sql.Timestamp;

public class UXInterface {
	
	//Am Anfang: alle dokumente
	//methode1
	
	//Pro Vorschläge: Alle Dokumente (Attributiert mit "nicht relevant") && vorschläge (Attributiert mit "relevant") 
	//methode2
	
	//Ordnername in google drive in ontology
	
	public static String[] inputArray = null;
	public static LinkedHashMap<String, String> dokOfferLinkedHashMap = null;
	public static LinkedHashMap<String, String> alleDokumenteLinkedHashMap = null;
	public static HashMap<String, String> dokOfferHashMap = null;
	public static HashMap<String, String> tmpDokOfferHashMap = null;
	Timestamp timestamp = null;

	public static void main(String[] args) {

		setArrayDemoData();
		getDocumentOffers();
		//getAllDocuments();
	}
	
	public static void setArrayDemoData() {
		
		//richToken
		inputArray = new String[4];
		inputArray[0] = "1";				// sessionID
		inputArray[1] = "2"; 					// userID
		inputArray[2] = "HighNet_project"; 		// context
		inputArray[3] = "milestone";			// keyword
		//inputArray[4] = "kickoff";
		
		//Kontext wird von Event berechnet, aus Projekt- oder Keywordschnittmenge der Dokumentvorschläge

	}
	
	public static LinkedHashMap<String, String> getAllDocuments() {

		String filePath = "src/semRepServices/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		alleDokumenteLinkedHashMap = new LinkedHashMap<String, String>();
		
		new Timestamp(System.currentTimeMillis());
		alleDokumenteLinkedHashMap.put("SessionID", inputArray[0]);
		alleDokumenteLinkedHashMap.put("TimeStamp", timestamp.toString());

		Dokumentvorschlag dokumentvorschlagObj = null;

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";

			// dokument
			String dok_IDStr = "";
			String dok_NameStr = "";
			String prioStr = "";
			String dok_TypStr = "";
			String dok_URLStr = "";
			String dok_folder = "";
		
			// initialisiere Objekte
			// dokument
			dokumentvorschlagObj = new Dokumentvorschlag(dok_IDStr, dok_NameStr, prioStr,
					dok_TypStr, dok_URLStr, dok_folder);

			for (int y = 0; y < inputArray.length; y++) {
				
				if (y == 0) {

					//Alle Dokumente abfragen
					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ ?Dok_URL ?Dok_Ordner "
							+ "WHERE { " 
							+ "?Dokument ontology:Dok_ID ?Dok_ID . "
							+ "?Dokument ontology:Dok_Name ?Dok_Name . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " 
							+ "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
							+ "}";

					// nur Kontext ohne Keywords
				//} if ((y == 3 && y == (inputArray.length - 1))) {
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
				int countLoop = 0;
				int countDokOffersInLoop = 0;

				// Ergebniswerte werden für Konsolendarstellung aufbereitet
				outerloop: for (@SuppressWarnings("unused")
				int i = 0; resultSet.hasNext() == true; i++) {
					countLoop = countLoop + i; 
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
						//prio bei allen Dokumenten irrelevant (0)
						prioStr = "0";
						dokumentvorschlagObj.setPrio(prioStr);
						alleDokumenteLinkedHashMap.put("Dokument_" + i, 
									"Dok_ID=" + dokumentvorschlagObj.getDok_IDStr()
									+ ", " + "Dok_Name=" + dokumentvorschlagObj.getDok_NameStr()
									+ ", " + "Dok_Prio=" + dokumentvorschlagObj.getPrio() + ", " 
									+ "Dok_Typ=" + dokumentvorschlagObj.getDok_TypStr() + ", " 
									+ "Dok_URL=" + dokumentvorschlagObj.getDok_URLStr() + ", " 
									+ "Dok_Ordner=" + dokumentvorschlagObj.getDok_folder());

						dokumentvorschlagObj.flushDokumentvorschlag();
					} 
					
				}

				queryExecution.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}  
		
		// drucke alles im richTokenHashMap aus
		for (String key : alleDokumenteLinkedHashMap.keySet()) {
			System.out.println(key + ": " + alleDokumenteLinkedHashMap.get(key) + ", ");
		}

		return alleDokumenteLinkedHashMap;

	}
	
	public static LinkedHashMap<String, String> getDocumentOffers() {

		String filePath = "src/semRepServices/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		dokOfferLinkedHashMap = new LinkedHashMap<String, String>();
		dokOfferHashMap = new LinkedHashMap<String, String>();
		tmpDokOfferHashMap = new LinkedHashMap<String, String>();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		dokOfferLinkedHashMap.put("SessionID", inputArray[0]);
		dokOfferLinkedHashMap.put("TimeStamp", timestamp.toString());

		Dokumentvorschlag dokumentvorschlagObj = null;

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";

			// dokument
			String dok_IDStr = "";
			String dok_NameStr = "";
			String prioStr = "";
			String dok_TypStr = "";
			String dok_URLStr = "";
			String dok_folder = "";
		
			// initialisiere Objekte
			// dokument
			dokumentvorschlagObj = new Dokumentvorschlag(dok_IDStr, dok_NameStr, prioStr,
					dok_TypStr, dok_URLStr, dok_folder);

			for (int y = 0; y < inputArray.length; y++) {
				
				if (y == 0) {

					//Alle Dokumente abfragen
					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ ?Dok_URL ?Dok_Ordner "
							+ "WHERE { " 
							+ "?Dokument ontology:Dok_ID ?Dok_ID . "
							+ "?Dokument ontology:Dok_Name ?Dok_Name . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . " 
							+ "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
							+ "}";

					// nur Kontext ohne Keywords
				//} if ((y == 3 && y == (inputArray.length - 1))) {
				} if (y >= 3) {

					sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
							+ "SELECT DISTINCT ?Kontext ?Dokument  ?Dok_ID ?Dok_Name ?Dok_Typ "
							+ "?Dok_URL ?Dok_Keywords ?Dok_Ordner " 					
							+ "WHERE { " 
							+ "?Dokument ontology:Dok_ID ?Dok_ID . "
							+ "?Dokument ontology:Dok_Name ?Dok_Name . "
							+ "?Dokument ontology:Dok_Typ ?Dok_Typ . "
							+ "?Dokument ontology:Dok_URL ?Dok_URL . "
							+ "?Dokument ontology:Dok_Ordner ?Dok_Ordner . "
							+ "?Dokument ontology:Dokument_hat_Kontext ?Kontext . "
							+ "?Dokument ontology:Dokument_hat_Keyword ?Dok_Keywords . "
							+ "?Dokument ontology:Dokument_hat_Keyword ontology:" + inputArray[y].toString() + " . "
							+ "?Dokument ontology:Dokument_hat_Kontext ontology:" + inputArray[2].toString() + " . "
							+ "}";

					//Kontext plus keywords
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
				int countLoop = 0;
				int countDokOffersInLoop = 0;

				// Ergebniswerte werden für Konsolendarstellung aufbereitet
				outerloop: for (@SuppressWarnings("unused")
				int i = 0; resultSet.hasNext() == true; i++) {
					countLoop = countLoop + i; 
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
						//prio bei allen Dokumenten irrelevant (0)
						prioStr = "0";
						dokumentvorschlagObj.setPrio(prioStr);
						dokOfferLinkedHashMap.put("Dokument_" + i, 
									"Dok_ID=" + dokumentvorschlagObj.getDok_IDStr()
									+ ", " + "Dok_Name=" + dokumentvorschlagObj.getDok_NameStr()
									+ ", " + "Dok_Prio=" + dokumentvorschlagObj.getPrio() + ", " 
									+ "Dok_Typ=" + dokumentvorschlagObj.getDok_TypStr() + ", " 
									+ "Dok_URL=" + dokumentvorschlagObj.getDok_URLStr() + ", " 
									+ "Dok_Ordner=" + dokumentvorschlagObj.getDok_folder());

						dokumentvorschlagObj.flushDokumentvorschlag();
					} else if (y >= 3) {
						//prio bei Dokumentvorschlägen relevant (1)
						prioStr = "1";
						dokumentvorschlagObj.setPrio(prioStr);
						dokOfferHashMap.put("Dokumentvorschlag_" + countDokOffersInLoop, 
									"Dok_ID=" + dokumentvorschlagObj.getDok_IDStr()
									+ ", " + "Dok_Name=" + dokumentvorschlagObj.getDok_NameStr() 
									+ ", " + "Dok_Prio=" + dokumentvorschlagObj.getPrio() + ", "  
									+ "Dok_Typ=" + dokumentvorschlagObj.getDok_TypStr() + ", " 
									+ "Dok_URL=" + dokumentvorschlagObj.getDok_URLStr() + ", " 
									+ "Dok_Ordner=" + dokumentvorschlagObj.getDok_folder());
						
						countDokOffersInLoop = countDokOffersInLoop + 1;
						
						dokumentvorschlagObj.flushDokumentvorschlag();
						}
					
				}

				queryExecution.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//checke Duplikate

		int countOffers = 0;
		String offerStr = "";
		
	    for(HashMap.Entry<String, String> entry : dokOfferHashMap.entrySet()) {
	        if (!tmpDokOfferHashMap.containsValue(entry.getValue())) {
	        	offerStr = entry.getKey().toString().split("_")[0];
	        	tmpDokOfferHashMap.put(offerStr + "_" + countOffers, entry.getValue());
	        	countOffers = countOffers + 1;
	        }
	    }
	    for(HashMap.Entry<String, String> entry : tmpDokOfferHashMap.entrySet()) {
	    	dokOfferLinkedHashMap.put(entry.getKey(), entry.getValue());
	    }
	    
		
		//SortedSet<String> keys = new TreeSet<String>(richTokenHashMap.keySet());

		// drucke alles im richTokenHashMap aus
		for (String key : dokOfferLinkedHashMap.keySet()) {
//		for (String key : keys) {
			System.out.println(key + ": " + dokOfferLinkedHashMap.get(key) + ", ");
		}

		return dokOfferLinkedHashMap;

	}
	
}
