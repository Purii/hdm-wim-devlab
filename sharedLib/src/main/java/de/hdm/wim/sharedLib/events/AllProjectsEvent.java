package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;


/**
 * @author Chris
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#allprojectsevent">AllProjectsEvent</a>
 */
public class AllProjectsEvent extends IEvent {
	/**
	 * Constructor
	 * set initial Attributes
	 */
	public AllProjectsEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ALL_PROJECTS);
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
	 * @return ProjectNames as commaseparated string
	 */
	public String getProjectNames(){
		return attributes.get(Constants.PubSub.AttributeKey.PROJECT_NAMES).toString();
	}

	/**
	 *
	 * @param projectNames as commaseparated string
	 */
	public void setProjectNames(String projectNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.PROJECT_NAMES, projectNames);
	}
}
