package models;

import java.util.ArrayList;

public class CustomAnalyzer {
	private String type;
	private String tokenizer;
	private ArrayList<String> filter;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTokenizer() {
		return tokenizer;
	}
	public void setTokenizer(String tokenizer) {
		this.tokenizer = tokenizer;
	}
	public ArrayList<String> getFilter() {
		return filter;
	}
	public void setFilter(ArrayList<String> filter) {
		this.filter = filter;
	}
}
