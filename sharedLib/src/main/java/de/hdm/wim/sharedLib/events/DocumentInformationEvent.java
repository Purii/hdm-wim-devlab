package de.hdm.wim.sharedLib.events;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Document information event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#documentinformationevent">DocumentInformationEvent</a>
 */
public class DocumentInformationEvent extends IEvent {

	/**
	 * Instantiates a new Document information event.
	 */
	public DocumentInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DOCUMENT_INFO);
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
	 * Get timestamp string.
	 *
	 * @return the string
	 */
	public String getTimestamp(){
		return this.attributes.get(AttributeKey.TIMESTAMP).toString();
	}

	/**
	 * Set timestamp.
	 *
	 * @param timestamp the timestamp
	 */
	public void setTimestamp(String timestamp){
		this.attributes.put(AttributeKey.TIMESTAMP, timestamp);
	}

	/**
	 * Get token id string.
	 *
	 * @return the string
	 */
	public String getTokenId(){
		return this.attributes.get(AttributeKey.TOKEN_ID).toString();
	}

	/**
	 * Set token id.
	 *
	 * @param tokenId the token id
	 */
	public void setTokenId(String tokenId){
		this.attributes.put(AttributeKey.TOKEN_ID, tokenId);
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
	 * Set document url.
	 *
	 * @param documentUrl the document url
	 */
	public void setDocumentUrl(String documentUrl){
		this.attributes.put(AttributeKey.DOCUMENT_URL, documentUrl);
	}

	/**
	 * Get document added string.
	 *
	 * @return the string
	 */
	public String getDocumentAdded(){
		return this.attributes.get(AttributeKey.DOCUMENT_ADDED).toString();
	}

	/**
	 * Set document added.
	 *
	 * @param documentAdded the document added
	 */
	public void setDocumentAdded(String documentAdded){
		this.attributes.put(AttributeKey.DOCUMENT_ADDED, documentAdded);
	}


	/**
	 * Get document version string.
	 *
	 * @return the string
	 */
	public String getDocumentVersion(){
		return this.attributes.get(AttributeKey.DOCUMENT_VERSION).toString();
	}

	/**
	 * Set document version.
	 *
	 * @param documentVersion the document version
	 */
	public void setDocumentVersion(String documentVersion){this.attributes.put(AttributeKey.DOCUMENT_VERSION, documentVersion);}

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
	 * Get document written by string.
	 *
	 * @return the string
	 */
	public String getDocumentWrittenBy(){
		return this.attributes.get(AttributeKey.DOCUMENT_WRITTEN_BY).toString();
	}

	/**
	 * Set document written by.
	 *
	 * @param documentWrittenBy the document written by
	 */
	public void setDocumentWrittenBy(String documentWrittenBy){this.attributes.put(AttributeKey.DOCUMENT_WRITTEN_BY, documentWrittenBy );}

	/**
	 * Get document belongs to project string.
	 *
	 * @return the string
	 */
	public String getDocumentBelongsToProject(){
		return this.attributes.get(AttributeKey.DOCUMENT_BELONGS_TO_PROJECT).toString();
	}

	/**
	 * Set document belongs to project.
	 *
	 * @param documentBelongsToProject the document belongs to project
	 */
	public void setDocumentBelongsToProject(String documentBelongsToProject){this.attributes.put(AttributeKey.DOCUMENT_BELONGS_TO_PROJECT, documentBelongsToProject);}

	/**
	 * Get document favored by string.
	 *
	 * @return the string
	 */
	public String getDocumentFavoredBy(){
		return this.attributes.get(AttributeKey.DOCUMENT_FAVORED_BY).toString();
	}

	/**
	 * Set document favored by.
	 *
	 * @param documentFavoredBy the document favored by
	 */
	public void setDocumentFavoredBy(String documentFavoredBy){this.attributes.put(AttributeKey.DOCUMENT_FAVORED_BY, documentFavoredBy);}

	/**
	 * Get document keywords string.
	 *
	 * @return the string
	 */
	public String getDocumentKeywords(){
		return this.attributes.get(AttributeKey.DOCUMENT_KEYWORDS).toString();
	}

	/**
	 * Set document keywords.
	 *
	 * @param documentKeywords the document keywords
	 */
	public void setDocumentKeywords(String documentKeywords){this.attributes.put(AttributeKey.DOCUMENT_KEYWORDS, documentKeywords);}

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
}
