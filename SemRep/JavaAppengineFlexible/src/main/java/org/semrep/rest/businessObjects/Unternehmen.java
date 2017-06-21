package main.java.org.semrep.rest.businessObjects;

public class Unternehmen {
	private String unternehmensName;
	private String sessionID;
	private String objectValue;
	
	public Unternehmen(String unternehmensName){
		this.unternehmensName = unternehmensName;
	}
	
	public Unternehmen(String sessionID, String objectValue){
		this.sessionID = sessionID;
		this.objectValue = objectValue;
	}
	
	public void flushUnternehmensObjekt(){
		setUnternehmensName("");
	}
	
	public String toStringUnternehmenObjekt() {
		return "SessionID=" + this.sessionID
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
		
}
