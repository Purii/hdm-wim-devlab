package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type User information event.
 *
 * @author Gezim Krasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#userinformationevent">UserInformationEvent</a>
 */
public class UserInformationEvent extends IEvent {

	/**
	 * Instantiates a new User information event.
	 */
	public UserInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_INFO);
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
	 * Get user id string.
	 *
	 * @return the string
	 */
	public String getUserId(){
		return this.attributes.get(AttributeKey.USER_ID).toString();
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
	 * Get firstname string.
	 *
	 * @return the string
	 */
	public String getFirstname(){
		return this.attributes.get(AttributeKey.FIRST_NAME).toString();
	}

	/**
	 * Set firstname.
	 *
	 * @param firstname the firstname
	 */
	public void setFirstname(String firstname){
		this.attributes.put(AttributeKey.FIRST_NAME, firstname);
	}

	/**
	 * Get lastname string.
	 *
	 * @return the string
	 */
	public String getLastname(){
		return this.attributes.get(AttributeKey.LAST_NAME).toString();
	}

	/**
	 * Set lastname.
	 *
	 * @param lastname the lastname
	 */
	public void setLastname(String lastname){
		this.attributes.put(AttributeKey.LAST_NAME, lastname);
	}

	/**
	 * Get email string.
	 *
	 * @return the string
	 */
	public String getEmail(){
		return this.attributes.get(AttributeKey.EMAIL).toString();
	}

	/**
	 * Set email.
	 *
	 * @param email the email
	 */
	public void setEmail(String email){
		this.attributes.put(AttributeKey.EMAIL, email);
	}

	/**
	 * Get user works on project string.
	 *
	 * @return the string
	 */
	public String getUserWorksOnProject(){
		return this.attributes.get(AttributeKey.USER_WORKS_ON_PROJECTS).toString();
	}

	/**
	 * Set user works on project.
	 *
	 * @param userWorksOnProject the user works on project
	 */
	public void setUserWorksOnProject(String userWorksOnProject){
		this.attributes.put(AttributeKey.USER_WORKS_ON_PROJECTS, userWorksOnProject);
	}

	/**
	 * Get user has projectrole string.
	 *
	 * @return the string
	 */
	public String getUserHasProjectrole(){
		return this.attributes.get(AttributeKey.USER_HAS_PROJECTROLE).toString();
	}

	/**
	 * Set user has projectrole.
	 *
	 * @param userHasProjectrole the user has projectrole
	 */
	public void setUserHasProjectrole(String userHasProjectrole){
		this.attributes.put(AttributeKey.USER_HAS_PROJECTROLE, userHasProjectrole);
	}

	/**
	 * Get user belongs to department string.
	 *
	 * @return the string
	 */
	public String getUserBelongsToDepartment(){
		return this.attributes.get(AttributeKey.USER_BELONGS_TO_DEPARTMENT).toString();
	}

	/**
	 * Set user belongs to department.
	 *
	 * @param userBelongsToDepartment the user belongs to department
	 */
	public void setUserBelongsToDepartment(String userBelongsToDepartment){
		this.attributes.put(AttributeKey.USER_BELONGS_TO_DEPARTMENT, userBelongsToDepartment);
	}

	/**
	 * Get user writes document string.
	 *
	 * @return the string
	 */
	public String getUserWritesDocument(){
		return this.attributes.get(AttributeKey.USER_WRITES_DOCUMENT).toString();
	}

	/**
	 * Set user writes document.
	 *
	 * @param userWritesDocument the user writes document
	 */
	public void setUserWritesDocument(String userWritesDocument){
		this.attributes.put(AttributeKey.USER_WRITES_DOCUMENT, userWritesDocument);
	}

	/**
	 * Get user calls document string.
	 *
	 * @return the string
	 */
	public String getUserCallsDocument(){
		return this.attributes.get(AttributeKey.USER_CALLS_DOCUMENT).toString();
	}

	/**
	 * Set user calls document.
	 *
	 * @param userCallsDocument the user calls document
	 */
	public void setUserCallsDocument(String userCallsDocument){
		this.attributes.put(AttributeKey.USER_CALLS_DOCUMENT, userCallsDocument);
	}

	/**
	 * Get user favours document string.
	 *
	 * @return the string
	 */
	public String getUserFavoursDocument(){
		return this.attributes.get(AttributeKey.USER_FAVOURS_DOCUMENT).toString();
	}

	/**
	 * Set user favours document.
	 *
	 * @param userFavoursDocument the user favours document
	 */
	public void setUserFavoursDocument(String userFavoursDocument){
		this.attributes.put(AttributeKey.USER_FAVOURS_DOCUMENT, userFavoursDocument);
	}
}
