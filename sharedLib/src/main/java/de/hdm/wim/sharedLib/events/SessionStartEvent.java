package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#sessionstartevent">SessionStartEvent</a>
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
