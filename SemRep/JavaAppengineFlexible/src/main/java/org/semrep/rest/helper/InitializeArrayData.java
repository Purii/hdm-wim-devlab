package org.semrep.rest.helper;

/**
 * The type Initialize array data.
 */
public class InitializeArrayData {

	/**
	 * The Input array.
	 */
	public String[] inputArray;

	/**
	 * Initialize array demo data string [ ].
	 *
	 * @param eventType the event type
	 * @return the string [ ]
	 */
	public String[] initializeArrayDemoData(String eventType) {

		if (eventType == EventNameConstants.OFFER_EVENT){
		
			// richToken
			inputArray = new String[6];
			inputArray[0] = "1"; // sessionID
			inputArray[1] = "6"; // userID
			inputArray[2] = "HighNet_project"; // context
			inputArray[3] = "milestone"; // keyword
			inputArray[4] = "tasks"; // keyword
			inputArray[5] = "leading"; //keyword
		
		} if (eventType == EventNameConstants.USER_INFORMATION_EVENT) {
			
			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "6"; // 6 userID
			
		} if (eventType == EventNameConstants.DOCUMENT_INFORMATION_EVENT) {
			
			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "1jQFZmcS__-CtScjqd3g5KKM8xepPnMSqaepy2ag2jNc"; // dokumentID
			//inputArray[1] = "1jQFZmcS__-abakhbfeuqh'Pidei#peahd"; //gibts nicht
			
		} if (eventType == EventNameConstants.PROJECT_INFORMATION_EVENT) {
			
			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "1"; // projectID

		} if (eventType == EventNameConstants.DEPARTMENT_INFORMATION_EVENT) {

			inputArray = new String[2];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "IT"; // departmentName

		} if (eventType == EventNameConstants.ALL_PROJECTS_EVENT
				|| eventType == EventNameConstants.ALL_PROJECTROLES_EVENT
				|| eventType == EventNameConstants.ALL_DEPARTMENTS_EVENT
				|| eventType == EventNameConstants.ALL_COMPANIES_EVENT) {

			inputArray = new String[1];
			inputArray[0] = "793dnj"; // sessionID

		} if (eventType == EventNameConstants.ADDITIONAL_USER_INFORMATION_EVENT) {

			inputArray = new String[8];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "873267"; // PersonID
			inputArray[2] = "Peng"; // Vorname
			inputArray[3] = "Scheng"; // Nachname
			inputArray[4] = "peng.scheng@starcars.com"; // Email
			inputArray[5] = "IT"; // Abteilung
			inputArray[6] = "HighNet"; // Projekt
			inputArray[7] = "Entwickler"; // Projektrolle

		} if (eventType == EventNameConstants.DOCUMENT_CONTEXT_EVENT) {

			inputArray = new String[2];
			inputArray[0] = "Projektplanung_HighNet"; // DokumentName
			inputArray[1] = "HighNet_Project"; // DokumentKontext

		} if (eventType == EventNameConstants.DOCUMENT_CALL_EVENT) {

			inputArray = new String[4];
			inputArray[0] = "793dnj"; // sessionID
			inputArray[1] = "Kristi"; // PersonVorname
			inputArray[2] = "Misti"; // PersonNachname
			inputArray[3] = "Projektplanung_HighNet"; // DokumentName

		} if (eventType == EventNameConstants.LEARN_EVENT) {

			inputArray = new String[3];
			inputArray[0] = "Kristi"; // PersonVorname
			inputArray[1] = "Misti"; // PersonNachname
			inputArray[2] = "Projektplanung_HighNet"; // DokumentName

		}

		return inputArray;

	}

}
