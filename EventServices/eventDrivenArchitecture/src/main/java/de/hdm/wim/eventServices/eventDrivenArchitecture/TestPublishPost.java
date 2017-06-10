package de.hdm.wim.eventServices.eventDrivenArchitecture;

import de.hdm.wim.eventServices.eventDrivenArchitecture.helper.PublishHelper;
import de.hdm.wim.sharedLib.Constants.Topic;
import de.hdm.wim.sharedLib.classes.Message;
import java.util.concurrent.TimeUnit;

/**
 * Created by ben on 5/06/2017.
 *
 * Publishes the generated messages via POST
 * make sure the app in pubSubTesting is running.
 */
public class TestPublishPost {
	private static final long MESSAGE_PERIOD_SECONDS = 1;

	public static void main(String[] args) throws Exception {
		int i 				= 1;
		PublishHelper ph 	= new PublishHelper();

		while (i <= 3) {
			Message message   = Message.generate("blubb_" + i, Topic.PUSH_TEST);

			ph.Publish(message);

			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));

			i++;
		}
		Message message1   	= new Message("blubb_", Topic.PUSH_TEST);
		Message message2  	= new Message("blubb_", Topic.PUSH_TEST);
		Message message3	= new Message("blubb_", Topic.PUSH_TEST);

		ph.Publish(message1);
		ph.Publish(message2);
		ph.Publish(message3);


	}
}
