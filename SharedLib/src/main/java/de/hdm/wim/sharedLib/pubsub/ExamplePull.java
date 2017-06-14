package de.hdm.wim.sharedLib.pubsub;

import de.hdm.wim.sharedLib.Constants.PubSub;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;

/**
 * Created by ben on 11/06/2017.
 * Erstellt eine pull subscription
 */
public class ExamplePull {

	public static void main(String[] args) throws Exception {

		// create the subscription
		SubscriptionHelper sh = new SubscriptionHelper(true);

		//TODO: suffix/prefix to create multiple subscriptions for one topic
		//TODO: receiver to handle message
		sh.Subscribe(SubscriptionType.PULL, PubSub.Topic.TOPIC_1);
	}
}
