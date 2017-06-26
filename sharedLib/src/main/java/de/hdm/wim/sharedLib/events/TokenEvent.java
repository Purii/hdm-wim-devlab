package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Token event.
 *
 * @author Nils Bachmann
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#tokenevent">TokenEvent</a>
 */
public class TokenEvent extends IEvent {

	/**
	 * Instantiates a new Token event.
	 */
	public TokenEvent() {
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.TOKEN);
	}

	/**
	 * Get user id string.
	 *
	 * @return the string
	 */
	public String getUserId(){
		return attributes.get(AttributeKey.USER_ID).toString();
	}

	/**
	 * Set user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID, userId);
	}

	/**
	 * Get session id string.
	 *
	 * @return the string
	 */
	public String getSessionId(){
		return attributes.get(AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Sets session id.
	 *
	 * @param sessionId the session id
	 */
	public void setSessionId(String sessionId) {
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	/**
	 * Gets tokens.
	 *
	 * @return the tokens
	 */
	public String getTokens() {
		return attributes.get(AttributeKey.TOKENS).toString();
	}

	/**
	 * Sets tokens.
	 *
	 * @param tokens the tokens
	 */
	public void setTokens(String tokens) {
		this.attributes.put(AttributeKey.TOKENS, tokens.toString());
	}

	/**
	 * Gets timestamp.
	 *
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return attributes.get(AttributeKey.TIMESTAMP).toString();
	}

	/**
	 * Sets timestamp.
	 *
	 * @param timestamp the timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.attributes.put(AttributeKey.TIMESTAMP, timestamp.toString());
	}

	/**
	 * Gets contexts.
	 *
	 * @return the contexts
	 */
	public String getContexts() {
		return attributes.get(AttributeKey.CONTEXTS).toString();
	}

	/**
	 * Sets contexts.
	 *
	 * @param contexts the contexts
	 */
	public void setContexts(String contexts) {
		this.attributes.put(AttributeKey.CONTEXTS, contexts.toString());
	}
}
