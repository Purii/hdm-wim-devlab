package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#userinactiveevent">UserInactiveEvent</a>
 */
public class UserInactiveEvent extends IEvent {
	public UserInactiveEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_INACTIVE);
	}

	public String getUserId(){
		return this.attributes.get(AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID , userId);
	}
}
