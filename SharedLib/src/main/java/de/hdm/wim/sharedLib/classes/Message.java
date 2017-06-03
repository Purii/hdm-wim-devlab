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

	public Message( String data, String topic, Hashtable attributes ){
		this.data 		= data;
		this.topic 		= topic;
		this.attributes = attributes;
	}

	public Message(){}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Hashtable getAttributes() {
		return attributes;
	}

	public void setAttributes(Hashtable attributes) {
		this.attributes = attributes;
	}

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
