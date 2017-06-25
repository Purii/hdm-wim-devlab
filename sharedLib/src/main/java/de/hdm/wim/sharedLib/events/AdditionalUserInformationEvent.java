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

	public String getFirstName() {
		return this.attributes.get(AttributeKey.FIRST_NAME);
	}

	public void setFirstName(String firstName) {
		this.attributes.put(AttributeKey.FIRST_NAME, firstName);
	}

	public String getLastName() {
		return this.attributes.get(AttributeKey.LAST_NAME);
	}

	public void setLastName(String lastName) {
		this.attributes.put(AttributeKey.LAST_NAME, lastName);
	}

	public String getMail() {
		return this.attributes.get(AttributeKey.EMAIL);
	}

	public void setMail(String mail) {
		this.attributes.put(AttributeKey.EMAIL, mail);
	}

	public String getDepartment() {
		return this.attributes.get(AttributeKey.DEPARTMENT_NAME);
	}

	public void setDepartment(String department) {
		this.attributes.put(AttributeKey.DEPARTMENT_NAME, department);
	}

	public String getProject() {
		return this.attributes.get(AttributeKey.PROJECT_NAME);
	}

	public void setProject(String project) {
		this.attributes.put(AttributeKey.PROJECT_NAME, project);
	}

	public String getProjectRole() {
		return this.attributes.get(AttributeKey.PROJECT_ROLE);
	}

	public void setProjectRole(String projectRole) {
		this.attributes.put(AttributeKey.PROJECT_ROLE, projectRole);
	}


}
