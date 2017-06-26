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
 * The type Event.
 *
 * @author Benedikt Benz
 */
public abstract class IEvent {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	/**
	 * The Data.
	 */
	protected String data = "";
	/**
	 * The Id.
	 */
	protected String id = "";
	/**
	 * The Publish time.
	 */
	protected String publishTime = "";
	/**
	 * The Handler id.
	 */
	protected String handlerId = "";

	/**
	 * The Attributes.
	 */
	protected Map<String, String> attributes = new HashMap<String, String>();

	/**
	 * Gets data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets data.
	 *
	 * @param data the data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(String id) {
		this.id= id;
	}

	/**
	 * Gets publish time.
	 *
	 * @return the publish time
	 */
	public String getPublishTime() {
		return publishTime;
	}

	/**
	 * Sets publish time.
	 *
	 * @param publishTime the publish time
	 */
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * Gets event type.
	 *
	 * @return the event type
	 */
	public String getEventType() {
		return attributes.get(AttributeKey.EVENT_TYPE).toString();
	}

	/*
	public void setEventType(String EventType){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType);
	}
	*/

	/**
	 * Gets event source.
	 *
	 * @return the event source
	 */
	public String getEventSource() {
		return attributes.get(AttributeKey.EVENT_SOURCE).toString();
	}

	/**
	 * Sets event source.
	 *
	 * @param EventSource the event source
	 */
	public void setEventSource(String EventSource) {
		this.attributes.put(AttributeKey.EVENT_SOURCE, EventSource);
	}

	/**
	 * Gets attributes.
	 *
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	/**
	 * Sets attributes.
	 *
	 * @param attributes the attributes
	 */
	public void setAttributes(String attributes) {
		this.attributes = new Helper().convertJsonToMap(attributes);
	}

	/**
	 * Sets attributes.
	 *
	 * @param attributes the attributes
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Gets attributes as string.
	 *
	 * @return the attributes as string
	 */
	public String getAttributesAsString() {
		return gson.toJson(this.attributes);
	}

	/**
	 * Gets handler id.
	 *
	 * @return the handler id
	 */
	public String getHandlerId() {
		return handlerId;
	}

	/**
	 * Sets handler id.
	 *
	 * @param handlerId the handler id
	 */
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}

	/**
	 *
	 * @return formatted & valid json string representation of the IEvent
	 */
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
