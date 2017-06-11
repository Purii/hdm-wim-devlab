package de.hdm.wim.sharedLib.pubsub;

import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.classes.Message;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import java.util.concurrent.TimeUnit;

/**
 * Created by ben on 11/06/2017.
 */
public class ExamplePublish {
	private static final long MESSAGE_PERIOD_SECONDS = 1;

	// publish one message every second
	public static void main(String[] args) throws Exception {
		int i 				= 1;

		PublishHelper ph 	= new PublishHelper(true);

		while (i <= 3) {
			Message message   = Message.generate("blubb_" + i, Topic.TOPIC_1);

			ph.Publish(message);

			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));

			i++;
		}
	}
}
