package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class SessionStartEvent extends IEvent {
	public SessionStartEvent(){
		this.attributes.put(Constants.PubSub.AttributeKey.EVENT_TYPE, Constants.PubSub.EventType.SESSION_START);
	}

	public String getSessionId(){
		return this.attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.TOKEN_ID, sessionId);
	}

	public String getUserId(){
		return this.attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}
}
