package org.semrep.rest.helper;

/**
 * Created by mateo_alliaj on 25.06.17.
 */
public class TokenDemoData {

	public String[] inputArray;

	public String[] simulateTokenData(int numberOfTokens, String eventType) {

		if (numberOfTokens == 0 && eventType == EventNameConstants.TOKEN_EVENT){

			// richToken
			inputArray = new String[3];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "Videokonferenz"; // context

		} if (numberOfTokens == 1 && eventType == EventNameConstants.TOKEN_EVENT){

			// richToken
			inputArray = new String[4];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "Videokonferenz"; // context
			inputArray[3] = "milestone"; // keyword


		}

		if (numberOfTokens == 2 && eventType == EventNameConstants.TOKEN_EVENT){

			// richToken
			inputArray = new String[5];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "Videokonferenz"; // context
			inputArray[3] = "milestone"; // keyword
			inputArray[4] = "tasks"; // keyword


		} if (numberOfTokens == 3 && eventType == EventNameConstants.TOKEN_EVENT){

			// richToken
			inputArray = new String[6];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "Videokonferenz"; // context
			inputArray[3] = "milestone"; // keyword
			inputArray[4] = "tasks"; // keyword
			inputArray[5] = "leading"; //keyword

		} if (numberOfTokens == 6 && eventType == EventNameConstants.TOKEN_EVENT){

			// richToken
			inputArray = new String[9];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "Videokonferenz"; // context
			inputArray[3] = "milestone"; // keyword
			inputArray[4] = "in"; // keyword
			inputArray[5] = "project"; //keyword
			inputArray[6] = "to"; // keyword
			inputArray[7] = "do"; // keyword
			inputArray[8] = "list"; //keyword

		} if (numberOfTokens == 9 && eventType == EventNameConstants.TOKEN_EVENT){

			// richToken
			inputArray = new String[12];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "Videokonferenz"; // context
			inputArray[3] = "activities"; // keyword
			inputArray[4] = "concerning"; // keyword
			inputArray[5] = "HighNet"; //keyword
			inputArray[6] = "project"; // keyword
			inputArray[7] = "list"; // keyword
			inputArray[8] = "next"; //keyword
			inputArray[9] = "week"; // keyword
			inputArray[10] = "you"; // keyword
			inputArray[11] = "me"; //keyword

		}


		return inputArray;

	}


}
