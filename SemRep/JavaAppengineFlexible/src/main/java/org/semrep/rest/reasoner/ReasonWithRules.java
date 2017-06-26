package org.semrep.rest.reasoner;

//import com.hp.hpl.jena.rdf.model.Model;
//import com.hp.hpl.jena.rdf.model.ModelFactory;
//import com.hp.hpl.jena.rdf.model.Resource;
//import com.hp.hpl.jena.rdf.model.RDFNode;
//import com.hp.hpl.jena.rdf.model.Property;
//import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
//import com.hp.hpl.jena.reasoner.Reasoner;
//import com.hp.hpl.jena.reasoner.rulesys.Rule;
//
//import riotcmd.rdfxml;
//
//import com.hp.hpl.jena.rdf.model.InfModel;
//import com.hp.hpl.jena.rdf.model.StmtIterator;
//import com.hp.hpl.jena.rdf.model.Statement;
import java.net.MalformedURLException;


/**
 *
 * @author mateo_alliaj
 * The type Reason with rules.
 *
 * Reasoner für neue Instanzen in der Ontologie
 * Inferiert neue Zusammenhänge anhand von festgelegten Rules
 *
 */
public class ReasonWithRules {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @throws MalformedURLException the malformed url exception
	 */
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub

//		Model instances = ModelFactory.createDefaultModel();
//		// instances.read ("file:dataset.txt","N3");
//		instances.read("file:dataset3.txt", null);
//		Reasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL("file:rule2.txt"));
//		// GenericRuleReasoner(Rule.rulesFromURL("file:rule.txt"));
//		reasoner.setDerivationLogging(true);
//		InfModel inf = ModelFactory.createInfModel(reasoner, instances);
//
//		// print out the statements in the model
//		StmtIterator iter = inf.listStatements();
//		while (iter.hasNext()) {
//			Statement stmt = iter.nextStatement();
//			Resource subject = stmt.getSubject();
//			Property predicate = stmt.getPredicate();
//			RDFNode object = stmt.getObject();
//
//			System.out.print(subject.toString());
//			System.out.print(" " + predicate.toString() + " ");
//			if (object instanceof Resource) {
//				System.out.print(object.toString());
//			} else {
//				// object is a literal
//				System.out.print(" \"" + object.toString() + "\"");
//			}
//			System.out.println(" .");
//		}
//
	}

}