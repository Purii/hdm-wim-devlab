package de.hdm.wim.sharedLib.pubsub;

import de.hdm.wim.sharedLib.Constants.PubSub;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;

/**
 * Created by ben on 11/06/2017.
 * Erstellt eine push subscription
 */
public class ExamplePush {

	public static void main(String[] args) throws Exception {

		// create the subscription
		SubscriptionHelper sh = new SubscriptionHelper(true);
		sh.Subscribe(SubscriptionType.PUSH, PubSub.Topic.TOPIC_1);
	}
}
