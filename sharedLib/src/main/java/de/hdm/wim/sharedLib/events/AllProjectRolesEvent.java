package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type All project roles event.
 *
 * @author Christian Schneider
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#allprojectrolesevent">AllProjectRolesEvent</a>
 */
public class AllProjectRolesEvent extends IEvent {

	/**
	 * Constructor
	 * set initial Attributes
	 */
	public AllProjectRolesEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ALL_PROJECTROLES);
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
	 * Set session id.
	 *
	 * @param SessionId the session id
	 */
	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	/**
	 * Get project roles string.
	 *
	 * @return ProjectRoles as commaseparated string
	 */
	public String getProjectRoles (){
		return attributes.get(Constants.PubSub.AttributeKey.PROJECT_ROLES).toString();
	}

	/**
	 * Set ProjectRoles
	 *
	 * @param projectRoles as commaseparated string
	 */
	public void setProjectRoles(String projectRoles) {
		this.attributes.put(Constants.PubSub.AttributeKey.PROJECT_ROLES, projectRoles);
	}
}
