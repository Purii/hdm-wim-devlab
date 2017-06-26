package de.hdm.wim.sharedLib.testing;

import de.hdm.wim.sharedLib.Constants.PubSub.Topic.GUI_INFORMATION;
import de.hdm.wim.sharedLib.events.AdditionalUserInformationEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;

/**
 * The PubSubber will publish events to the given topic
 * this class is only for testing purposes!
 *
 * @author Benedikt Benz
 * @createdOn 23.06.2017
 */
public class PubSubber {

	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger
		.getLogger(PubSubber.class);

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		PublishHelper ph = new PublishHelper(false);

		while (true) {
			//Event event = Event.generate("data");

			AdditionalUserInformationEvent evt = new AdditionalUserInformationEvent();

			/*LOGGER.info("getData: " + event.getData());
			LOGGER.info("getEventSource: " + event.getEventSource());
			LOGGER.info("getEventType: " + event.getEventType());*/
			evt.setDepartment("Test_");
			evt.setData("test data");
			evt.setFirstName("bene");
			evt.setLastName("benz");
			evt.setMail("asdasd@asdas");
			evt.setProject("wasdas");
			evt.setProjectRole("asda333");

			ph.Publish(evt, GUI_INFORMATION.TOPIC_ID, true);

			Thread.sleep(1000);
		}
	}
}