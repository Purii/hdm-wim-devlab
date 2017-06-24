package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 20.06.2017.
 */
public class AllDepartmentsEvent extends IEvent {

	private String data;
	private String id;
	private String publishTime;
	private Map<String, String> attributes = new HashMap<String, String>();

	public AllDepartmentsEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ALL_DEPARTMENTS);
	}

	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	public String getDepartmentNames(){
		return attributes.get(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES).toString();
	}

	public void setDepartmentNames(String departmentNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.DEPARTMENT_NAMES, departmentNames);
	}
}
