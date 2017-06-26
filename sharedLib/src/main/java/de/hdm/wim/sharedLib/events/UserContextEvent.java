package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type User context event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#usercontextevent">UserContextEvent</a>
 */
public class UserContextEvent extends IEvent {

	/**
	 * Instantiates a new User context event.
	 */
	public UserContextEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_CONTEXT);
	}

	/**
	 * Get user ids string.
	 *
	 * @return the string
	 */
	public String getUserIds(){
		return this.attributes.get(AttributeKey.USER_IDS).toString();
	}

	/**
	 * Set user ids.
	 *
	 * @param userIds the user ids
	 */
	public void setUserIds(String userIds){
		this.attributes.put(AttributeKey.USER_IDS, userIds);
	}

	/**
	 * Get user names string.
	 *
	 * @return the string
	 */
	public String getUserNames(){
		return this.attributes.get(AttributeKey.USER_NAMES).toString();
	}

	/**
	 * Set user names.
	 *
	 * @param userNames the user names
	 */
	public void setUserNames(String userNames){
		this.attributes.put(AttributeKey.USER_NAMES, userNames);
	}

	/**
	 * Get context string.
	 *
	 * @return the string
	 */
	public String getContext(){
		return this.attributes.get(AttributeKey.CONTEXT).toString();
	}

	/**
	 * Set context.
	 *
	 * @param context the context
	 */
	public void setContext(String context){
		this.attributes.put(AttributeKey.CONTEXT, context);
	}



}
