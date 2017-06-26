package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Document call event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#documentcallevent">DocumentCallEvent</a>
 */
public class DocumentCallEvent extends IEvent {

	/**
	 * Instantiates a new Document call event.
	 */
	public DocumentCallEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DOCUMENT_CALL);
	}

	/**
	 * Get user id string.
	 *
	 * @return the string
	 */
	public String getUserId(){
		return attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	/**
	 * Set user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}

	/**
	 * Get document name string.
	 *
	 * @return the string
	 */
	public String getDocumentName(){
		return attributes.get(Constants.PubSub.AttributeKey.DOCUMENT_NAME.toString());
	}

	/**
	 * Set document name.
	 *
	 * @param documentName the document name
	 */
	public void setDocumentName(String documentName){
		this.attributes.put(Constants.PubSub.AttributeKey.DOCUMENT_NAME, documentName);
	}
}
