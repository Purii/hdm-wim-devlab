package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class ProjectInformationEvent implements IEvent{

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	// TODO: Update Event Type
	public ProjectInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.PROJECT_INFO);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id= id;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getEventType() {
		return attributes.get(AttributeKey.EVENT_TYPE).toString();
	}

	/*
	public void setEventType(String EventType){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType);
	}
	*/

	public String getEventSource() {
		return attributes.get(AttributeKey.EVENT_SOURCE).toString();
	}

	public void setEventSource(String EventSource) {
		this.attributes.put(AttributeKey.EVENT_SOURCE, EventSource);
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
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
