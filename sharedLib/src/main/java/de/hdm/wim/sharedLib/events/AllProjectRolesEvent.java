package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 20.06.2017.
 */
public class AllProjectRolesEvent extends IEvent{

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	public AllProjectRolesEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ALL_PROJECTROLES);
	}

	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	public String getProjectRolse (){
		return attributes.get(Constants.PubSub.AttributeKey.PROJECT_ROLES).toString();
	}

	public void setProjectRoles(String projectRoles) {
		this.attributes.put(Constants.PubSub.AttributeKey.PROJECT_ROLES, projectRoles);
	}
}
