package de.hdm.wim.sharedLib.testing;

import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import java.util.concurrent.TimeUnit;

/**
 * Created by ben on 11/06/2017.
 */
public class TestPublish {
	private static final long MESSAGE_PERIOD_SECONDS = 1;

	/**
	 * before you publish, make sure there is a subscription, otherwise messages might get lost Oo
	 */
	public static void main(String[] args) throws Exception {
		int COUNT 					= 1;
		int MAX_NUMBER_OF_MESSAGES 	= 10;

		PublishHelper ph = new PublishHelper(false);

		while (COUNT <= MAX_NUMBER_OF_MESSAGES) {
			Event event = Event.generate("topic-1_" + COUNT);
			// event, topic, useREST = true/false
			ph.Publish(event, Topic.TOPIC_1, false);
			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
			COUNT++;
		}

		while (COUNT <= MAX_NUMBER_OF_MESSAGES) {
			Event event = Event.generate("topic-2_" + COUNT);
			// event, topic, useREST = true/false
			ph.Publish(event, Topic.TOPIC_2, false);
			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));
			COUNT++;
		}
	}
}
