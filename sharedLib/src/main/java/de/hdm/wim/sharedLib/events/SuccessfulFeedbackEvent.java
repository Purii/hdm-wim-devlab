package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#successfulfeedbackevent">SuccessfulFeedbackEvent</a>
 */
public class SuccessfulFeedbackEvent extends IEvent {
	public SuccessfulFeedbackEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.SUCCESSFUL_FEEDBACK);
	}

	public String getUserId(){
		return this.attributes.get(AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID, userId);
	}

	public String getDocumentId(){
		return this.attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	public void setDocumentId(String documentId){
		this.attributes.put(AttributeKey.DOCUMENT_ID, documentId);
	}

	public String getDocumentAffiliation(){
		return this.attributes.get(AttributeKey.DOCUMENT_AFFILIATION).toString();
	}

	public void setDocumentAffiliation(String documentAffiliation){
		this.attributes.put(AttributeKey.DOCUMENT_AFFILIATION, documentAffiliation);
	}

}
