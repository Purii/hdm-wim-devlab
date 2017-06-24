package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#documentcallevent">DocumentCallEvent</a>
 */
public class DocumentCallEvent extends IEvent {
	public DocumentCallEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DOCUMENT_CALL);
	}

	public String getUserId(){
		return attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}

	public String getDocumentName(){
		return attributes.get(Constants.PubSub.AttributeKey.DOCUMENT_NAME.toString());
	}

	public void setDocumentName(String documentName){
		this.attributes.put(Constants.PubSub.AttributeKey.DOCUMENT_NAME, documentName);
	}
}
