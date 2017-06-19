package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.Hashtable;

/**
 * Created by GezimKrasniqi on 19.06.17.
 */
public class DepartmentInformationEvent implements IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Hashtable attributes = new Hashtable<String, String>();

	// TODO: Update Event Type
	// auto set EventType on init
	public DepartmentInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.INSIGHT);
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

	public Hashtable<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Hashtable<String, String> attributes) {
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

	public String getDepartmentId(){
		return this.attributes.get(AttributeKey.DEPARTMENT_ID).toString();
	}

	public void setDepartmentId(String departmentId){
		this.attributes.put(AttributeKey.TOKEN_ID, departmentId);
	}

	public String getDepartmentName(){
		return this.attributes.get(AttributeKey.DEPARTMENT_NAME).toString();
	}

	public void setDepartmentName(String departmentName){
		this.attributes.put(AttributeKey.TOKEN_ID, departmentName);
	}

	public String getDepartmentShort(){
		return this.attributes.get(AttributeKey.DEPARTMENT_SHORT).toString();
	}

	public void setDepartmentShort(String departmentShort){
		this.attributes.put(AttributeKey.TOKEN_ID, departmentShort);
	}

	public String getDepartmentProjects(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_PROJECTS).toString();
	}

	public void setDepartmentProjects(String[] project ){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_PROJECTS, project.toString());
	}

	public String getDepartmentWorker(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_WORKER).toString();
	}

	public void setDepartmentWorker(String[] worker ){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_WORKER, worker.toString());
	}

	public String getDepartmentCompany(){
		return this.attributes.get(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY).toString();
	}

	public void setDepartmentCompany(String[] departmentCompany ){
		this.attributes.put(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY, departmentCompany.toString());
	}
}


