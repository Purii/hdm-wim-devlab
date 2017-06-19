package de.hdm.wim.sharedLib.testing;

import com.google.pubsub.v1.Subscription;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;

/**
 * Created by ben on 16/06/2017.
 */
public class TestCreateSubscription {

	public static void main(String[] args) throws Exception {
		SubscriptionHelper sh = new SubscriptionHelper(false, Config.APP_ID);

		Subscription subscription1 = sh
			.CreateSubscription(SubscriptionType.PUSH, Topic.TOPIC_1, "test1");

		Subscription subscription2 = sh
			.CreateSubscription(SubscriptionType.PUSH, Topic.TOPIC_2, "test1");

	}
}
