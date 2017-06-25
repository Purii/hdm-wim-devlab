package de.hdm.wim.sharedLib.events;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.helper.Helper;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben
 */
public abstract class IEvent {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	protected String data = "";
	protected String id = "";
	protected String publishTime = "";
	protected String handlerId = "";

	protected Map<String, String> attributes = new HashMap<String, String>();

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

	public void setAttributes(String attributes) {
		this.attributes = new Helper().convertJsonToMap(attributes);
	}

	public String getAttributesAsString() {
		return gson.toJson(this.attributes);
	}

	public String getHandlerId() {
		return handlerId;
	}

	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}

	@Override
	public String toString() {

		Type myType = new TypeToken<IEvent>() {
		}.getType();

		Event evt = new Event();
		evt.setAttributes(this.attributes);
		evt.setData(this.data);
		evt.setId(this.id);
		evt.setPublishTime(this.publishTime);

		String json = gson.toJson(evt, myType);
		return json;
	}
}
