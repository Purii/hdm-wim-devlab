package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by nilsb on 19.06.2017.
 */
public class TokenEvent implements IEvent {

	private String data;
	private String id;
	private String publishTime;
	private ArrayList<String> tokens;
	private ArrayList<String> contexts;
	private Hashtable attributes = new Hashtable<String, String>();

	// auto set EventType on init
	public TokenEvent() {
		this.attributes.put(Constants.PubSub.AttributeKey.EVENT_TYPE, Constants.PubSub.EventType.TOKEN);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getUserId(){
		return attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}

	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId) { this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, sessionId); }

	public String getEventType(){
		return attributes.get(Constants.PubSub.AttributeKey.EVENT_TYPE).toString();
	}

	public ArrayList<String> getTokens() {
		return tokens;
	}

	public void setTokens(ArrayList<String> tokens) {
		this.tokens = tokens;
	}

	public ArrayList<String> getContexts() {
		return contexts;
	}

	public void setContexts(ArrayList<String> contexts) {
		this.contexts = contexts;
	}

	/*
	public void setEventType(String EventType){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType);
	}
	*/

	public String getEventSource(){
		return attributes.get(Constants.PubSub.AttributeKey.EVENT_SOURCE).toString();
	}

	public void setEventSource(String EventSource){
		this.attributes.put(Constants.PubSub.AttributeKey.EVENT_SOURCE, EventSource);
	}

	public Hashtable<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Hashtable<String, String> attributes) {
		this.attributes = attributes;
	}
}
