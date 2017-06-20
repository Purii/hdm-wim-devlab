package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ben on 18/06/2017.
 *
 * Example:
 * 	LearnEvent learnEvent = new LearnEvent();
 *	learnEvent.setData("test");
 *	learnEvent.setDocumentId("");
 *	learnEvent.setEventSource(EventSource.MACHINE_LEARNING);
 *	learnEvent.setProjectId("test project id");
 *	learnEvent.setUserclick(false);
 *	learnEvent.setUserId("test user id");
 *
 */
public class LearnEvent implements IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	// auto set EventType on init
	public LearnEvent() {
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.LEARN);
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

	public String getDocumentId(){
		return attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	public void setDocumentId(String DocumentId){
		this.attributes.put(AttributeKey.DOCUMENT_ID, DocumentId);
	}

	public String getProjectId(){
		return attributes.get(AttributeKey.PROJECT_ID).toString();
	}

	public void setProjectId(String ProjectId){
		this.attributes.put(AttributeKey.PROJECT_ID, ProjectId);
	}

	public boolean getUserclick(){
		String val = attributes.get(AttributeKey.USERCLICK).toString();
		return Boolean.parseBoolean(val);
	}

	public void setUserclick(boolean Userclick){
		this.attributes.put(AttributeKey.USERCLICK, Boolean.toString(Userclick));
	}

	public String getEventType(){
		return attributes.get(AttributeKey.EVENT_TYPE).toString();
	}

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
