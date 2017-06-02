package de.hdm.wim.sharedLib.classes;

import java.time.LocalDateTime;
import java.util.Hashtable;

/**
 * Created by ben on 2/06/2017.
 */
public class PubSubMessage {

	private String data;
	private String messageId;
	private LocalDateTime publishTime;

	private Hashtable attributes = new Hashtable();

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
}
