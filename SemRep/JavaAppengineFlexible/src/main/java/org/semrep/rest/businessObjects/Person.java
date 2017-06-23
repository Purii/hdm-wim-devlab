package org.semrep.rest.businessObjects;

import de.hdm.wim.sharedLib.Constants;

public class Person {

	public String person;
	public String id;
	public String klasse;
	public String vorname;
	public String nachname;
	public String name;
	public String mail;
	public String person_arbeitet_an_Projekt;
	public String person_hat_Projektrolle;
	public String person_gehoert_zu_Abteilung;
	public String person_hat_Dokument_verfasst;
	public String person_ruft_Dokument_auf;
	public String person_favorisiert_Dokument;

	private String sessionID;
	private String timeStamp;
	private String identity;
	private String objectValue;
	
	public Person(String sessionID, String objectValue) {

		this.sessionID = sessionID;
		this.objectValue = objectValue;

	}
	
	public Person(String name) {

		this.name = name;

	}

	public Person(String sessionID, String identity, String objectValue) {

		this.sessionID = sessionID;
		this.identity = identity;
		this.objectValue = objectValue;

	}
	
	public Person(String sessionID, String timeStamp, String identity, String objectValue) {

		this.sessionID = sessionID;
		this.timeStamp = timeStamp;
		this.identity = identity;
		this.objectValue = objectValue;

	}

	public Person(String person, String id, String klasse, String vorname, String nachname, String mail,
			String person_arbeitet_an_Projekt, String person_hat_Projektrolle, String person_gehoert_zu_Abteilung,
			String person_hat_Dokument_verfasst, String person_ruft_Dokument_auf, String person_favorisiert_Dokument) {
		this.person = person;
		this.id = id;
		this.klasse = klasse;
		this.vorname = vorname;
		this.nachname = nachname;
		this.mail = mail;
		this.person_arbeitet_an_Projekt = person_arbeitet_an_Projekt;
		this.person_hat_Projektrolle = person_hat_Projektrolle;
		this.person_gehoert_zu_Abteilung = person_gehoert_zu_Abteilung;
		this.person_hat_Dokument_verfasst = person_hat_Dokument_verfasst;
		this.person_ruft_Dokument_auf = person_ruft_Dokument_auf;
		this.person_favorisiert_Dokument = person_favorisiert_Dokument;
	}
	
	public void flushPersonObj(){
		setPerson("");
		setId("");
		setKlasse("");
		setVorname("");
		setNachname("");
		setMail("");
		setPerson_arbeitet_an_Projekt("");
		setPerson_hat_Projektrolle("");
		setPerson_gehoert_zu_Abteilung("");
		setPerson_hat_Dokument_verfasst("");
		setPerson_ruft_Dokument_auf("");
		setPerson_favorisiert_Dokument("");
		
	}

	public String toStringPersonObjekt() {
		return Constants.PubSub.AttributeKey.SESSION_ID + ":" + this.sessionID
				+ ", " + Constants.PubSub.AttributeKey.TIMESTAMP + ":" + this.timeStamp
				+ ", " + Constants.PubSub.AttributeKey.TOKEN_ID + ":" + this.identity
				+ ", " + this.objectValue;
	}
	
	public String toStringUserInformationEvent() {
		return Constants.PubSub.AttributeKey.SESSION_ID + ":" + this.sessionID
				+ ", " + Constants.PubSub.AttributeKey.TIMESTAMP + ":" + this.timeStamp
				+ ", " + Constants.PubSub.AttributeKey.TOKEN_ID + ":" + this.identity
				+ ", " + this.objectValue;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPerson_arbeitet_an_Projekt() {
		return person_arbeitet_an_Projekt;
	}

	public void setPerson_arbeitet_an_Projekt(String person_arbeitet_an_Projekt) {
		this.person_arbeitet_an_Projekt = person_arbeitet_an_Projekt;
	}

	public String getPerson_hat_Projektrolle() {
		return person_hat_Projektrolle;
	}

	public void setPerson_hat_Projektrolle(String person_hat_Projektrolle) {
		this.person_hat_Projektrolle = person_hat_Projektrolle;
	}

	public String getPerson_gehoert_zu_Abteilung() {
		return person_gehoert_zu_Abteilung;
	}

	public void setPerson_gehoert_zu_Abteilung(String person_gehoert_zu_Abteilung) {
		this.person_gehoert_zu_Abteilung = person_gehoert_zu_Abteilung;
	}

	public String getPerson_hat_Dokument_verfasst() {
		return person_hat_Dokument_verfasst;
	}

	public void setPerson_hat_Dokument_verfasst(String person_hat_Dokument_verfasst) {
		this.person_hat_Dokument_verfasst = person_hat_Dokument_verfasst;
	}

	public String getPerson_ruft_Dokument_auf() {
		return person_ruft_Dokument_auf;
	}

	public void setPerson_ruft_Dokument_auf(String person_ruft_Dokument_auf) {
		this.person_ruft_Dokument_auf = person_ruft_Dokument_auf;
	}

	public String getPerson_favorisiert_Dokument() {
		return person_favorisiert_Dokument;
	}

	public void setPerson_favorisiert_Dokument(String person_favorisiert_Dokument) {
		this.person_favorisiert_Dokument = person_favorisiert_Dokument;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
