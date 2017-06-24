package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 19.06.17.
 */
public class DocumentContextEvent extends IEvent {
	public DocumentContextEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DOCUMENT_CONTEXT);
	}

	public String getDocumentIds() {
		return attributes.get(AttributeKey.DOCUMENT_IDS).toString();
	}

	public void setDocumentIds(String documentIds) {
		attributes.put(AttributeKey.DOCUMENT_IDS, documentIds.toString());
	}

	public String getDocumentNames() {
		return attributes.get(AttributeKey.DOCUMENT_NAMES).toString();
	}

	public void setDocumentNames(String documentNames) {
		attributes.put(AttributeKey.DOCUMENT_NAMES, documentNames.toString());
	}

	public String getContext(){
		return this.attributes.get(AttributeKey.CONTEXT).toString();
	}

	public void setContext(String context){
		this.attributes.put(AttributeKey.CONTEXT, context);
	}
}
