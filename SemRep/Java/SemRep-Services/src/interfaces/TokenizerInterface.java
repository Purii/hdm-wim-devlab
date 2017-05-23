package interfaces;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

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

public class TokenizerInterface {

	public static String[] inputArray = new String[4];

	public static void main(String[] args) {

		setArrayDemoData();
		getDocumentMetaData();
	}

	public static void setArrayDemoData() {
		inputArray[0] = "4";
		inputArray[1] = "activities";
		inputArray[2] = "HighNet";
		inputArray[3] = "project";
	}

	/*
	PersonArray - x
	ProjektArray - leer/Alternative b
	DokumentArray - x
	*/
	
	public static ArrayList<String> getDocumentMetaData() {

		String filePath = "src/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			for (int y = 0; y < inputArray.length; y++) {
							
			String sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> "
					+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>" 
					+ "SELECT DISTINCT ?Person ?ID ?Klasse ?Vorname ?Nachname ?mail ?Status ?Projekt ?Projektrolle ?Abteilung ?Dokument ?Aufruf "
					+ "WHERE { "
					+ "?Person a ?Klasse . "
					+ "?Person ontology:Person_ID ?ID . "
					+ "?Person ontology:Person_Vorname ?Vorname . "
					+ "?Person ontology:Person_Nachname ?Nachname . "
					+ "?Person ontology:Person_Email ?mail . "
					+ "?Person ontology:Person_Mitarbeiter ?Status ."
					+ "?Person ontology:Person_arbeitet_an_Projekt ?Projekt . "
					+ "?Person ontology:Person_hat_Projektrolle ?Projektrolle . "
					+ "?Person ontology:Person_gehoert_zu_Abteilung ?Abteilung . "
					+ "?Person ontology:Person_hat_Dokument_verfasst ?Dokument ."
					+ "?Person ontology:Person_ruft_Dokument_auf ?Aufruf ."
					//+ "?individual rdf:type ontology:Externer_Mitarbeiter . "
					// Eingrenzung auf userID
					+ "?Person ontology:Person_ID '"
					+ inputArray[y].toString() + "' ." 
					+ "}";

			// Initialisierung und Ausführung einer SPARQL-Query
			Query query = QueryFactory.create(sparql);
			QueryExecution queryExecution = QueryExecutionFactory.create(query, ontologyModel);

			// Initialisierung von Resultset für Ergebniswerte der SPARQL-Query
			ResultSet resultSet = queryExecution.execSelect();

			String splitResult = "";
			int indexOfToSplitCharacter;

				// Ergebniswerte werden für Konsolendarstellung aufbereitet
				for (int i = 0; resultSet.hasNext() == true; i++) {
					QuerySolution querySolution = resultSet.nextSolution();
					for (int j = 0; j < resultSet.getResultVars().size(); j++) {
						String results = resultSet.getResultVars().get(j).toString();
						RDFNode rdfNode = querySolution.get(results);
	
						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
						splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);
						
						if (splitResult == "NamedIndividual") {
							break;
						}
	
						System.out.println(results + ": " +splitResult);
					}
					System.out.print("\n");
				}

			queryExecution.close();
			
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;

	}

	public ArrayList<String> generateKeywordList(String keyword) {

		return null;

	}

	public void sortKeywords() {

	}

	public HashMap<String, String> generateRichTokenHashMap(String keyword) {
		return null;

	}

}
