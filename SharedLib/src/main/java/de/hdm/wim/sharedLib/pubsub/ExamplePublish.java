package de.hdm.wim.sharedLib.pubsub;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

/**
 * Created by ben on 11/06/2017.
 */
public class ExamplePublish {
	private static final long MESSAGE_PERIOD_SECONDS = 1;

	// publish one message every second
	public static void main(String[] args) throws Exception {
		int i 				= 1;

		PublishHelper ph 	= new PublishHelper(false);

		while (i <= 3) {
			Event event   = Event.generate("blubb_" + i);

			ph.Publish(event, Topic.TOPIC_1);

			Thread.sleep(TimeUnit.SECONDS.toMillis(MESSAGE_PERIOD_SECONDS));

			i++;
		}

		Event feedbackEvent = new Event();
		feedbackEvent.setData("feedbackEvent");
		feedbackEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.FEEDBACK);}});

		Event insightEvent = new Event();
		insightEvent.setData("insightEvent");
		insightEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.INSIGHT);}});

		Event offerEvent = new Event();
		offerEvent.setData("offerEvent");
		offerEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.OFFER);}});

		Event richTokenEvent = new Event();
		richTokenEvent.setData("richTokenEvent");
		richTokenEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.RICHTOKEN);}});

		Event stayaliveEvent = new Event();
		stayaliveEvent.setData("stayaliveEvent");
		stayaliveEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.STAYALIVE);}});

		Event tokenEvent = new Event();
		tokenEvent.setData("tokenEvent");
		tokenEvent.setAttributes(new Hashtable<String, String>(){{put(AttributeKey.EVENT_TYPE, EventType.TOKEN);}});

		ph.Publish(feedbackEvent, 	Topic.TOPIC_1);
		ph.Publish(insightEvent, 	Topic.TOPIC_1);
		ph.Publish(offerEvent, 		Topic.TOPIC_1);
		ph.Publish(richTokenEvent, 	Topic.TOPIC_1);
		ph.Publish(stayaliveEvent, 	Topic.TOPIC_1);
		ph.Publish(tokenEvent, 		Topic.TOPIC_1);
	}
}
