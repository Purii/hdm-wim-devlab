package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Document highly relevant event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#documenthighlyrelevantEvent">DocumentHighlyRelevantEvent</a>
 */
public class DocumentHighlyRelevantEvent extends IEvent {

	/**
	 * Instantiates a new Document highly relevant event.
	 */
	public DocumentHighlyRelevantEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DOCUMENT_HIGHLY_RELEVANT);
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


}
