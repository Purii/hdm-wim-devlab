package main.java.org.semrep.rest.businessObjects;

import main.java.org.semrep.rest.helper.Constants;

public class Unternehmen {
	private String unternehmensName;
	private String sessionID;
	private String timeStamp;
	private String identity;
	private String objectValue;
	
	public Unternehmen(String unternehmensName){

		this.unternehmensName = unternehmensName;
	}
	
	public Unternehmen(String sessionID, String timeStamp,
					   String identity, String objectValue){
		this.sessionID = sessionID;
		this.timeStamp = timeStamp;
		this.identity = identity;
		this.objectValue = objectValue;
	}
	
	public void flushUnternehmensObjekt(){

		setUnternehmensName("");
	}
	
	public String toStringUnternehmenObjekt() {
		return Constants.PubSub.AttributeKey.SESSION_ID + ":" + this.sessionID
			+ ", " + Constants.PubSub.AttributeKey.TIMESTAMP + ":" + this.timeStamp
			+ ", " + Constants.PubSub.AttributeKey.TOKEN_ID + ":" + this.identity
			+ ", " + this.objectValue;
	}

	public String getUnternehmensName() {

		return unternehmensName;
	}

	public void setUnternehmensName(String unternehmensName) {

		this.unternehmensName = unternehmensName;
	}

	public String getSessionID() {

		return sessionID;
	}

	public void setSessionID(String sessionID) {

		this.sessionID = sessionID;
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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
