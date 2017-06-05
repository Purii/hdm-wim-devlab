package de.hdm.wim.eventServices.eventDrivenArchitecture;

import com.google.cloud.ServiceOptions;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.Topic;
import de.hdm.wim.eventServices.eventDrivenArchitecture.helper.PublishHelperOld;
import de.hdm.wim.eventServices.eventDrivenArchitecture.helper.SubscriptionHelper;
import de.hdm.wim.eventServices.eventDrivenArchitecture.helper.TopicHelper;
import de.hdm.wim.sharedLib.Constants;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by ben on 24/05/2017.
 */
public class RunPubSub {

	private static final Logger LOGGER 		= Logger.getLogger(RunPubSub.class);
	private static final String PROJECT_ID	= ServiceOptions.getDefaultProjectId();
	private static String TEST_TOPIC 		= Constants.Topic.PUSH_TEST;
	private static String SUBSCRIPTION_ID 	= "test-subscription";

	public static void main(String[] args) throws Exception {

		LOGGER.info("projectId: " + PROJECT_ID);

		// create a new topic
		TopicHelper th = new TopicHelper();
		Topic topic    = th.createTopicIfNotExists(TEST_TOPIC);

		LOGGER.info("test1");

		// create subscription.
		// IMPORTANT: do this before you publish messages, otherwise messages might get lost
		SubscriptionHelper sh 		= new SubscriptionHelper(PROJECT_ID);
		Subscription subscription 	= sh.createSubscriptionIfNotExists(topic.getNameAsTopicName(),
			SUBSCRIPTION_ID);

		LOGGER.info("test2");

		// publish messages to a specific topic
		PublishHelperOld phOLD	= new PublishHelperOld();
		List<String> messages 	= Arrays.asList("first message", "second message");
		phOLD.publishMessages(messages, topic.getNameAsTopicName());

		// create a subscriber which uses the subscription to listen to messages of the specified topic
		sh.createSubscriber(subscription.getNameAsSubscriptionName());
	}
}
