package de.hdm.wim.sharedLib.classes;

import de.hdm.wim.sharedLib.Constants.AttributeKey;
import de.hdm.wim.sharedLib.Constants.EventSource;
import de.hdm.wim.sharedLib.Constants.EventType;
import de.hdm.wim.sharedLib.Constants.Topic;
import de.hdm.wim.sharedLib.helper.Helper;
import java.util.Hashtable;

/**
 * Created by ben on 3/06/2017.
 *
 * A message captures information from the Pubsub message received over the push endpoint and is
 * persisted in storage.
 */
public class Message {

	private String data;
	private String topic;
	private String id;
	private String publishTime;
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

	public Message( String data, String topic ){
		this.data 		= data;
		this.topic 		= topic;
	}

	/**
	 * Instantiates a new Message.
	 */
	public Message(){}

	/**
	 * Instantiates a new Message.
	 *
	 * @param messageId the message id
	 */
	public Message(String messageId) {
		this.id = messageId;
	}

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
	 * @param data the data
	 * @return the message
	 */
	public static Message generate(String data){

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

		return new Message(
			data,
			helper.getRandomStringFromList(Topic.list),
			attributes
		);
	}

	/**
	 * Generate message.
	 *
	 * @param data the data
	 * @param topic the topic
	 * @return the message
	 */
	public static Message generate(String data, String topic){

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

		return new Message(data, topic, attributes);
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
