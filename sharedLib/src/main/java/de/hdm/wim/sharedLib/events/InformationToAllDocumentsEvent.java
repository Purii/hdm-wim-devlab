package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class InformationToAllDocumentsEvent implements IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	public InformationToAllDocumentsEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.INFO_TOALL_DOCUMENTS);
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

	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	public String getDocumentId(){
		return this.attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	public void setDocumentId(String documentId){
		this.attributes.put(AttributeKey.DOCUMENT_ID, documentId);
	}

	public String getDocumentName(){
		return this.attributes.get(AttributeKey.DOCUMENT_NAME).toString();
	}

	public void setDocumentName(String documentName){
		this.attributes.put(AttributeKey.DOCUMENT_NAME, documentName);
	}

	public String getDocumentUrl(){
		return this.attributes.get(AttributeKey.DOCUMENT_URL).toString();
	}

	public void setDocumentUrl(String documentUrl) {
		this.attributes.put(AttributeKey.DOCUMENT_URL, documentUrl);
	}

	public String getDocumentType(){
		return this.attributes.get(AttributeKey.DOCUMENT_TYPE).toString();
	}

	public void setDocumentType(String documentType){this.attributes.put(AttributeKey.DOCUMENT_TYPE, documentType);}

	public String getDocumentFolder(){
		return this.attributes.get(AttributeKey.DOCUMENT_FOLDER).toString();
	}

	public void setDocumentFolder(String documentFolder){this.attributes.put(AttributeKey.DOCUMENT_FOLDER, documentFolder);}

	public String getDocumentPrio(){
		return this.attributes.get(AttributeKey.DOCUMENT_PRIO).toString();
	}

	public void setDocumentPrio(String documentPrio){this.attributes.put(AttributeKey.DOCUMENT_PRIO, documentPrio);}




}
