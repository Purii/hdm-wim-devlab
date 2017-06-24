package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventSource;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import de.hdm.wim.sharedLib.helper.Helper;
import java.util.Hashtable;

/**
 * The type Event.
 *
 * @author ben
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#event">Event</a>
 */
public class Event extends IEvent {

	/**
	 * Instantiates a new Event.
	 *
	 * @param data the data
	 * @param attributes the attributes
	 */
	public Event( String data, Hashtable attributes ){
		this.data 		= data;
		this.attributes = attributes;
	}

	/**
	 * Instantiates a new Event.
	 *
	 * @param id the id
	 */
	public Event(String id) {
		this.id = id;
	}

	/**
	 * Instantiates a new Event.
	 */
	public Event() {
	}

	/**
	 * Generate event.
	 *
	 * @param data the data
	 * @return the event
	 */
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
