package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.Map;

/**
 * Example:
 * 	LearnEvent learnEvent = new LearnEvent();
 *	learnEvent.setData("test");
 *	learnEvent.setDocumentId("");
 *	learnEvent.setEventSource(EventSource.MACHINE_LEARNING);
 *	learnEvent.setProjectId("test project id");
 *	learnEvent.setUserclick(false);
 *	learnEvent.setUserId("test user id");
 *
 * @author Ben
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#learnevent">LearnEvent</a>
 */
public class LearnEvent extends IEvent {
	public LearnEvent() {
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.LEARN);
	}

	public String getUserId(){
		return attributes.get(AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID, userId);
	}

	public String getDocumentId(){
		return attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	public void setDocumentId(String DocumentId){
		this.attributes.put(AttributeKey.DOCUMENT_ID, DocumentId);
	}

	public String getProjectId(){
		return attributes.get(AttributeKey.PROJECT_ID).toString();
	}

	public void setProjectId(String ProjectId){
		this.attributes.put(AttributeKey.PROJECT_ID, ProjectId);
	}

	public String getDocumentAffiliation(){
		return attributes.get(AttributeKey.DOCUMENT_AFFILIATION).toString();
	}

	public void setDocumentAffiliation(String documentAffiliation){
		this.attributes.put(AttributeKey.DOCUMENT_AFFILIATION, documentAffiliation);
	}

	public String getEventType(){
		return attributes.get(AttributeKey.EVENT_TYPE).toString();
	}

	public String getEventSource(){
		return attributes.get(AttributeKey.EVENT_SOURCE).toString();
	}

	public void setEventSource(String EventSource){
		this.attributes.put(AttributeKey.EVENT_SOURCE, EventSource);
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
