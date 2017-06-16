package models;

import java.util.ArrayList;

public class ElasticsearchResult {
	private String userID;
	private String sessionID;
	private ArrayList<String> listTokens;
	private int timestamp;
	private ArrayList<String> listKontexte;
	
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
	public ArrayList<String> getListTokens() {
		return listTokens;
	}
	public void setListTokens(ArrayList<String> listTokens) {
		this.listTokens = listTokens;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	public ArrayList<String> getListKontexte() {
		return listKontexte;
	}
	public void setListKontexte(ArrayList<String> listKontexte) {
		this.listKontexte = listKontexte;
	}
	
	

}
