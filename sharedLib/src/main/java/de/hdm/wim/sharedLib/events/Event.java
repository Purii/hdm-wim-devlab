package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventSource;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import de.hdm.wim.sharedLib.helper.Helper;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Event extends IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	public Event( String data, Hashtable attributes ){
		this.data 		= data;
		this.attributes = attributes;
	}

	public Event( String data ){
		this.data 		= data;
	}

	public Event(){}

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
}
