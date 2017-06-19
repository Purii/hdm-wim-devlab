package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nilsb on 19.06.2017.
 */
public class TokenEvent implements IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	// auto set EventType on init
	public TokenEvent() {
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.TOKEN);
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
		return attributes.get(AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID, userId);
	}

	public String getSessionId(){
		return attributes.get(AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId) {
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	public String getEventType(){
		return attributes.get(AttributeKey.EVENT_TYPE).toString();
	}

	public String getTokens() {
		return attributes.get(AttributeKey.TOKENS).toString();
	}

	public void setTokens(ArrayList<String> tokens) {
		this.attributes.put(AttributeKey.TOKENS, tokens.toString());
	}

	public String getContexts() {
		return attributes.get(AttributeKey.CONTEXTS).toString();
	}

	public void setContexts(ArrayList<String> contexts) {
		this.attributes.put(AttributeKey.CONTEXTS, contexts.toString());
	}

	/*
	public void setEventType(String EventType){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType);
	}
	*/

	public String getEventSource(){
		return attributes.get(AttributeKey.EVENT_SOURCE).toString();
	}

	public void setEventSource(String EventSource){
		this.attributes.put(AttributeKey.EVENT_SOURCE, EventSource);
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
