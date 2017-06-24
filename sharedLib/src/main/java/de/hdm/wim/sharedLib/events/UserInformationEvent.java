package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class UserInformationEvent extends IEvent {
	public UserInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_INFO);
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

	public String getUserId(){
		return this.attributes.get(AttributeKey.USER_ID).toString();
	}

	public void setUserId(String userId){
		this.attributes.put(AttributeKey.USER_ID, userId);
	}

	public String getFirstname(){
		return this.attributes.get(AttributeKey.FIRST_NAME).toString();
	}

	public void setFirstname(String firstname){
		this.attributes.put(AttributeKey.FIRST_NAME, firstname);
	}

	public String getLastname(){
		return this.attributes.get(AttributeKey.LAST_NAME).toString();
	}

	public void setLastname(String lastname){
		this.attributes.put(AttributeKey.LAST_NAME, lastname);
	}

	public String getEmail(){
		return this.attributes.get(AttributeKey.EMAIL).toString();
	}

	public void setEmail(String email){
		this.attributes.put(AttributeKey.EMAIL, email);
	}

	public String getUserWorksOnProject(){
		return this.attributes.get(AttributeKey.USER_WORKS_ON_PROJECTS).toString();
	}

	public void setUserWorksOnProject(String userWorksOnProject){
		this.attributes.put(AttributeKey.USER_WORKS_ON_PROJECTS, userWorksOnProject);
	}

	public String getUserHasProjectrole(){
		return this.attributes.get(AttributeKey.USER_HAS_PROJECTROLE).toString();
	}

	public void setUserHasProjectrole(String userHasProjectrole){
		this.attributes.put(AttributeKey.USER_HAS_PROJECTROLE, userHasProjectrole);
	}

	public String getUserBelongsToDepartment(){
		return this.attributes.get(AttributeKey.USER_BELONGS_TO_DEPARTMENT).toString();
	}

	public void setUserBelongsToDepartment(String userBelongsToDepartment){
		this.attributes.put(AttributeKey.USER_BELONGS_TO_DEPARTMENT, userBelongsToDepartment);
	}

	public String getUserWritesDocument(){
		return this.attributes.get(AttributeKey.USER_WRITES_DOCUMENT).toString();
	}

	public void setUserWritesDocument(String userWritesDocument){
		this.attributes.put(AttributeKey.USER_WRITES_DOCUMENT, userWritesDocument);
	}

	public String getUserCallsDocument(){
		return this.attributes.get(AttributeKey.USER_CALLS_DOCUMENT).toString();
	}

	public void setUserCallsDocument(String userCallsDocument){
		this.attributes.put(AttributeKey.USER_CALLS_DOCUMENT, userCallsDocument);
	}

	public String getUserFavoursDocument(){
		return this.attributes.get(AttributeKey.USER_FAVOURS_DOCUMENT).toString();
	}

	public void setUserFavoursDocument(String userFavoursDocument){
		this.attributes.put(AttributeKey.USER_FAVOURS_DOCUMENT, userFavoursDocument);
	}
}
