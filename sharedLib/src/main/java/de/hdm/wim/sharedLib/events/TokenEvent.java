package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Nils
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#tokenevent">TokenEvent</a>
 */
public class TokenEvent extends IEvent {
	public TokenEvent() {
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.TOKEN);
	}

	public String getUserId(){
		return attributes.get(AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID, userId);
	}

	public String getSessionId(){
		return attributes.get(AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId) {
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	public String getTokens() {
		return attributes.get(AttributeKey.TOKENS).toString();
	}

	public void setTokens(String tokens) {
		this.attributes.put(AttributeKey.TOKENS, tokens.toString());
	}

	public String getTimestamp() {
		return attributes.get(AttributeKey.TIMESTAMP).toString();
	}

	public void setTimestamp(String timestamp) {
		this.attributes.put(AttributeKey.TIMESTAMP, timestamp.toString());
	}

	public String getContexts() {
		return attributes.get(AttributeKey.CONTEXTS).toString();
	}

	public void setContexts(String contexts) {
		this.attributes.put(AttributeKey.CONTEXTS, contexts.toString());
	}
}
