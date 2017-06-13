package de.hdm.wim.sharedLib.pubsub;

import com.google.pubsub.v1.Topic;
import de.hdm.wim.sharedLib.Constants.PubSub;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;
import de.hdm.wim.sharedLib.pubsub.helper.TopicHelper;

/**
 * Created by ben on 11/06/2017.
 * Erstellt eine push subscription
 */
public class ExamplePush {

	public static void main(String[] args) throws Exception {

		// create the topic
		TopicHelper th = new TopicHelper();
		Topic topic    = th.createTopicIfNotExists(PubSub.Topic.TOPIC_1);

		// create the subscription
		SubscriptionHelper sh = new SubscriptionHelper(true);
		sh.Subscribe(SubscriptionType.PUSH, topic.getNameAsTopicName());
	}
}
