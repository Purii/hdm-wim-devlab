package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Chris
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#additionaluserinformationevent">AdditionalUserInformationEvent</a>
 */
public class AdditionalUserInformationEvent extends IEvent {

	/**
	 * Constructor
	 * set initial Attributes
	 */
	public AdditionalUserInformationEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.ADDITIONAL_USER_INFO);
	}

	/**
	 *
	 * @return UserId
	 */
	public String getUserId(){
		return attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	/**
	 * Set UserId
	 * @param userId
	 */
	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}
}
