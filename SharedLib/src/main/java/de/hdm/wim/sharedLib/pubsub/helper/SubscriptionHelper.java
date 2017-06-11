package de.hdm.wim.sharedLib.pubsub.helper;

import com.google.api.gax.grpc.ExecutorProvider;
import com.google.api.gax.grpc.InstantiatingExecutorProvider;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.spi.v1.MessageReceiver;
import com.google.cloud.pubsub.spi.v1.PagedResponseWrappers;
import com.google.cloud.pubsub.spi.v1.Subscriber;
import com.google.cloud.pubsub.spi.v1.SubscriptionAdminClient;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.pubsub.v1.ListSubscriptionsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import org.apache.log4j.Logger;

/**
 * Created by ben on 04/06/2017.
 */
public class SubscriptionHelper {

	private static final Logger LOGGER = Logger.getLogger(SubscriptionHelper.class);
	private String ENDPOINT;
	private boolean IS_LOCAL;
	private String PROJECT_ID;

	/**
	 * Instantiates a new TopicHelper.
	 *
	 * @param isLocal true if you are running a local webapp, false in prod
	 */
	public SubscriptionHelper(boolean isLocal) {
		IS_LOCAL = isLocal;
		PROJECT_ID= ServiceOptions.getDefaultProjectId();

		if(IS_LOCAL)
			ENDPOINT = Config.LOCAL_PUSH_ENDPOINT;
		else
			ENDPOINT = Config.PROD_PUSH_ENDPOINT;
	}

	/**
	 * Instantiates a new TopicHelper.
	 *
	 * @param isLocal true if you are running a local webapp, false in prod
	 * @param projectId the project id, you can find it in the constants
	 */
	public SubscriptionHelper(boolean isLocal, String projectId) {
		IS_LOCAL 	= isLocal;
		PROJECT_ID	= projectId;

		if(IS_LOCAL)
			ENDPOINT = Config.LOCAL_PUSH_ENDPOINT;
		else
			ENDPOINT = Config.PROD_PUSH_ENDPOINT;
	}

	/**
	 * Create a pull subscription to the given topic.
	 *
	 * @param topicName the topic name
	 * @param subscriptionId the subscription id, eg. "my-test-subscription"
	 * @return the subscription
	 * @throws Exception the exception
	 */
	private Subscription createPullSubscriptionIfNotExists(TopicName topicName, String subscriptionId) throws Exception{

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

			Iterable<Subscription> subscriptions 	= getSubscriptions();

			for(Subscription subscription : subscriptions){
				if(subscription.getNameAsSubscriptionName().getSubscription().equals(subscriptionId))
				{
					LOGGER.info("Using existing subscription: " + subscriptionId);
					return subscription;
				}
			}

			SubscriptionName subscriptionName =	SubscriptionName.create(PROJECT_ID, subscriptionId);

			// create a pull subscription with default acknowledgement deadline
			Subscription subscription = subscriptionAdminClient.createSubscription(
				subscriptionName,
				topicName,
				PushConfig.getDefaultInstance(),
				10
			);

			LOGGER.info("Successfully created pull subscription: " + subscriptionId);

			return subscription;
		}
	}

	/**
	 * Create a push subscription to the given topic.
	 *
	 * @param topicName the topic name
	 * @param subscriptionId the subscription id, eg. "my-test-subscription"
	 * @return the subscription
	 * @throws Exception the exception
	 */
	private Subscription createPushSubscriptionIfNotExists(TopicName topicName, String subscriptionId) throws Exception{

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

			Iterable<Subscription> subscriptions = getSubscriptions();

			for(Subscription subscription : subscriptions){
				if(subscription.getNameAsSubscriptionName().getSubscription().equals(subscriptionId))
				{
					LOGGER.info("Using existing subscription: " + subscriptionId);
					return subscription;
				}
			}

			SubscriptionName subscriptionName 	= SubscriptionName.create(PROJECT_ID, subscriptionId);

			// set endpoint
			PushConfig pushConfig 				= PushConfig.newBuilder().setPushEndpoint(ENDPOINT).build();

			// create a push subscription with default acknowledgement deadline
			Subscription subscription = subscriptionAdminClient.createSubscription(
				subscriptionName,
				topicName,
				pushConfig,
				10
			);

			LOGGER.info("Successfully created push subscription: " + subscriptionId);

			return subscription;
		}
	}

	/**
	 * Gets subscriptions.
	 *
	 * @return the subscriptions
	 * @throws Exception the exception
	 */
	public Iterable<Subscription> getSubscriptions() throws Exception {

		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

			ListSubscriptionsRequest listSubscriptionsRequest =
				ListSubscriptionsRequest.newBuilder()
					.setProjectWithProjectName(ProjectName.create(PROJECT_ID))
					.build();

			PagedResponseWrappers.ListSubscriptionsPagedResponse response = subscriptionAdminClient
				.listSubscriptions(listSubscriptionsRequest);
			Iterable<Subscription> subscriptions = response.iterateAll();
			for (Subscription subscription : subscriptions) {
				LOGGER.info(subscription.getName());
			}
			return subscriptions;
		}
	}

	/**
	 * Create subscriber.
	 *
	 * @param subscription the subscription
	 * @throws Exception the exception
	 */
	private void createSubscriber(Subscription subscription) throws Exception{

		SubscriptionName subscriptionName 	= subscription.getNameAsSubscriptionName();
		Subscriber subscriber 				= null;

		ExecutorProvider executorProvider =	InstantiatingExecutorProvider.newBuilder()
			.setExecutorThreadCount(1)
			.build();

		LOGGER.info("TEST1");

		// Instantiate an asynchronous message receiver
		MessageReceiver receiver = (message, consumer) ->{

			// handle incoming message, then ack/nack the received message
			LOGGER.info("Id : " + message.getMessageId());
			LOGGER.info("Data : " + message.getData().toStringUtf8());
			LOGGER.info("Attributes: "  + message.getAttributesMap().toString());

			consumer.ack();
		};

		try {
			// Create a subscriber bound to the message receiver
			subscriber = Subscriber
				.defaultBuilder(subscriptionName, receiver)
				.setExecutorProvider(executorProvider)
				.build();

			subscriber.addListener(
				new Subscriber.Listener() {
					@Override
					public void failed(Subscriber.State from, Throwable failure) {
						// Handle failure. This is called when the Subscriber encountered a fatal error and is shutting down.
						LOGGER.error(failure);
					}
				},
				MoreExecutors.directExecutor()
			);

			subscriber.startAsync().awaitRunning();

			Thread.sleep(30000); //5 Minutes = 300000
		} catch (InterruptedException e){
			e.printStackTrace();
		} finally {
			// stop receiving messages
			if (subscriber != null) {
				subscriber.stopAsync();
			}
		}
	}

	/**
	 * Subscribe.
	 *
	 * @param subscriptionType type of subscription: push or pull, see Constants!
	 * @param topicName name of the topic
	 * @throws Exception the exception
	 */
	public void Subscribe(String subscriptionType, TopicName topicName) throws Exception{

		Subscription subscription = null;
		final String namePrefix = "subscription-";
		String subscriptionName;

		if(subscriptionType.equals(SubscriptionType.PULL)){
			LOGGER.info("pull");
			subscriptionName 	= namePrefix + SubscriptionType.PULL + "-" + topicName.getTopic();
			subscription 		= createPullSubscriptionIfNotExists(topicName, subscriptionName);
		}else if(subscriptionType.equals(SubscriptionType.PUSH)){
			LOGGER.info("push");
			subscriptionName 	= namePrefix + SubscriptionType.PUSH + "-" + topicName.getTopic();
			subscription 		= createPushSubscriptionIfNotExists(topicName, subscriptionName);
		}else{
			LOGGER.error("wrong subscription type: " + subscriptionType);
		}

		createSubscriber(subscription);
	}
}