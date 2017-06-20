package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 20.06.2017.
 */
public class AllDepartmentsEvent implements IEvent{

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	// TODO: Update Event Type!
	public AllDepartmentsEvent(){
		this.attributes.put(Constants.PubSub.AttributeKey.EVENT_TYPE, Constants.PubSub.EventType.ALL_DEPARTMENTS);
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

	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	public String getDepartmentNames(){
		return attributes.get(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES).toString();
	}

	public void setDepartmentNames(String departmentNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES, departmentNames);
	}
}
