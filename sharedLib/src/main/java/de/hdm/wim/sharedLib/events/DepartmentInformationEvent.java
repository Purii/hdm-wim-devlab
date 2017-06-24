package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#departmentinformationevent">DepartmentInformationEvent</a>
 */
public class DepartmentInformationEvent extends IEvent {
	/**
	 * Constructor
	 * set initial Attributes
	 */
	public DepartmentInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.DEPARTMENT_INFO);
	}

	/**
	 *
	 * @return SessionId
	 */
	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Set SessionId
	 * @param sessionId
	 */
	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	/**
	 *
	 * @return timestamp
	 */
	public String getTimestamp(){
		return this.attributes.get(AttributeKey.TIMESTAMP).toString();
	}

	/**
	 * Set Timestamp
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp){
		this.attributes.put(AttributeKey.TIMESTAMP, timestamp);
	}

	/**
	 *
	 * @return TokenId
	 */
	public String getTokenId(){
		return this.attributes.get(AttributeKey.TOKEN_ID).toString();
	}

	/**
	 * Set tokenId
	 * @param tokenId
	 */
	public void setTokenId(String tokenId){
		this.attributes.put(AttributeKey.TOKEN_ID, tokenId);
	}

	/**
	 *
	 * @return DepartmentId
	 */
	public String getDepartmentId(){
		return this.attributes.get(AttributeKey.DEPARTMENT_ID).toString();
	}

	/**
	 * Set departmentName
	 * @param departmentId
	 */
	public void setDepartmentId(String departmentId){
		this.attributes.put(AttributeKey.DEPARTMENT_ID, departmentId);
	}

	/**
	 *
	 * @return DepartmentName
	 */
	public String getDepartmentName(){
		return this.attributes.get(AttributeKey.DEPARTMENT_NAME).toString();
	}

	/**
	 * Set departmentName
	 * @param departmentName
	 */
	public void setDepartmentName(String departmentName){
		this.attributes.put(AttributeKey.DEPARTMENT_NAME, departmentName);
	}

	/**
	 *
	 * @return DepartmentShort
	 */
	public String getDepartmentShort(){
		return this.attributes.get(AttributeKey.DEPARTMENT_SHORT).toString();
	}

	/**
	 * Set departmentShort
	 * @param departmentShort
	 */
	public void setDepartmentShort(String departmentShort){
		this.attributes.put(AttributeKey.DEPARTMENT_SHORT, departmentShort);
	}

	/**
	 *
	 * @return DepartmentHasProjects
	 */
	public String getDepartmentHasProjects(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_PROJECTS).toString();
	}

	/**
	 * Set departmentHasproject
	 * @param project
	 */
	public void setDepartmentHasProjects(String project){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_PROJECTS, project);
	}

	/**
	 *
	 * @return departmentHasWorker
	 */
	public String getDepartmentHasWorker(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_WORKER).toString();
	}

	/**
	 *
	 * @param worker
	 */
	public void setDepartmentHasWorker(String worker){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_WORKER, worker);
	}

	/**
	 *
	 * @return departmentCompany
	 */
	public String getDepartmentCompany(){
		return this.attributes.get(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY).toString();
	}

	/**
	 *
	 * @param departmentCompany
	 */
	public void setDepartmentCompany(String departmentCompany){
		this.attributes.put(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY, departmentCompany.toString());
	}
}


