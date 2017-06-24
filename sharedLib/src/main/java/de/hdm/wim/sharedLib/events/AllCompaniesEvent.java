package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 20.06.2017.
 */
public class AllCompaniesEvent extends IEvent{
	public AllCompaniesEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ALL_COMPANIES);
	}

	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	public String getCompanyNames(){
		return attributes.get(Constants.PubSub.AttributeKey.COMPANY_NAMES).toString();
	}

	public void setCompanyNames(String companyNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.COMPANY_NAMES, companyNames);
	}
}
