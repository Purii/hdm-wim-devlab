package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

/**
 * The type Session start event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#sessionstartevent">SessionStartEvent</a>
 */
public class SessionStartEvent extends IEvent {

	/**
	 * Instantiates a new Session start event.
	 */
	public SessionStartEvent(){
		this.attributes.put(Constants.PubSub.AttributeKey.EVENT_TYPE, Constants.PubSub.EventType.SESSION_START);
	}

	/**
	 * Get session id string.
	 *
	 * @return the string
	 */
	public String getSessionId(){
		return this.attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Set session id.
	 *
	 * @param sessionId the session id
	 */
	public void setSessionId(String sessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.TOKEN_ID, sessionId);
	}

	/**
	 * Get user id string.
	 *
	 * @return the string
	 */
	public String getUserId(){
		return this.attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	/**
	 * Set user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}
}
