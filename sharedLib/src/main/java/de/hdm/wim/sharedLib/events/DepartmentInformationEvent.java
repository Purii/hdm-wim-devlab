package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 19.06.17.
 */
public class DepartmentInformationEvent extends IEvent {
	public DepartmentInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DEPARTMENT_INFO);
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

	public String getDepartmentId(){
		return this.attributes.get(AttributeKey.DEPARTMENT_ID).toString();
	}

	public void setDepartmentId(String departmentId){
		this.attributes.put(AttributeKey.DEPARTMENT_ID, departmentId);
	}

	public String getDepartmentName(){
		return this.attributes.get(AttributeKey.DEPARTMENT_NAME).toString();
	}

	public void setDepartmentName(String departmentName){
		this.attributes.put(AttributeKey.DEPARTMENT_NAME, departmentName);
	}

	public String getDepartmentShort(){
		return this.attributes.get(AttributeKey.DEPARTMENT_SHORT).toString();
	}

	public void setDepartmentShort(String departmentShort){
		this.attributes.put(AttributeKey.DEPARTMENT_SHORT, departmentShort);
	}

	public String getDepartmentProjects(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_PROJECTS).toString();
	}

	public void setDepartmentProjects(String project){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_PROJECTS, project);
	}

	public String getDepartmentWorker(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_WORKER).toString();
	}

	public void setDepartmentWorker(String worker){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_WORKER, worker);
	}

	public String getDepartmentCompany(){
		return this.attributes.get(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY).toString();
	}

	public void setDepartmentCompany(String departmentCompany){
		this.attributes.put(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY, departmentCompany.toString());
	}
}


