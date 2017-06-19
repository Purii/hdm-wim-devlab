package de.hdm.wim.pubSubTesting;

import de.hdm.wim.sharedLib.events.Event;
import java.util.List;

/**
 * Created by ben on 4/06/2017.
 */
public interface MessageRepository {

	/** Save message to persistent storage. */
	void save(Event event);

	/**
	 * Retrieve most recent stored messages.
	 * @param limit number of messages
	 * @return ALL_TOPICS of messages
	 */
	List<Event> retrieve(int limit);
}