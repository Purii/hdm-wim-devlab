package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Department information event.
 *
 * @author Gezim Krasniqi
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
	 * Get session id string.
	 *
	 * @return SessionId string
	 */
	public String getSessionId(){
		return this.attributes.get(AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Set SessionId
	 *
	 * @param sessionId the session id
	 */
	public void setSessionId(String sessionId){
		this.attributes.put(AttributeKey.SESSION_ID, sessionId);
	}

	/**
	 * Get timestamp string.
	 *
	 * @return timestamp string
	 */
	public String getTimestamp(){
		return this.attributes.get(AttributeKey.TIMESTAMP).toString();
	}

	/**
	 * Set Timestamp
	 *
	 * @param timestamp the timestamp
	 */
	public void setTimestamp(String timestamp){
		this.attributes.put(AttributeKey.TIMESTAMP, timestamp);
	}

	/**
	 * Get token id string.
	 *
	 * @return TokenId string
	 */
	public String getTokenId(){
		return this.attributes.get(AttributeKey.TOKEN_ID).toString();
	}

	/**
	 * Set tokenId
	 *
	 * @param tokenId the token id
	 */
	public void setTokenId(String tokenId){
		this.attributes.put(AttributeKey.TOKEN_ID, tokenId);
	}

	/**
	 * Get department id string.
	 *
	 * @return DepartmentId string
	 */
	public String getDepartmentId(){
		return this.attributes.get(AttributeKey.DEPARTMENT_ID).toString();
	}

	/**
	 * Set departmentName
	 *
	 * @param departmentId the department id
	 */
	public void setDepartmentId(String departmentId){
		this.attributes.put(AttributeKey.DEPARTMENT_ID, departmentId);
	}

	/**
	 * Get department name string.
	 *
	 * @return DepartmentName string
	 */
	public String getDepartmentName(){
		return this.attributes.get(AttributeKey.DEPARTMENT_NAME).toString();
	}

	/**
	 * Set departmentName
	 *
	 * @param departmentName the department name
	 */
	public void setDepartmentName(String departmentName){
		this.attributes.put(AttributeKey.DEPARTMENT_NAME, departmentName);
	}

	/**
	 * Get department short string.
	 *
	 * @return DepartmentShort string
	 */
	public String getDepartmentShort(){
		return this.attributes.get(AttributeKey.DEPARTMENT_SHORT).toString();
	}

	/**
	 * Set departmentShort
	 *
	 * @param departmentShort the department short
	 */
	public void setDepartmentShort(String departmentShort){
		this.attributes.put(AttributeKey.DEPARTMENT_SHORT, departmentShort);
	}

	/**
	 * Get department has projects string.
	 *
	 * @return DepartmentHasProjects string
	 */
	public String getDepartmentHasProjects(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_PROJECTS).toString();
	}

	/**
	 * Set departmentHasproject
	 *
	 * @param project the project
	 */
	public void setDepartmentHasProjects(String project){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_PROJECTS, project);
	}

	/**
	 * Get department has worker string.
	 *
	 * @return departmentHasWorker string
	 */
	public String getDepartmentHasWorker(){
		return this.attributes.get(AttributeKey.DEPARTMENT_HAS_WORKER).toString();
	}

	/**
	 * Set department has worker.
	 *
	 * @param worker the worker
	 */
	public void setDepartmentHasWorker(String worker){
		this.attributes.put(AttributeKey.DEPARTMENT_HAS_WORKER, worker);
	}

	/**
	 * Get department company string.
	 *
	 * @return departmentCompany string
	 */
	public String getDepartmentCompany(){
		return this.attributes.get(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY).toString();
	}

	/**
	 * Set department company.
	 *
	 * @param departmentCompany the department company
	 */
	public void setDepartmentCompany(String departmentCompany){
		this.attributes.put(AttributeKey.DEPARTMENT_BELONGS_TO_COMPANY, departmentCompany.toString());
	}
}


