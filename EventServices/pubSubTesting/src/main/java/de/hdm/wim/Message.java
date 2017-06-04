package de.hdm.wim; /**
 * Created by ben on 4/06/2017.
 */
/**
 * A message captures information from the Pubsub message received over the push endpoint and is
 * persisted in storage.
 */
public class Message {
	private String messageId;
	private String publishTime;
	private String data;

	public Message(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
