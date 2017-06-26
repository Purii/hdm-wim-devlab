package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.Map;

/**
 * Example for usage
 * LearnEvent learnEvent = new LearnEvent();
 * learnEvent.setData("test");
 * learnEvent.setDocumentId("");
 * learnEvent.setEventSource(EventSource.MACHINE_LEARNING);
 * learnEvent.setProjectId("test project id");
 * learnEvent.setUserclick(false);
 * learnEvent.setUserId("test user id");
 *
 * @author Benedikt Benz
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#learnevent">LearnEvent</a>
 */
public class LearnEvent extends IEvent {

	/**
	 * Instantiates a new Learn event.
	 */
	public LearnEvent() {
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.LEARN);
	}

	/**
	 * Get user id string.
	 *
	 * @return the string
	 */
	public String getUserId(){
		return attributes.get(AttributeKey.USER_ID).toString();
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
		return attributes.get(AttributeKey.DOCUMENT_ID).toString();
	}

	/**
	 * Set document id.
	 *
	 * @param DocumentId the document id
	 */
	public void setDocumentId(String DocumentId){
		this.attributes.put(AttributeKey.DOCUMENT_ID, DocumentId);
	}

	/**
	 * Get project id string.
	 *
	 * @return the string
	 */
	public String getProjectId(){
		return attributes.get(AttributeKey.PROJECT_ID).toString();
	}

	/**
	 * Set project id.
	 *
	 * @param ProjectId the project id
	 */
	public void setProjectId(String ProjectId){
		this.attributes.put(AttributeKey.PROJECT_ID, ProjectId);
	}

	/**
	 * Get document affiliation string.
	 *
	 * @return the string
	 */
	public String getDocumentAffiliation(){
		return attributes.get(AttributeKey.DOCUMENT_AFFILIATION).toString();
	}

	/**
	 * Set document affiliation.
	 *
	 * @param documentAffiliation the document affiliation
	 */
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
