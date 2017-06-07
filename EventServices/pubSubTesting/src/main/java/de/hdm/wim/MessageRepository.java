package de.hdm.wim;

import java.util.List;

/**
 * Created by ben on 4/06/2017.
 */
public interface MessageRepository {

	/** Save message to persistent storage. */
	void save(Message message);

	/**
	 * Retrieve most recent stored messages.
	 * @param limit number of messages
	 * @return list of messages
	 */
	List<Message> retrieve(int limit);
}