package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Information to all documents event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#informationtoalldocumentsevent">InformationToAllDocumentsEvent</a>
 */
public class InformationToAllDocumentsEvent extends IEvent {

	/**
	 * Instantiates a new Information to all documents event.
	 */
	public InformationToAllDocumentsEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.INFO_TOALL_DOCUMENTS);
	}

	/**
	 * Get session id string.
	 *
	 * @return the string
	 */
	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Set session id.
	 *
	 * @param sessionId the session id
	 */
	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
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

	/**
	 * Get document url string.
	 *
	 * @return the string
	 */
	public String getDocumentUrl(){
		return this.attributes.get(AttributeKey.DOCUMENT_URL).toString();
	}

	/**
	 * Sets document url.
	 *
	 * @param documentUrl the document url
	 */
	public void setDocumentUrl(String documentUrl) {
		this.attributes.put(AttributeKey.DOCUMENT_URL, documentUrl);
	}

	/**
	 * Get document type string.
	 *
	 * @return the string
	 */
	public String getDocumentType(){
		return this.attributes.get(AttributeKey.DOCUMENT_TYPE).toString();
	}

	/**
	 * Set document type.
	 *
	 * @param documentType the document type
	 */
	public void setDocumentType(String documentType){this.attributes.put(AttributeKey.DOCUMENT_TYPE, documentType);}

	/**
	 * Get document folder string.
	 *
	 * @return the string
	 */
	public String getDocumentFolder(){
		return this.attributes.get(AttributeKey.DOCUMENT_FOLDER).toString();
	}

	/**
	 * Set document folder.
	 *
	 * @param documentFolder the document folder
	 */
	public void setDocumentFolder(String documentFolder){this.attributes.put(AttributeKey.DOCUMENT_FOLDER, documentFolder);}

	/**
	 * Get document prio string.
	 *
	 * @return the string
	 */
	public String getDocumentPrio(){
		return this.attributes.get(AttributeKey.DOCUMENT_PRIO).toString();
	}

	/**
	 * Set document prio.
	 *
	 * @param documentPrio the document prio
	 */
	public void setDocumentPrio(String documentPrio){this.attributes.put(AttributeKey.DOCUMENT_PRIO, documentPrio);}




}
