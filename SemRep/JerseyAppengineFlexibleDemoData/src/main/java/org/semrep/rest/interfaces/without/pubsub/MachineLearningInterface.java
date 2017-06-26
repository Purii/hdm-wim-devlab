package main.java.org.semprep.rest.interfaces.without.pubsub;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

import main.java.org.semprep.rest.businessObjects.Dokumentvorschlag;
import main.java.org.semprep.rest.businessObjects.Person;
import main.java.org.semrep.rest.helper.Constants;
import main.java.org.semrep.rest.helper.FusekiConfigConstants;

@Path("/machineLearningInterface")
public class MachineLearningInterface {
	


}
