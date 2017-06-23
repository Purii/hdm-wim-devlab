package org.semrep.rest.businessObjects;

import de.hdm.wim.sharedLib.Constants;

public class Abteilung {
	
	public String abteilung_ID;
	public String abteilung_Name;
	public String abteilung_Kuerzel;
	public String abteilung_hat_Projekt;
	public String abteilung_hat_Mitarbeiter;
	public String abteilung_gehoert_zu_Unternehmen;
	private String sessionID; 
	public String timeStamp;
	private String identity; 
	private String objectValue;


	//Konstruktor 1
	public Abteilung(String sessionID, String timeStamp, String identity, String objectValue)
	{
		this.sessionID = sessionID;
		this.timeStamp = timeStamp;
		this.identity = identity;
		this.objectValue = objectValue;	
	}

	//Konstruktor 2
	public Abteilung(String abteilung_ID, String abteilung_Name, String abteilung_Kuerzel,
					 String abteilung_hat_Projekt, String abteilung_hat_Mitarbeiter,
					 String abteilung_gehoert_zu_Unternehmen)
	{
		this.abteilung_ID = abteilung_ID;
		this.abteilung_Name = abteilung_Name;
		this.abteilung_Kuerzel = abteilung_Kuerzel;
		this.abteilung_hat_Projekt = abteilung_hat_Projekt;
		this.abteilung_hat_Mitarbeiter = abteilung_hat_Mitarbeiter;
		this.abteilung_gehoert_zu_Unternehmen = abteilung_gehoert_zu_Unternehmen;	
	}

	//toString()
	public String toStringAbteilungsObjekt() {
		return Constants.PubSub.AttributeKey.SESSION_ID + ":" + this.sessionID
				+ ", " + Constants.PubSub.AttributeKey.TIMESTAMP + ":" + this.timeStamp
				+ ", " + Constants.PubSub.AttributeKey.TOKEN_ID + ":" + this.identity
				+ ", " + this.objectValue;
	}

	//Objekt leeren
	public void flushAbteilungsObjekt() {
		setAbteilung_ID("");
		setAbteilung_Name("");
		setAbteilung_Kuerzel("");
		setAbteilung_hat_Projekt("");
		setAbteilung_hat_Mitarbeiter("");
		setAbteilung_gehoert_zu_Unternehmen("");
	}

	//Getter und Setter
		
	public String getObjectValue() {

		return objectValue;
	}

	public void setObjectValue(String objectValue) {

		this.objectValue = objectValue;
	}

	public String getSessionID() {

		return sessionID;
	}

	public void setSessionID(String sessionID) {

		this.sessionID = sessionID;
	}
	
	public String getTimeStamp() {

		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {

		this.timeStamp = timeStamp;
	}

	public String getIdentity() {

		return identity;
	}

	public void setIdentity(String identity) {

		this.identity = identity;
	}

	public String getAbteilung_ID() {

		return abteilung_ID;
	}

	public void setAbteilung_ID(String abteilung_ID) {

		this.abteilung_ID = abteilung_ID;
	}

	public String getAbteilung_Name() {

		return abteilung_Name;
	}

	public void setAbteilung_Name(String abteilung_Name) {

		this.abteilung_Name = abteilung_Name;
	}

	public String getAbteilung_Kuerzel() {

		return abteilung_Kuerzel;
	}

	public void setAbteilung_Kuerzel(String abteilung_Kuerzel) {

		this.abteilung_Kuerzel = abteilung_Kuerzel;
	}

	public String getAbteilung_hat_Projekt() {

		return abteilung_hat_Projekt;
	}

	public void setAbteilung_hat_Projekt(String abteilung_hat_Projekt) {

		this.abteilung_hat_Projekt = abteilung_hat_Projekt;
	}

	public String getAbteilung_hat_Mitarbeiter() {

		return abteilung_hat_Mitarbeiter;
	}

	public void setAbteilung_hat_Mitarbeiter(String abteilung_hat_Mitarbeiter) {

		this.abteilung_hat_Mitarbeiter = abteilung_hat_Mitarbeiter;
	}

	public String getAbteilung_gehoert_zu_Unternehmen() {

		return abteilung_gehoert_zu_Unternehmen;
	}

	public void setAbteilung_gehoert_zu_Unternehmen(String abteilung_gehoert_zu_Unternehmen) {

		this.abteilung_gehoert_zu_Unternehmen = abteilung_gehoert_zu_Unternehmen;
	}
	
}
