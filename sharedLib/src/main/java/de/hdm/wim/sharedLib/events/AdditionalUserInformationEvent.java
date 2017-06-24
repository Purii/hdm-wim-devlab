package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 20.06.2017.
 */
public class AdditionalUserInformationEvent extends IEvent {

	public AdditionalUserInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ADDITIONAL_USER_INFO);
	}

	public String getUserId(){
		return attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}
}
