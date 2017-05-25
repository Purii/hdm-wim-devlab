package de.hdm.wim.event.pubsub;

import com.google.cloud.ServiceOptions;
import com.google.pubsub.v1.Topic;
import de.hdm.wim.event.pubsub.Helper.PublishHelper;
import de.hdm.wim.event.pubsub.Helper.SubscriptionHelper;
import de.hdm.wim.event.pubsub.Helper.TopicHelper;
import de.hdm.wim.event.shared.Enums;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by ben on 24/05/2017.
 */
public class Start {

	private static final Logger _logger 	= Logger.getLogger(Start.class);
	private static final String _projectId	= ServiceOptions.getDefaultProjectId();
	private static String _testTopic 		= Enums.Topic.topic1;
	private static String _subscriptionId   = "my-test-subscription-for-test-topic-1";

	public static void main(String[] args) throws Exception {

		_logger.info("projectId: " + _projectId);

		// create a new topic
		TopicHelper th = new TopicHelper(_projectId);
		Topic topic    = th.createTopicIfNotExists(_testTopic);

		// create subscription.
		// IMPORTANT: do this before you publish messages, otherwise messages might get lost
		SubscriptionHelper sh = new SubscriptionHelper(_projectId);
		sh.createSubscriptionIfNotExists(topic.getNameAsTopicName(), _subscriptionId);

		// publish messages to a specific topic
		PublishHelper ph 		= new PublishHelper();
		List<String> messages 	= Arrays.asList("first message", "second message");
		ph.publishMessages(messages, topic.getNameAsTopicName());

		// create a subscriber which uses the subscription to listen to messages of the specified topic
		sh.createSubscriber(_subscriptionId);
	}
}
