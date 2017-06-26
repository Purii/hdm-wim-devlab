package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type All departments event.
 *
 * @author Christian Schneider
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
	 * Get session id string.
	 *
	 * @return SessionId string
	 */
	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	/**
	 * Set SessionId
	 *
	 * @param SessionId the session id
	 */
	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	/**
	 * Get department names string.
	 *
	 * @return DepartmentNames as commaseparated string
	 */
	public String getDepartmentNames(){
		return attributes.get(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES).toString();
	}

	/**
	 * Sets department names.
	 *
	 * @param departmentNames as commaseprated string
	 */
	public void setDepartmentNames(String departmentNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES, departmentNames);
	}
}
