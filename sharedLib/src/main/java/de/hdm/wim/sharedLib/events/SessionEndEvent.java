package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Session end event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#sessionendevent">SessionEndEvent</a>
 */
public class SessionEndEvent extends IEvent {

	/**
	 * Instantiates a new Session end event.
	 */
	public SessionEndEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.SESSION_END);
	}

	/**
	 * Get session id string.
	 *
	 * @return the string
	 */
	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Set session id.
	 *
	 * @param sessionId the session id
	 */
	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}



}
