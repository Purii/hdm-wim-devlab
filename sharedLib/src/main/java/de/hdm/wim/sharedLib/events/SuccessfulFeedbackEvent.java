package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Successful feedback event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#successfulfeedbackevent">SuccessfulFeedbackEvent</a>
 */
public class SuccessfulFeedbackEvent extends IEvent {

	/**
	 * Instantiates a new Successful feedback event.
	 */
	public SuccessfulFeedbackEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.SUCCESSFUL_FEEDBACK);
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
	 * Get document id string.
	 *
	 * @return the string
	 */
	public String getDocumentId(){
		return this.attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	/**
	 * Set document id.
	 *
	 * @param documentId the document id
	 */
	public void setDocumentId(String documentId){
		this.attributes.put(AttributeKey.DOCUMENT_ID, documentId);
	}

	/**
	 * Get document affiliation string.
	 *
	 * @return the string
	 */
	public String getDocumentAffiliation(){
		return this.attributes.get(AttributeKey.DOCUMENT_AFFILIATION).toString();
	}

	/**
	 * Set document affiliation.
	 *
	 * @param documentAffiliation the document affiliation
	 */
	public void setDocumentAffiliation(String documentAffiliation){
		this.attributes.put(AttributeKey.DOCUMENT_AFFILIATION, documentAffiliation);
	}

}
