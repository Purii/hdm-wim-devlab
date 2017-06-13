package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventSource;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import de.hdm.wim.sharedLib.testing.Helper;
import java.util.Hashtable;

/**
 * Created by ben on 3/06/2017.
 *
 * A message captures information from the Pubsub message received over the push endpoint and is
 * persisted in storage.
 */
public class Event {

	private String data;
	private String id;
	private String publishTime;
	private Hashtable attributes = new Hashtable();

	/**
	 * Instantiates a new Message.
	 *
	 * @param data the data
	 * @param attributes the attributes
	 */
	public Event( String data, Hashtable attributes ){
		this.data 		= data;
		this.attributes = attributes;
	}

	public Event( String data ){
		this.data 		= data;
	}

	/**
	 * Instantiates a new Message.
	 */
	public Event(){}

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
	 * Gets attributes.
	 *
	 * @return the attributes
	 */
	public Hashtable getAttributes() {
		return attributes;
	}

	/**
	 * Sets attributes.
	 *
	 * @param attributes the attributes
	 */
	public void setAttributes(Hashtable attributes) {
		this.attributes = attributes;
	}

	/**
	 * Generate message.
	 *
	 * @param data the data
	 * @return the message
	 */
	public static Event generate(String data){

		Helper helper 			= new Helper();
		Hashtable attributes 	= new Hashtable();

		attributes.put(
			AttributeKey.EVENT_SOURCE,
			helper.getRandomStringFromList(EventSource.list)
		);

		attributes.put(
			AttributeKey.EVENT_TYPE,
			helper.getRandomStringFromList(EventType.list)
		);

		return new Event(data, attributes);
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
		this.id = id;
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
}
