package org.semrep.rest.helper;

/**
 *
 */
public class DynamicTokenConcatenater {

	// 1 - n ( || )
//		"FILTER ( "
//		+ "?Dok_Keywords = '" + inputArray[3].toString() + "' || " + "?Dok_Keywords = '"
//		+ inputArray[4].toString() + "' || " + "?Dok_Keywords = '" + inputArray[5].toString()
//						+ "' || "
//
		// 1 - 1+1 ( && )
	// ( || )
// + "?Dok_Keywords = '" + inputArray[4].toString() + " ' && '" + inputArray[3].toString()
// + "' || " + "?Dok_Keywords = '" + inputArray[3].toString()
//						+ " ' && '" + inputArray[4].toString()
// + "' || " + "?Dok_Keywords = '"
//		+ inputArray[4].toString() + " ' && '" + inputArray[5].toString()
// + "' || " + "?Dok_Keywords = '"
// + inputArray[5].toString() + " ' && '" + inputArray[4].toString()
//	+ "' || " + "?Dok_Keywords = '"
// + inputArray[3].toString() + " ' && '" + inputArray[5].toString()
// + "' || " + "?Dok_Keywords = '" + inputArray[5].toString()
//						+ " ' && '" + inputArray[3].toString()

	// 1 - n ( && )
// + "' || " + "?Dok_Keywords = '"
//		+ inputArray[4].toString() + " ' && '" + inputArray[3].toString() + " ' && '"
//		+ inputArray[5].toString()
// + "' || " + "?Dok_Keywords = '" + inputArray[4].toString()
//						+ " ' && '" + inputArray[5].toString() + " ' && '" + inputArray[3].toString() + "' || "
//		+ "?Dok_Keywords = '" + inputArray[3].toString() + " ' && '" + inputArray[4].toString()
//						+ " ' && '" + inputArray[5].toString() + "' || " + "?Dok_Keywords = '"
//		+ inputArray[3].toString() + " ' && '" + inputArray[5].toString() + " ' && '"
//		+ inputArray[4].toString() + "' || " + "?Dok_Keywords = '" + inputArray[5].toString()
//						+ " ' && '" + inputArray[4].toString() + " ' && '" + inputArray[3].toString() + "' || "
//		+ "?Dok_Keywords = '" + inputArray[5].toString() + " ' && '" + inputArray[3].toString()
//						+ " ' && '" + inputArray[4].toString() + "' " + ") " + "}" + "ORDER BY ?Dok_Name";

	private static String toConcatenateSelection = "?Dok_Keywords = '";
	private static String toConcatenatePatternOR = "' || ";
	private static String toConcatenatePatternAND = " ' && '";

	public String concatenateToken(){

		return null;
	}


}
