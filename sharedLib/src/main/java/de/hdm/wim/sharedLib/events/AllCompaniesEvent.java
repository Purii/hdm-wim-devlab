package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type All companies event.
 *
 * @author Christian Schneider
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
	 * Get session id string.
	 *
	 * @return SessionId string
	 */
	public String getSessionId(){
		return attributes.get(Constants.PubSub.AttributeKey.SESSION_ID).toString();
	}

	/**
	 * set SessionId
	 *
	 * @param SessionId the session id
	 */
	public void setSessionId(String SessionId){
		this.attributes.put(Constants.PubSub.AttributeKey.SESSION_ID, SessionId);
	}

	/**
	 * Get company names string.
	 *
	 * @return CompanyNames as commaseparated String
	 */
	public String getCompanyNames(){
		return attributes.get(Constants.PubSub.AttributeKey.COMPANY_NAMES).toString();
	}

	/**
	 * Set CompanyNames as commaseparated String
	 *
	 * @param companyNames the company names
	 */
	public void setCompanyNames(String companyNames) {
		this.attributes.put(Constants.PubSub.AttributeKey.COMPANY_NAMES, companyNames);
	}
}
