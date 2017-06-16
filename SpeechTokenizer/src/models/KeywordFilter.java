package models;

import java.util.ArrayList;

public class KeywordFilter {
	private String tokenWithoutKeywords;
	private ArrayList<String> listFilteredKeywords;
	public String getTokenWithoutKeywords() {
		return tokenWithoutKeywords;
	}
	public void setTokenWithoutKeywords(String tokenWithoutKeywords) {
		this.tokenWithoutKeywords = tokenWithoutKeywords;
	}
	public ArrayList<String> getListFilteredKeywords() {
		return listFilteredKeywords;
	}
	public void setListFilteredKeywords(ArrayList<String> listFilteredKeywords) {
		this.listFilteredKeywords = listFilteredKeywords;
	}

}
