package de.hdm.wim.sharedLib.testing;

import com.google.cloud.pubsub.spi.v1.MessageReceiver;
import com.google.pubsub.v1.Subscription;
import de.hdm.wim.sharedLib.Constants.PubSub;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import de.hdm.wim.sharedLib.pubsub.helper.SubscriptionHelper;
import de.hdm.wim.sharedLib.pubsub.receiver.ExampleReceiver;

/**
 * Created by ben on 11/06/2017.
 */
public class TestPush {

	public static void main(String[] args) throws Exception {

		MessageReceiver receiver = new ExampleReceiver();

		// create the subscription
		SubscriptionHelper sh 		= new SubscriptionHelper(false, Config.APP_ID);
		Subscription subscription 	= sh.CreateSubscription(SubscriptionType.PUSH, PubSub.Topic.TOPIC_1, "test1");

		sh.Subscribe(subscription, receiver);
	}
}
