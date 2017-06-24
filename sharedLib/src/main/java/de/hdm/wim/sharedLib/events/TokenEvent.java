package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nilsb on 19.06.2017.
 */
public class TokenEvent extends IEvent {

	private Map<String, String> attributes = new HashMap<String, String>();

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
