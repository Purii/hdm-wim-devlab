package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class DocumentRelevantEvent implements IEvent{

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	//Todo: Update EventType
	public DocumentRelevantEvent(){
		this.attributes.put(Constants.PubSub.AttributeKey.EVENT_TYPE, Constants.PubSub.EventType.INSIGHT);
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
		this.id= id;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getEventType() {
		return attributes.get(Constants.PubSub.AttributeKey.EVENT_TYPE).toString();
	}

	/*
	public void setEventType(String EventType){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType);
	}
	*/

	public String getEventSource() {
		return attributes.get(Constants.PubSub.AttributeKey.EVENT_SOURCE).toString();
	}

	public void setEventSource(String EventSource) {
		this.attributes.put(Constants.PubSub.AttributeKey.EVENT_SOURCE, EventSource);
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}


	public String getUserId(){
		return attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}

	public String getDocumentId(){
		return attributes.get(Constants.PubSub.AttributeKey.DOCUMENT_ID.toString());
	}

	public void setDocumentId(String documentId){
		this.attributes.put(Constants.PubSub.AttributeKey.DOCUMENT_ID, documentId);
	}
}
