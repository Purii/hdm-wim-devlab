package de.hdm.wim.sharedLib.testing;

import com.google.pubsub.v1.Subscription;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.TOPIC_1;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.TOPIC_2;
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;

/**
 * Created by ben on 16/06/2017.
 */
public class TestCreateSubscription {

	public static void main(String[] args) throws Exception {
		SubscriptionHelper sh = new SubscriptionHelper(false, Config.APP_ID);

		Subscription subscription1 = sh
			.CreatePushSubscription(TOPIC_1.TOPIC_ID, TOPIC_1.HANDLER_ID);

		Subscription subscription2 = sh
			.CreatePushSubscription(Topic.TOPIC_2.TOPIC_ID, TOPIC_2.HANDLER_ID);
	}
}
