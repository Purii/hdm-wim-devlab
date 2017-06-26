package de.hdm.wim.sharedLib.pubsub.classes;

import com.google.common.io.BaseEncoding;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventSource;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import de.hdm.wim.sharedLib.helper.Helper;
import java.time.LocalDateTime;
import java.util.Hashtable;

/**
 * The type Pub sub message.
 * this  class is only for testing purposes!
 *
 * @author Benedikt Benz
 * @createdOn 02.06.2017
 */
public class PubSubMessage {

	private String data;
	private String messageId;
	private LocalDateTime publishTime;
	private Hashtable attributes = new Hashtable();

	/**
	 * Instantiates a new Pub sub message.
	 *
	 * @param data the data
	 * @param messageId the message id
	 * @param publishTime the publish time
	 * @param attributes the attributes
	 */
	public PubSubMessage(String data, String messageId, LocalDateTime publishTime, Hashtable attributes){
		this.setData(data);
		this.messageId 		= messageId;
		this.publishTime 	= publishTime;
		this.attributes 	= attributes;
	}

	/**
	 * Generate a pubSub message.
	 *
	 * @param data the data
	 * @param id the id
	 * @return PubSubMessage random
	 */
	public static PubSubMessage getRandom(String data, String id) {

		Helper helper = new Helper();
		Hashtable attributes = new Hashtable();

		attributes.put(
			AttributeKey.EVENT_SOURCE,
			helper.getRandomStringFromList(EventSource.list)
		);

		attributes.put(
			AttributeKey.EVENT_TYPE,
			helper.getRandomStringFromList(EventType.list)
		);

		return new PubSubMessage(data, id, LocalDateTime.now(), attributes);
	}

	/**
	 * Gets data.
	 *
	 * @return the data
	 */
	public String getData() {
		return new String(BaseEncoding.base64().decode(this.data));
	}

	/**
	 * Sets data.
	 *
	 * @param data the data
	 */
	public void setData(String data) {
		this.data = BaseEncoding.base64Url().encode(data.getBytes());
	}

	/**
	 * Gets message id.
	 *
	 * @return the message id
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * Sets message id.
	 *
	 * @param messageId the message id
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * Gets publish time.
	 *
	 * @return the publish time
	 */
	public LocalDateTime getPublishTime() {
		return publishTime;
	}

	/**
	 * Sets publish time.
	 *
	 * @param publishTime the publish time
	 */
	public void setPublishTime(LocalDateTime publishTime) {
		this.publishTime = publishTime;
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
}
