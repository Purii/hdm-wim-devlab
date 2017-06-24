package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#projectinformationevent">ProjectInformationEvent</a>
 */
public class ProjectInformationEvent extends IEvent {
	public ProjectInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.PROJECT_INFO);
	}

	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	public String getTimestamp(){
		return this.attributes.get(AttributeKey.TIMESTAMP).toString();
	}

	public void setTimestamp(String timestamp){
		this.attributes.put(AttributeKey.TIMESTAMP, timestamp);
	}

	public String getTokenId(){
		return this.attributes.get(AttributeKey.TOKEN_ID).toString();
	}

	public void setTokenId(String tokenId){
		this.attributes.put(AttributeKey.TOKEN_ID, tokenId);
	}

	public String getProjectId(){
		return this.attributes.get(AttributeKey.PROJECT_ID).toString();
	}

	public void setProjectId(String projectId){
		this.attributes.put(AttributeKey.PROJECT_ID, projectId);
	}

	public String getProjectName(){
		return this.attributes.get(AttributeKey.PROJECT_NAME).toString();
	}

	public void setProjectName(String projectName){
		this.attributes.put(AttributeKey.PROJECT_NAME, projectName);
	}

	public String getProjectBelongsToCompany(){
		return this.attributes.get(AttributeKey.PROJECT_BELONGS_TO_COMPANY).toString();
	}

	public void setProjectBelongsToCompany(String projectBelongsToCompany){
		this.attributes.put(AttributeKey.PROJECT_BELONGS_TO_COMPANY, projectBelongsToCompany);
	}

	public String getProjectBelongsToDepartment(){
		return this.attributes.get(AttributeKey.PROJECT_BELONGS_TO_DEPARTMENT).toString();
	}

	public void setProjectBelongsToDepartment(String projectBelongsToDepartment){
		this.attributes.put(AttributeKey.PROJECT_BELONGS_TO_DEPARTMENT, projectBelongsToDepartment);
	}

	public String getProjectHasMembers(){
		return this.attributes.get(AttributeKey.PROJECT_HAS_MEMBERS).toString();
	}

	public void setProjectHasMembers(String projectHasMembers){
		this.attributes.put(AttributeKey.PROJECT_HAS_MEMBERS, projectHasMembers);
	}

	public String getProjectHasDocuments(){
		return this.attributes.get(AttributeKey.PROJECT_HAS_DOCUMENTS).toString();
	}

	public void setProjectHasDocuments(String projectHasDocuments){
		this.attributes.put(AttributeKey.PROJECT_HAS_DOCUMENTS, projectHasDocuments);
	}

}
