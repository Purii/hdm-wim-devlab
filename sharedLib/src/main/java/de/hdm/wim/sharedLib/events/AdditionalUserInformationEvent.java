package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Additional user information event.
 *
 * @author Christian Schneider
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
	 * Get user id string.
	 *
	 * @return UserId string
	 */
	public String getUserId(){
		return attributes.get(Constants.PubSub.AttributeKey.USER_ID).toString();
	}

	/**
	 * Set UserId
	 *
	 * @param userId the user id
	 */
	public void setUserId(String userId){
		this.attributes.put(Constants.PubSub.AttributeKey.USER_ID, userId);
	}

	/**
	 * Gets first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return this.attributes.get(AttributeKey.FIRST_NAME);
	}

	/**
	 * Sets first name.
	 *
	 * @param firstName the first name
	 */
	public void setFirstName(String firstName) {
		this.attributes.put(AttributeKey.FIRST_NAME, firstName);
	}

	/**
	 * Gets last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return this.attributes.get(AttributeKey.LAST_NAME);
	}

	/**
	 * Sets last name.
	 *
	 * @param lastName the last name
	 */
	public void setLastName(String lastName) {
		this.attributes.put(AttributeKey.LAST_NAME, lastName);
	}

	/**
	 * Gets mail.
	 *
	 * @return the mail
	 */
	public String getMail() {
		return this.attributes.get(AttributeKey.EMAIL);
	}

	/**
	 * Sets mail.
	 *
	 * @param mail the mail
	 */
	public void setMail(String mail) {
		this.attributes.put(AttributeKey.EMAIL, mail);
	}

	/**
	 * Gets department.
	 *
	 * @return the department
	 */
	public String getDepartment() {
		return this.attributes.get(AttributeKey.DEPARTMENT_NAME);
	}

	/**
	 * Sets department.
	 *
	 * @param department the department
	 */
	public void setDepartment(String department) {
		this.attributes.put(AttributeKey.DEPARTMENT_NAME, department);
	}

	/**
	 * Gets project.
	 *
	 * @return the project
	 */
	public String getProject() {
		return this.attributes.get(AttributeKey.PROJECT_NAME);
	}

	/**
	 * Sets project.
	 *
	 * @param project the project
	 */
	public void setProject(String project) {
		this.attributes.put(AttributeKey.PROJECT_NAME, project);
	}

	/**
	 * Gets project role.
	 *
	 * @return the project role
	 */
	public String getProjectRole() {
		return this.attributes.get(AttributeKey.PROJECT_ROLE);
	}

	/**
	 * Sets project role.
	 *
	 * @param projectRole the project role
	 */
	public void setProjectRole(String projectRole) {
		this.attributes.put(AttributeKey.PROJECT_ROLE, projectRole);
	}
}
