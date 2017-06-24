package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class UserContextEvent extends IEvent {
	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

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
