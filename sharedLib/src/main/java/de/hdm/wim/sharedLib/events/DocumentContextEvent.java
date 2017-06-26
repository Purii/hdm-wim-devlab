package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Document context event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#documentcallevent">DocumentCallEvent</a>
 */
public class DocumentContextEvent extends IEvent {

	/**
	 * Instantiates a new Document context event.
	 */
	public DocumentContextEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DOCUMENT_CONTEXT);
	}

	/**
	 * Gets document ids.
	 *
	 * @return the document ids
	 */
	public String getDocumentIds() {
		return attributes.get(AttributeKey.DOCUMENT_IDS).toString();
	}

	/**
	 * Sets document ids.
	 *
	 * @param documentIds the document ids
	 */
	public void setDocumentIds(String documentIds) {
		attributes.put(AttributeKey.DOCUMENT_IDS, documentIds.toString());
	}

	/**
	 * Gets document names.
	 *
	 * @return the document names
	 */
	public String getDocumentNames() {
		return attributes.get(AttributeKey.DOCUMENT_NAMES).toString();
	}

	/**
	 * Sets document names.
	 *
	 * @param documentNames the document names
	 */
	public void setDocumentNames(String documentNames) {
		attributes.put(AttributeKey.DOCUMENT_NAMES, documentNames.toString());
	}

	/**
	 * Get context string.
	 *
	 * @return the string
	 */
	public String getContext(){
		return this.attributes.get(AttributeKey.CONTEXT).toString();
	}

	/**
	 * Set context.
	 *
	 * @param context the context
	 */
	public void setContext(String context){
		this.attributes.put(AttributeKey.CONTEXT, context);
	}
}
