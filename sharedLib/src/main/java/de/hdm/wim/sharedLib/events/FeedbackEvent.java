package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Feedback event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#feedbackevent">FeedbackEvent</a>
 */
public class FeedbackEvent extends IEvent {

	/**
	 * Instantiates a new Feedback event.
	 */
	public FeedbackEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.FEEDBACK);
	}

	/**
	 * Get user id string.
	 *
	 * @return the string
	 */
	public String getUserId(){
		return this.attributes.get(AttributeKey.USER_ID).toString();
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
	 * Get document name string.
	 *
	 * @return the string
	 */
	public String getDocumentName(){
		return this.attributes.get(AttributeKey.DOCUMENT_NAME).toString();
	}

	/**
	 * Set document name.
	 *
	 * @param documentName the document name
	 */
	public void setDocumentName(String documentName){
		this.attributes.put(AttributeKey.DOCUMENT_NAME, documentName);
	}
}
