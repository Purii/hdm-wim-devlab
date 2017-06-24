package de.hdm.wim.eventServices.eventProcessing.helper;

/**
 * Created by Chris on 24.06.2017.
 */
public class ActiveUsers {

	int activeUsers=0;
	String sessionId;

	public ActiveUsers(){}

	public void userJoinedSession(){
		activeUsers++;
	}

	public void userLeftSession(){
		activeUsers--;
	}

	public Integer getActiveUsers(){
		return activeUsers;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
