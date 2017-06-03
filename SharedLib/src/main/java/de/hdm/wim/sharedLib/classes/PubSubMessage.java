package de.hdm.wim.sharedLib.classes;

import de.hdm.wim.sharedLib.Constants.AttributeKey;
import de.hdm.wim.sharedLib.Constants.EventSource;
import de.hdm.wim.sharedLib.Constants.EventType;
import de.hdm.wim.sharedLib.helper.Helper;
import java.time.LocalDateTime;
import java.util.Hashtable;

/**
 * Created by ben on 2/06/2017.
 * this class is only for testing purposes!
 */
public class PubSubMessage {

	private String data;
	private String messageId;
	private LocalDateTime publishTime;
	private Hashtable attributes = new Hashtable();

	public PubSubMessage(String data, String messageId, LocalDateTime publishTime, Hashtable attributes){
		this.data 			= data;
		this.messageId 		= messageId;
		this.publishTime 	= publishTime;
		this.attributes 	= attributes;
	}

	public String getData() {
		return data;
	}

	public String getMessageId() {
		return messageId;
	}

	public LocalDateTime getPublishTime() {
		return publishTime;
	}

	public Hashtable getAttributes() {
		return attributes;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setPublishTime(LocalDateTime publishTime) {
		this.publishTime = publishTime;
	}

	public void setAttributes(Hashtable attributes) {
		this.attributes = attributes;
	}

	public static PubSubMessage generate(String data, String id){

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

		return new PubSubMessage(
			data,
			id,
			LocalDateTime.now(),
			attributes
		);
	}
}
