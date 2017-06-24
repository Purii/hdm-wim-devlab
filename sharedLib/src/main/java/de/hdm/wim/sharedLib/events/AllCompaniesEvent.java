package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Chris
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#allcompaniesevent">AllCompaniesEvent</a>
 */
public class AllCompaniesEvent extends IEvent{
	/**
	 * Constructor
	 * set initial Attributes
	 */
	public AllCompaniesEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ALL_COMPANIES);
	}

	/**
	 *
	 * @return SessionId
	 */
	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	/**
	 * set SessionId
	 * @param SessionId
	 */
	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	/**
	 *
	 * @return CompanyNames as commaseparated String
	 */
	public String getCompanyNames(){
		return attributes.get(Constants.PubSub.AttributeKey.COMPANY_NAMES).toString();
	}

	/**
	 * Set CompanyNames as commaseparated String
	 * @param companyNames
	 */
	public void setCompanyNames(String companyNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.COMPANY_NAMES, companyNames);
	}
}
