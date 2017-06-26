package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type User login event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#userloginevent">UserLoginEvent</a>
 */
public class UserLoginEvent extends IEvent {

	/**
	 * Instantiates a new User login event.
	 */
	public UserLoginEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_LOGIN);
	}

	/**
	 * Get user id string.
	 *
	 * @return the string
	 */
	public String getUserId(){
		return this.attributes.get(AttributeKey.USER_ID).toString();
	}

	/**
	 * Set user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID, userId);
	}
}
