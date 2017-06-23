package org.semrep.rest.interfaces;

public class InitializeArrayData {
	
	public String[] inputArray;
	
	public String[] initializeArrayDemoData(String eventType) {

		if (eventType == "OfferEvent"){
		
			// richToken
			inputArray = new String[6];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "HighNet_project"; // context
			inputArray[3] = "milestone"; // keyword
			inputArray[4] = "tasks"; // keyword
			inputArray[5] = "leading"; //keyword
		
		} if (eventType == "UserInformationEvent") {
			
			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "6"; // 6 userID
			
		} if (eventType == "DocumentInformationEvent") {
			
			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "1jQFZmcS__-CtScjqd3g5KKM8xepPnMSqaepy2ag2jNc"; // dokumentID
			//inputArray[1] = "1jQFZmcS__-abakhbfeuqh'Pidei#peahd"; //gibts nicht
			
		} if (eventType == "ProjectInformationEvent") {
			
			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "1"; // projectID

		} if (eventType == "DepartmentInformationEvent") {

			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "IT"; // departmentName

		} if (eventType == "AllProjectsEvent") {

			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID

		} if (eventType == "AllProjectRolesEvent") {

			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID

		}

		
		return inputArray;

	}

}
