package de.hdm.wim.sharedLib.pubsub;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.EventSource;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.TOPIC_1;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.events.LearnEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

/**
 * Created by ben on 11/06/2017.
 */
public class ExamplePublish {
	private static final long MESSAGE_PERIOD_SECONDS 	= 1;
	private static int CURRENT 							= 1;
	private static int MAX_NUMBER_OF_MESSAGES 			= 10;

	/**
	 * before you publish, make sure there is a subscription, otherwise messages might get lost Oo
	 */
	public static void main(String[] args) throws Exception {

		// init a SubscriptionHelper to use for prod environment for the given project
		SubscriptionHelper sh = new SubscriptionHelper(false, Config.APP_ID);

		/**
		 * this will create a subscription with id: "subscription-pull-topic-1-test1"
		 * if the subscription already exists, we will use it
		 */
		sh.CreatePullSubscription(TOPIC_1.TOPIC_ID, "test1");
		sh.CreatePullSubscription(TOPIC_1.TOPIC_ID, "test2");

		PublishHelper ph = new PublishHelper(false);

		while (CURRENT <= MAX_NUMBER_OF_MESSAGES) {
			Event event = Event.generate("blubb_" + CURRENT);

			// event, topic, useREST = true/false
			ph.Publish(event, TOPIC_1.TOPIC_ID, false);

			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));

			CURRENT++;
		}

		LearnEvent learnEvent = new LearnEvent();
		learnEvent.setData("test");
		learnEvent.setDocumentId("");
		learnEvent.setEventSource(EventSource.MACHINE_LEARNING);
		learnEvent.setProjectId("test project id");
		learnEvent.setDocumentAffiliation("false");
		learnEvent.setUserId("test user id");
		ph.Publish(learnEvent, TOPIC_1.TOPIC_ID);


		Event feedbackEvent = new Event();
		feedbackEvent.setData("feedbackEvent");
		feedbackEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.FEEDBACK);}});
		ph.Publish(feedbackEvent, TOPIC_1.TOPIC_ID);


		Event offerEvent = new Event();
		offerEvent.setData("offerEvent");
		offerEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.OFFER);}});
		ph.Publish(offerEvent, TOPIC_1.TOPIC_ID);


		Event stayaliveEvent = new Event();
		stayaliveEvent.setData("stayaliveEvent");
		stayaliveEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.STAYALIVE);}});
		ph.Publish(stayaliveEvent, TOPIC_1.TOPIC_ID);


		Event tokenEvent = new Event();
		tokenEvent.setData("tokenEvent");
		tokenEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.TOKEN);}});
		ph.Publish(tokenEvent, TOPIC_1.TOPIC_ID);
	}
}
