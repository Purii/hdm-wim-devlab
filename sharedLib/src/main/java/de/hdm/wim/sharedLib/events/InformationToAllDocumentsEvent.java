package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#informationtoalldocumentsevent">InformationToAllDocumentsEvent</a>
 */
public class InformationToAllDocumentsEvent extends IEvent {
	public InformationToAllDocumentsEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.INFO_TOALL_DOCUMENTS);
	}

	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	public String getDocumentId(){
		return this.attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	public void setDocumentId(String documentId){
		this.attributes.put(AttributeKey.DOCUMENT_ID, documentId);
	}

	public String getDocumentName(){
		return this.attributes.get(AttributeKey.DOCUMENT_NAME).toString();
	}

	public void setDocumentName(String documentName){
		this.attributes.put(AttributeKey.DOCUMENT_NAME, documentName);
	}

	public String getDocumentUrl(){
		return this.attributes.get(AttributeKey.DOCUMENT_URL).toString();
	}

	public void setDocumentUrl(String documentUrl) {
		this.attributes.put(AttributeKey.DOCUMENT_URL, documentUrl);
	}

	public String getDocumentType(){
		return this.attributes.get(AttributeKey.DOCUMENT_TYPE).toString();
	}

	public void setDocumentType(String documentType){this.attributes.put(AttributeKey.DOCUMENT_TYPE, documentType);}

	public String getDocumentFolder(){
		return this.attributes.get(AttributeKey.DOCUMENT_FOLDER).toString();
	}

	public void setDocumentFolder(String documentFolder){this.attributes.put(AttributeKey.DOCUMENT_FOLDER, documentFolder);}

	public String getDocumentPrio(){
		return this.attributes.get(AttributeKey.DOCUMENT_PRIO).toString();
	}

	public void setDocumentPrio(String documentPrio){this.attributes.put(AttributeKey.DOCUMENT_PRIO, documentPrio);}




}
