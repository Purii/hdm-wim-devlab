package semRepServices.interfaces;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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

public class InternalInterface {

	public static void main(String[] args) {

		getProjects();

	}

	public static LinkedHashMap<String, String> getProjects() {

		String filePath = "src/semRepServices/interfaces/Ontology.owl";
		OntModel ontologyModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

		LinkedHashMap<String, String> projektHashMap = new LinkedHashMap<String, String>();

		try {
			File file = new File(filePath);
			FileReader fileReader = new FileReader(file);
			ontologyModel.read(fileReader, null, "TTL");

			// initialisiere Variablen
			// sparql
			String sparql = "";

			// projekt
			String projektNameStr = "";


			// initialisiere Objekte
			// person

			sparql = " PREFIX ontology: <http://www.semanticweb.org/sem-rep/ontology#> " + "SELECT DISTINCT ?Projekte "
					+ "WHERE { " + "?x ontology:Projekt_Name ?Projekte . " + "} " + "ORDER BY ?Projekte";

			if (sparql != "") {

				// Initialisierung und Ausführung einer SPARQL-Query
				//Query query = QueryFactory.create(sparql);
				//QueryExecution queryExecution = QueryExecutionFactory.create(query, ontologyModel);
				QueryExecution queryExecution =
				QueryExecutionFactory.sparqlService("http://35.187.45.171:3030/ontology/query",
						sparql);

				// Initialisierung von Resultset für Ergebniswerte der
				// SPARQL-Query
				ResultSet resultSet = queryExecution.execSelect();

				// initialisiere Variablen
				String splitResult = "";
				int indexOfToSplitCharacter;

				for (int i = 0; resultSet.hasNext() == true; i++) {
					QuerySolution querySolution = resultSet.nextSolution();
					projektHashMap.put("Projekt_" + i, "");
					
					for (int j = 0; j < resultSet.getResultVars().size(); j++) {
						String results = resultSet.getResultVars().get(j).toString();
						RDFNode rdfNode = querySolution.get(results);

						indexOfToSplitCharacter = rdfNode.toString().indexOf("#");
						splitResult = rdfNode.toString().substring(indexOfToSplitCharacter + 1);

						
							// einmaliges befüllen der nachfolgenden Werte
							if (((projektHashMap.get("Projekt_" + i) == "") == true)) {

								switch (results) {
								case "Projekte":
									projektNameStr = splitResult;
									projektHashMap.put("Projekt_" + i, projektNameStr);
									break;
								}

							}

					}

				}

				queryExecution.close();

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// drucke alles im richTokenHashMap aus
		for (String key : projektHashMap.keySet()) {
			System.out.println(key + ": " + projektHashMap.get(key));
		}

		return projektHashMap;

	}

}
