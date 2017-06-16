package models;

import java.security.Timestamp;

public class TextInformation {
	private String sessionID;
	private String userID;
	private String textresultat;
	private int timestamp;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getTextresultat() {
		return textresultat;
	}
	public void setTextresultat(String textresultat) {
		this.textresultat = textresultat;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

}
