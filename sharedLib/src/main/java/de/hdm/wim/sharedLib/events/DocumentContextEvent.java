package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 19.06.17.
 */
public class DocumentContextEvent implements IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	public DocumentContextEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DOCUMENT_CONTEXT);
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

	public String getDocumentIds() {
		return attributes.get(AttributeKey.DOCUMENT_IDS).toString();
	}

	public void setDocumentIds(String documentIds) {
		attributes.put(AttributeKey.DOCUMENT_IDS, documentIds.toString());
	}

	public String getDocumentNames() {
		return attributes.get(AttributeKey.DOCUMENT_NAMES).toString();
	}

	public void setDocumentNames(String documentNames) {
		attributes.put(AttributeKey.DOCUMENT_NAMES, documentNames.toString());
	}

	public String getContext(){
		return this.attributes.get(AttributeKey.CONTEXT).toString();
	}

	public void setContext(String context){
		this.attributes.put(AttributeKey.CONTEXT, context);
	}
}
