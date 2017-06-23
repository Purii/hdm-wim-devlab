package de.hdm.wim.sharedLib.helper;

import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.pubsub.v1.PubsubMessage;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.events.IEvent;

import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * Created by ben on 2/06/2017.
 */
public class Helper {

	private static final Logger LOGGER 	= Logger.getLogger(Helper.class);
	private final Gson gson 			= new Gson();
	private final JsonParser jsonParser = new JsonParser();

	/**
	 * Instantiates a new Helper.
	 */
	public Helper() {
	}

	/**
	 * Get random string from list of String.
	 *
	 * @param list the list
	 * @return the string
	 */
	public String getRandomStringFromList(List<String> list) {
		Random random = new Random();
		int index = random.nextInt(list.size());
		return list.get(index);
	}

	/**
	 * Encode base 64 string.
	 *
	 * @param data the data
	 * @return the string
	 * @throws Exception the exception
	 */
	public String encodeBase64(String data) throws Exception {
		byte[] byteString = data.getBytes(data);
		return new String(BaseEncoding.base64().encode(byteString));
	}

	/**
	 * Decode base 64 string.
	 *
	 * @param data the data
	 * @return the string
	 */
	public String decodeBase64(String data) {
		return new String(BaseEncoding.base64().decode(data));
	}

	/**
	 * Event converter to convert a PubsubMessage to an Event based on eventtype
	 *
	 * @param message the PubsubMessage
	 * @return event event
	 */
	//TODO: update this
	public IEvent convertPubsubMessageToIEvent(PubsubMessage message) {
		IEvent event = new Event();

		// get message content & transform
		Map<String, String> attributes = message.getAttributesMap();
		String data = decodeBase64(message.getData().toString());
		String messageId = message.getMessageId();
		String publishTime = message.getPublishTime().toString();

		// get eventType from attributes
		String eventType = attributes.get(AttributeKey.EVENT_TYPE);



		/*
		// init event based on type
		switch (eventType) {
			case EventType.LEARN:
				event = new LearnEvent();
				type  = LearnEvent.class;
				break;
			case EventType.TOKEN:
				event = new TokenEvent();
				type  = TokenEvent.class;
				break;
			default:
				LOGGER.warn("Invalid EventType");
		}
		 */

		// fill new message object
		event.setData(data);
		event.setAttributes(attributes);
		event.setId(messageId);
		event.setPublishTime(publishTime);

		//return event;
		return event;
	}

	/**
	 * Get event from json string.
	 *
	 * @param json the json string of the event
	 * @return IEvent
	 */
	public IEvent GetEventFromJson(String json) {

		LOGGER.info("aa" + json);

		JsonElement jsonRoot 	= jsonParser.parse(json);
		String eventStr 		= jsonRoot.getAsJsonObject().get("message").toString();
		Event event 			= gson.fromJson(eventStr, Event.class);

		// decode from base64
		String decoded = decodeBase64(event.getData());
		event.setData(decoded);
		return event;
	}
}
