package de.hdm.wim.sharedLib.helper;

import de.hdm.wim.sharedLib.Constants.AttributeKey;
import de.hdm.wim.sharedLib.Constants.EventSource;
import de.hdm.wim.sharedLib.Constants.EventType;
import de.hdm.wim.sharedLib.classes.PubSubMessage;
import java.time.LocalDateTime;
import java.util.Hashtable;

/**
 * Created by ben on 2/06/2017.
 */
class MessageGenerator {

	public PubSubMessage GenerateMessage(String data, String id){

		Helper helper = new Helper();
		PubSubMessage message = new PubSubMessage();

		message.setData(data);
		message.setMessageId(id);
		message.setPublishTime(LocalDateTime.now());

		Hashtable attributes = new Hashtable();

		attributes.put(
			AttributeKey.eventSource,
			helper.getRandomStringFromList(EventSource.list)
		);

		attributes.put(
			AttributeKey.eventType,
			helper.getRandomStringFromList(EventType.list)
		);

		message.setAttributes(attributes);

		return message;
	}
}
