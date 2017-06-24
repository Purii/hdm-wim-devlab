package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class UserContextEvent implements IEvent{
	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	public UserContextEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_CONTEXT);
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

	public String getUserIds(){
		return this.attributes.get(AttributeKey.USER_IDS).toString();
	}

	public void setUserIds(String userIds){
		this.attributes.put(AttributeKey.USER_IDS, userIds);
	}

	public String getUserNames(){
		return this.attributes.get(AttributeKey.USER_NAMES).toString();
	}

	public void setUserNames(String userNames){
		this.attributes.put(AttributeKey.USER_NAMES, userNames);
	}

	public String getContext(){
		return this.attributes.get(AttributeKey.CONTEXT).toString();
	}

	public void setContext(String context){
		this.attributes.put(AttributeKey.CONTEXT, context);
	}



}
