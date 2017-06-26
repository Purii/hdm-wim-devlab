package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Project information event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#projectinformationevent">ProjectInformationEvent</a>
 */
public class ProjectInformationEvent extends IEvent {

	/**
	 * Instantiates a new Project information event.
	 */
	public ProjectInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.PROJECT_INFO);
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
	 * Get project id string.
	 *
	 * @return the string
	 */
	public String getProjectId(){
		return this.attributes.get(AttributeKey.PROJECT_ID).toString();
	}

	/**
	 * Set project id.
	 *
	 * @param projectId the project id
	 */
	public void setProjectId(String projectId){
		this.attributes.put(AttributeKey.PROJECT_ID, projectId);
	}

	/**
	 * Get project name string.
	 *
	 * @return the string
	 */
	public String getProjectName(){
		return this.attributes.get(AttributeKey.PROJECT_NAME).toString();
	}

	/**
	 * Set project name.
	 *
	 * @param projectName the project name
	 */
	public void setProjectName(String projectName){
		this.attributes.put(AttributeKey.PROJECT_NAME, projectName);
	}

	/**
	 * Get project belongs to company string.
	 *
	 * @return the string
	 */
	public String getProjectBelongsToCompany(){
		return this.attributes.get(AttributeKey.PROJECT_BELONGS_TO_COMPANY).toString();
	}

	/**
	 * Set project belongs to company.
	 *
	 * @param projectBelongsToCompany the project belongs to company
	 */
	public void setProjectBelongsToCompany(String projectBelongsToCompany){
		this.attributes.put(AttributeKey.PROJECT_BELONGS_TO_COMPANY, projectBelongsToCompany);
	}

	/**
	 * Get project belongs to department string.
	 *
	 * @return the string
	 */
	public String getProjectBelongsToDepartment(){
		return this.attributes.get(AttributeKey.PROJECT_BELONGS_TO_DEPARTMENT).toString();
	}

	/**
	 * Set project belongs to department.
	 *
	 * @param projectBelongsToDepartment the project belongs to department
	 */
	public void setProjectBelongsToDepartment(String projectBelongsToDepartment){
		this.attributes.put(AttributeKey.PROJECT_BELONGS_TO_DEPARTMENT, projectBelongsToDepartment);
	}

	/**
	 * Get project has members string.
	 *
	 * @return the string
	 */
	public String getProjectHasMembers(){
		return this.attributes.get(AttributeKey.PROJECT_HAS_MEMBERS).toString();
	}

	/**
	 * Set project has members.
	 *
	 * @param projectHasMembers the project has members
	 */
	public void setProjectHasMembers(String projectHasMembers){
		this.attributes.put(AttributeKey.PROJECT_HAS_MEMBERS, projectHasMembers);
	}

	/**
	 * Get project has documents string.
	 *
	 * @return the string
	 */
	public String getProjectHasDocuments(){
		return this.attributes.get(AttributeKey.PROJECT_HAS_DOCUMENTS).toString();
	}

	/**
	 * Set project has documents.
	 *
	 * @param projectHasDocuments the project has documents
	 */
	public void setProjectHasDocuments(String projectHasDocuments){
		this.attributes.put(AttributeKey.PROJECT_HAS_DOCUMENTS, projectHasDocuments);
	}

}
