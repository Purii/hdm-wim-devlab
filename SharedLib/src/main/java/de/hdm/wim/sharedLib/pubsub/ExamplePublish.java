package de.hdm.wim.sharedLib.pubsub;

import de.hdm.wim.sharedLib.Constants.PubSub;
import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.EventSource;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
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
		sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test1");
		sh.CreateSubscription(SubscriptionType.PULL, PubSub.Topic.TOPIC_1, "test2");

		PublishHelper ph = new PublishHelper(false);

		while (CURRENT <= MAX_NUMBER_OF_MESSAGES) {
			Event event = Event.generate("blubb_" + CURRENT);

			// event, topic, useREST = true/false
			ph.Publish(event, Topic.TOPIC_1, false);

			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));

			CURRENT++;
		}

		LearnEvent learnEvent = new LearnEvent();
		learnEvent.setData("test");
		learnEvent.setDocumentId("");
		learnEvent.setEventSource(EventSource.MACHINE_LEARNING);
		learnEvent.setProjectId("test project id");
		learnEvent.setUserclick(false);
		learnEvent.setUserId("test user id");
		ph.Publish(learnEvent, 	Topic.TOPIC_1);


		Event feedbackEvent = new Event();
		feedbackEvent.setData("feedbackEvent");
		feedbackEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.FEEDBACK);}});
		ph.Publish(feedbackEvent, 	Topic.TOPIC_1);


		Event insightEvent = new Event();
		insightEvent.setData("insightEvent");
		insightEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.INSIGHT);}});
		ph.Publish(insightEvent, 	Topic.TOPIC_1);


		Event offerEvent = new Event();
		offerEvent.setData("offerEvent");
		offerEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.OFFER);}});
		ph.Publish(offerEvent, 		Topic.TOPIC_1);


		Event richTokenEvent = new Event();
		richTokenEvent.setData("richTokenEvent");
		richTokenEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.RICHTOKEN);}});
		ph.Publish(richTokenEvent, 	Topic.TOPIC_1);


		Event stayaliveEvent = new Event();
		stayaliveEvent.setData("stayaliveEvent");
		stayaliveEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.STAYALIVE);}});
		ph.Publish(stayaliveEvent, 	Topic.TOPIC_1);


		Event tokenEvent = new Event();
		tokenEvent.setData("tokenEvent");
		tokenEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.TOKEN);}});
		ph.Publish(tokenEvent, 		Topic.TOPIC_1);
	}
}
