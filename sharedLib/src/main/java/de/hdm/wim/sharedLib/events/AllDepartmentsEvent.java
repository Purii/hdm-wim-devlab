package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Chris
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#alldepartmentsevent">AllDepartmentsEvent</a>
 */
public class AllDepartmentsEvent extends IEvent {
	/**
	 * Constructor
	 * set initial Attributes
	 */
	public AllDepartmentsEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ALL_DEPARTMENTS);
	}

	/**
	 *
	 * @return SessionId
	 */
	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Set SessionId
	 * @param SessionId
	 */
	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	/**
	 *
	 * @return DepartmentNames as commaseparated string
	 */
	public String getDepartmentNames(){
		return attributes.get(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES).toString();
	}

	/**
	 *
	 * @param departmentNames as commaseprated string
	 */
	public void setDepartmentNames(String departmentNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES, departmentNames);
	}
}
