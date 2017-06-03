package de.hdm.wim.sharedLib.classes;

import de.hdm.wim.sharedLib.Constants.AttributeKey;
import de.hdm.wim.sharedLib.Constants.EventSource;
import de.hdm.wim.sharedLib.Constants.EventType;
import de.hdm.wim.sharedLib.Constants.Topic;
import de.hdm.wim.sharedLib.helper.Helper;
import java.util.Hashtable;

/**
 * Created by ben on 3/06/2017.
 */
public class Message {

	private String data;
	private String topic;
	private Hashtable attributes = new Hashtable();

	/**
	 * Instantiates a new Message.
	 *
	 * @param data the data
	 * @param topic the topic
	 * @param attributes the attributes
	 */
	public Message( String data, String topic, Hashtable attributes ){
		this.data 		= data;
		this.topic 		= topic;
		this.attributes = attributes;
	}

	/**
	 * Instantiates a new Message.
	 */
	public Message(){}

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
	 * Gets topic.
	 *
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Sets topic.
	 *
	 * @param topic the topic
	 */
	public void setTopic(String topic) {
		this.topic = topic;
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
	 * @return the message
	 */
	public static Message generate(){

		Helper helper 			= new Helper();
		Hashtable attributes 	= new Hashtable();

		attributes.put(
			AttributeKey.eventSource,
			helper.getRandomStringFromList(EventSource.list)
		);

		attributes.put(
			AttributeKey.eventType,
			helper.getRandomStringFromList(EventType.list)
		);

		return new Message(
			"test",
			helper.getRandomStringFromList(Topic.list),
			attributes
		);
	}
}
