package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class UserInformationEvent implements IEvent{
	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	// TODO: Update Event Type
	public UserInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.USER_INFO);
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

	public String getProjectName(){
		return this.attributes.get(AttributeKey.PROJECT_NAME).toString();
	}

	public void setProjectName(String projectName){
		this.attributes.put(AttributeKey.PROJECT_NAME, projectName);
	}

	public String getProjectRole(){
		return this.attributes.get(AttributeKey.PROJECT_ROLE).toString();
	}

	public void setProjectRole(String projectRole){
		this.attributes.put(AttributeKey.PROJECT_ROLE, projectRole);
	}

	public String getDepartmentShort(){
		return this.attributes.get(AttributeKey.DEPARTMENT_SHORT).toString();
	}

	public void setDepartmentShort(String departmentShort){
		this.attributes.put(AttributeKey.DEPARTMENT_SHORT, departmentShort);
	}

	public String getDocumentAuthor(){
		return this.attributes.get(AttributeKey.DOCUMENT_AUTHOR).toString();
	}

	public void setDocumentAuthor(String documentAuthor){
		this.attributes.put(AttributeKey.DOCUMENT_AUTHOR, documentAuthor);
	}

	public String getDocumentCall(){
		return this.attributes.get(AttributeKey.DOCUMENT_CALL).toString();
	}

	public void setDocumentCall(String documentCall){
		this.attributes.put(AttributeKey.DOCUMENT_CALL, documentCall);
	}

	public String getDocumentFavorit(){
		return this.attributes.get(AttributeKey.DOCUMENT_FAVORIT).toString();
	}

	public void setDocumentFavorit(String documentFavorit){
		this.attributes.put(AttributeKey.DOCUMENT_FAVORIT, documentFavorit);
	}
}
