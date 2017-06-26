package main.java.org.semrep.rest.businessObjects;

import main.java.org.semrep.rest.helper.Constants;

public class Projektrolle {

	public String projektrolle;

	private String sessionID;
	private String timeStamp;
	private String identity;
	private String objectValue;

	public Projektrolle(String projektrolle) {
		this.projektrolle = projektrolle;
	}

	public Projektrolle(String sessionID, String timeStamp, String identity, String objectValue) {
		this.sessionID = sessionID;
		this.timeStamp = timeStamp;
		this.identity = identity;
		this.objectValue = objectValue;
	}

	public String toStringProjektrolleObj() {

		return Constants.PubSub.AttributeKey.SESSION_ID + ":" + this.sessionID
			+ ", " + Constants.PubSub.AttributeKey.TIMESTAMP + ":" + this.timeStamp
			+ ", " + Constants.PubSub.AttributeKey.TOKEN_ID + ":" + this.identity
			+ ", " + this.objectValue;

	}

	public void flushProjektrolleObj(){
		setProjektrolle("");
	}

	public String getProjektrolle() {

		return projektrolle;
	}

	public void setProjektrolle(String projektrolle) {

		this.projektrolle = projektrolle;
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

	public String getObjectValue() {

		return objectValue;
	}

	public void setObjectValue(String objectValue) {

		this.objectValue = objectValue;
	}

}
