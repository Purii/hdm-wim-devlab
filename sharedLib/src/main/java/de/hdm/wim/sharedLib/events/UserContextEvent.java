package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#usercontextevent">UserContextEvent</a>
 */
public class UserContextEvent extends IEvent {
	public UserContextEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_CONTEXT);
	}

	public String getUserIds(){
		return this.attributes.get(AttributeKey.USER_IDS).toString();
	}

	public void setUserIds(String userIds){
		this.attributes.put(AttributeKey.USER_IDS, userIds);
	}

	public String getUserNames(){
		return this.attributes.get(AttributeKey.USER_NAMES).toString();
	}

	public void setUserNames(String userNames){
		this.attributes.put(AttributeKey.USER_NAMES, userNames);
	}

	public String getContext(){
		return this.attributes.get(AttributeKey.CONTEXT).toString();
	}

	public void setContext(String context){
		this.attributes.put(AttributeKey.CONTEXT, context);
	}



}
