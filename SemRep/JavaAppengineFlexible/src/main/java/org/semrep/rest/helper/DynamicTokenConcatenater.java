package org.semrep.rest.helper;

import java.util.Random;

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

	private static String sparqlKeyword = "?Dok_Keywords = '";
	private static String orStr = "' || ";
	private static String endORStr = "||";
	private static String andStr = " ' && '";
	private static String filter = "FILTER ( ";
	private static String toHandOverString = "";


	public static String concatenateToken(String[] inputArray){


		// min 1 keyword
		if (inputArray.length >= 4) {

			// ab stelle 3 im Array beginnen die Tokens
			for (int i = 3; i < inputArray.length; i++){

				// kommt noch ein weiteres Keyword?
				if (i + 1 == inputArray.length){
					filter = filter + sparqlKeyword + inputArray[i] + "' ";
				} else {
					filter = filter + sparqlKeyword + inputArray[i] + orStr;
				}

			}

			toHandOverString = filter;
			filter = "";

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

			// min 2 keywords
			if (inputArray.length >= 5) {

				toHandOverString = toHandOverString + endORStr;

				// ab stelle 3 im Array beginnen die Tokens
				for (int z = 3; z < inputArray.length; z++){

					// kommt noch ein weiteres Keyword?
					if (z + 1 == inputArray.length){
						filter = filter + sparqlKeyword + inputArray[z] + "' ";
					} else {
						filter = filter + sparqlKeyword + inputArray[z] + andStr;
					}

					sparqlKeyword = "";

				}

				toHandOverString = toHandOverString + " " + filter + endORStr + " ";
				filter = "";
				sparqlKeyword = "?Dok_Keywords = '";

				// beginne vom letzten Keyword zum ersten
				for (int z = inputArray.length - 1; z > 2; z--){

					// kommt noch ein weiteres Keyword?
					if (z - 1 == 2){
						filter = filter + sparqlKeyword + inputArray[z] + "' ";
					} else {
						filter = filter + sparqlKeyword + inputArray[z] + andStr;
					}

					sparqlKeyword = "";

				}

				toHandOverString = toHandOverString + filter + endORStr + " ";
				filter = "";
				sparqlKeyword = "?Dok_Keywords = '";

				Random random = new Random();
				int min = 3;
				int max = inputArray.length - 1;
				int anzahlKeywords = (int) Math.pow((inputArray.length - min), 3);
				int randomNumber = 0;

				// verkette zufällig keywords
				for (int z = 3; z < inputArray.length; z++){

					randomNumber = random.nextInt(max + 1 - min) + min;

					// kommt noch ein weiteres Keyword?
					if (z + 1 == inputArray.length){
						filter = filter + sparqlKeyword + inputArray[randomNumber] + "' ";
					} else {
						filter = filter + sparqlKeyword + inputArray[randomNumber] + andStr;
					}

					sparqlKeyword = "";

				}

				toHandOverString = toHandOverString + filter;
				filter = "";

			}

		} if (inputArray.length < 4) {

			filter = "";

		}

		toHandOverString = toHandOverString + ") ";


		return toHandOverString;
	}

	public static void main(String[] args) {

		try {
			System.out.print(concatenateToken(TokenDemoData.simulateTokenData(6, EventNameConstants.TOKEN_EVENT)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
