package de.hdm.wim.sharedLib.pubsub.helper;

import com.google.api.gax.grpc.ExecutorProvider;
import com.google.api.gax.grpc.InstantiatingExecutorProvider;
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

	private static final Logger LOGGER 	= Logger.getLogger(SubscriptionHelper.class);
	private static boolean IS_LOCAL 	= false;
	private static String PROJECT_ID	= Config.APP_ID;
	private String ENDPOINT;
	private String HANDLER;

	/**
	 * Instantiates a new SubscriptionHelper.
	 *
	 * @param projectId the projectId, format: "my-project-id", by default {@value
	 * SubscriptionHelper#PROJECT_ID}
	 */
	public SubscriptionHelper(String projectId) {
		PROJECT_ID 	= projectId;
		ENDPOINT 	= EndpointHelper.GetPublishEndpoint(IS_LOCAL);
	}

	/**
	 * Instantiates a new SubscriptionHelper. Using the projectId from Constants
	 *
	 * @param isLocal true if you are running a local webapp, by default {@value
	 * SubscriptionHelper#IS_LOCAL}
	 */
	public SubscriptionHelper(boolean isLocal) {
		IS_LOCAL 	= isLocal;
		ENDPOINT 	= EndpointHelper.GetPublishEndpoint(IS_LOCAL);
	}

	/**
	 * Instantiates a new SubscriptionHelper.
	 *
	 * @param isLocal true if you are running a local webapp, by default {@value
	 * SubscriptionHelper#IS_LOCAL}
	 * @param projectId the project id, by default {@value SubscriptionHelper#PROJECT_ID}
	 */
	public SubscriptionHelper(boolean isLocal, String projectId) {
		IS_LOCAL 	= isLocal;
		PROJECT_ID	= projectId;
		//HANDLER     = handler;
	}

	/**
	 * Create a pull subscription to the given topic or return an existing subscription.
	 *
	 * @param topicId name of the topic, see {@link de.hdm.wim.sharedLib.Constants.PubSub.Topic}
	 * @param suffix suffix to separate subscriptions, result: "subscription-push/pull-topicId-suffix"
	 * @return Subscription subscription
	 * @throws Exception the exception
	 */
	public Subscription CreatePullSubscription(String topicId, String suffix) throws Exception{

		LOGGER.info("creating pull subscription...");

		TopicName topicName 	= TopicName.newBuilder().setTopic(topicId).setProject(PROJECT_ID).build();
		final String namePrefix = "subscription-";

		String	subscriptionId 	= namePrefix + SubscriptionType.PULL + "-" + topicName.getTopic() + "-" + suffix;
		PushConfig pushConfig 	= PushConfig.getDefaultInstance();

		return subscribe(subscriptionId, topicName, pushConfig);
	}

	/**
	 * Create a push subscription to the given topic or return an existing subscription.
	 *
	 * @param topicId name of the topic, see {@link de.hdm.wim.sharedLib.Constants.PubSub.Topic}
	 * @param handler endpoint handler
	 * @return Subscription subscription
	 * @throws Exception the exception
	 */
	public Subscription CreatePushSubscription(String topicId, String handler) throws  Exception
	{
		LOGGER.info("creating push subscription...");

		TopicName topicName 	= TopicName.newBuilder().setTopic(topicId).setProject(PROJECT_ID).build();
		final String namePrefix = "subscription-";
		ENDPOINT 				= EndpointHelper.GetPushEndpoint(IS_LOCAL, HANDLER);
		String	subscriptionId 	= namePrefix + SubscriptionType.PUSH + "-" + topicName.getTopic() + "-" + handler;
		PushConfig	pushConfig 	= PushConfig.newBuilder().setPushEndpoint(ENDPOINT).build();

		return subscribe(subscriptionId, topicName, pushConfig);
	}

	private Subscription subscribe(String subscriptionId, TopicName topicName, PushConfig pushConfig) throws Exception{
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

			// check if subscription with same name exists, if yes use it
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
				pushConfig,
				10
			);
			LOGGER.info("Successfully created subscription: " + subscriptionId);

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

		//LOGGER.info("existing subscriptions:");
		try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {

			ListSubscriptionsRequest listSubscriptionsRequest =
				ListSubscriptionsRequest.newBuilder()
					.setProjectWithProjectName(ProjectName.create(PROJECT_ID))
					.build();

			PagedResponseWrappers.ListSubscriptionsPagedResponse response = subscriptionAdminClient
				.listSubscriptions(listSubscriptionsRequest);
			Iterable<Subscription> subscriptions = response.iterateAll();
			for (Subscription subscription : subscriptions) {
				//LOGGER.info(subscription.getName());
			}
			return subscriptions;
		}
	}

	/**
	 * Subscribe to a topic using a subscription.
	 *
	 * @param subscription the subscription
	 * @param receiver the receiver
	 * @throws Exception the exception
	 */
	public void Subscribe(Subscription subscription, MessageReceiver receiver) throws Exception{

		LOGGER.info("test0");

		SubscriptionName subscriptionName 	= subscription.getNameAsSubscriptionName();
		Subscriber subscriber 				= null;

		LOGGER.info("subscriptionId: " + subscription.getName());
		LOGGER.info("topic: " + subscription.getTopic());

		ExecutorProvider executorProvider =	InstantiatingExecutorProvider.newBuilder()
			.setExecutorThreadCount(1)
			.build();

		LOGGER.info("test1");

		try {
			LOGGER.info("test2");
			// Create a subscriber bound to the message receiver
			subscriber = Subscriber
				.defaultBuilder(subscriptionName, receiver)
				.setExecutorProvider(executorProvider)
				.build();


			LOGGER.info("test2.1");

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


			LOGGER.info("test2.2");

			subscriber.startAsync().awaitRunning();


			LOGGER.info("test2.3");

			Thread.sleep(1000); //5 Minutes = 300000
		} catch (InterruptedException e){

			LOGGER.info("test3");
			LOGGER.error(e);

			e.printStackTrace();
		} finally {

			LOGGER.info("test4");
			// stop receiving messages
			if (subscriber != null) {
				subscriber.stopAsync();
			}
		}
	}
}