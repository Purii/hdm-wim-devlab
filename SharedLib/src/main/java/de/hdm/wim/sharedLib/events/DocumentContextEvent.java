package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 19.06.17.
 */
public class DocumentContextEvent implements IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	// TODO: Update Event Type
	// auto set EventType on init
	public DocumentContextEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.INSIGHT);
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
		return attributes.get(AttributeKey.EVENT_TYPE).toString();
	}

	/*
	public void setEventType(String EventType){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType);
	}
	*/

	public String getEventSource() {
		return attributes.get(AttributeKey.EVENT_SOURCE).toString();
	}

	public void setEventSource(String EventSource) {
		this.attributes.put(AttributeKey.EVENT_SOURCE, EventSource);
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getDocumentId() {
		return attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	public void setDocumentId(String[] documentId) {
		attributes.put(AttributeKey.DOCUMENT_ID, documentId.toString());
	}

	public String getDocumentNames() {
		return attributes.get(AttributeKey.DOCUMENT_NAME).toString();
	}

	public void setDocumentNames(String[] documentName) {
		attributes.put(AttributeKey.DOCUMENT_NAME, documentName.toString());
	}

	public String getProjectId(){
		return this.attributes.get(AttributeKey.PROJECT_ID).toString();
	}

	public void setProjectId(String projectId){
		this.attributes.put(AttributeKey.PROJECT_ID, projectId);
	}
}
