package org.semrep.rest.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The type Dynamic token concatenater.
 */
public class DynamicTokenConcatenater {

	private static String sparqlKeyword = "?Dok_Keywords = '";
	private static String orStr = "' || ";
	private static String endORStr = "||";
	private static String andStr = " ' && '";
	private static String filter = "FILTER ( ";
	private static String toHandOverString = "";


	/**
	 * Concatenate token string.
	 *
	 * @param inputArray the input array
	 * @return the string
	 */
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

			ArrayList<String> rememberDokNameArrList = new ArrayList<String>();

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
				// ersten drei inputs sind keine keywords
				int min = 3;
				int max = inputArray.length - 1;
				int anzahlKeywords = inputArray.length - min;
				//berechne die Fakultät der Keywords für die Kombinationen
				long fakultaet = 1;
				if (anzahlKeywords >= 2) {
					for (int i = 1; i <= anzahlKeywords; i++) {
						fakultaet = fakultaet * i;
					}
				}
				int randomNumber = 0;

				String tmpFilter = " '";

				for (int y = 0; y < fakultaet; y++) {

					// verkette zufällig keywords
					for (int z = 3; z < inputArray.length; z++){

						randomNumber = random.nextInt(max + 1 - min) + min;

						// kommt noch ein weiteres Keyword?
						if (z + 1 == inputArray.length){
							tmpFilter = tmpFilter  + inputArray[randomNumber] + "' ";
							//filter = filter + sparqlKeyword + inputArray[randomNumber] + "' ";
						} else {
							tmpFilter = tmpFilter + inputArray[randomNumber] + andStr;
							//filter = filter + sparqlKeyword + inputArray[randomNumber] + andStr;
						}

						sparqlKeyword = "";

					}

					if (!(rememberDokNameArrList.contains(tmpFilter))){
						rememberDokNameArrList.add(tmpFilter);
					}

					tmpFilter = " '";

				}

				for (int x = 0; x < rememberDokNameArrList.size(); x++) {
					if (x + 1 != rememberDokNameArrList.size()) {
						filter = filter + rememberDokNameArrList.get(x) + endORStr;
					} else {
						filter = filter + rememberDokNameArrList.get(x);
					}
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

}
