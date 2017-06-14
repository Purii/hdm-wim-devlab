package de.hdm.wim.sharedLib.pubsub.helper;

import com.google.api.gax.grpc.ExecutorProvider;
import com.google.api.gax.grpc.InstantiatingExecutorProvider;
import com.google.cloud.pubsub.spi.v1.MessageReceiver;
import com.google.cloud.pubsub.spi.v1.PagedResponseWrappers;
import com.google.cloud.pubsub.spi.v1.Subscriber;
import com.google.cloud.pubsub.spi.v1.SubscriptionAdminClient;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.pubsub.v1.ListSubscriptionsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.SubscriptionType;
import java.util.stream.Stream;
import org.apache.log4j.Logger;

/**
 * Created by ben on 04/06/2017.
 */
public class SubscriptionHelper {

	private static final Logger LOGGER 	= Logger.getLogger(SubscriptionHelper.class);
	private static boolean IS_LOCAL 	= false;
	private static boolean USE_REST 	= false;
	private static String PROJECT_ID	= Config.APP_ID;
	private String ENDPOINT;

	/**
	 * Instantiates a new SubscriptionHelper.
	 *
	 * @param projectId the projectId, format: "my-project-id", by default {@value SubscriptionHelper#PROJECT_ID}
	 */
	public SubscriptionHelper(String projectId) {
		PROJECT_ID 	= projectId;

		if(IS_LOCAL)
			ENDPOINT = Config.getLocalPushEndpoint();
		else
			ENDPOINT = Config.getProdPushEndpoint();
	}

	/**
	 * Instantiates a new SubscriptionHelper. Using the projectId from Constants
	 *
	 * @param isLocal true if you are running a local webapp, by default {@value SubscriptionHelper#IS_LOCAL}
	 * @param useREST true if you want to use REST, by default {@value SubscriptionHelper#USE_REST}
	 */
	public SubscriptionHelper(boolean isLocal, boolean useREST) {
		IS_LOCAL 	= isLocal;
		USE_REST 	= useREST;

		if(IS_LOCAL)
			ENDPOINT = Config.getLocalPushEndpoint();
		else
			ENDPOINT = Config.getProdPushEndpoint();
	}

	/**
	 * Instantiates a new SubscriptionHelper.
	 *
	 * @param isLocal true if you are running a local webapp, by default {@value SubscriptionHelper#IS_LOCAL}
	 * @param useREST true if you want to use REST, by default {@value SubscriptionHelper#USE_REST}
	 * @param projectId the project id, by default {@value SubscriptionHelper#PROJECT_ID}
	 */
	public SubscriptionHelper(boolean isLocal, boolean useREST, String projectId) {
		IS_LOCAL 	= isLocal;
		PROJECT_ID	= projectId;
		USE_REST 	= useREST;

		if(IS_LOCAL)
			ENDPOINT = Config.getLocalPushEndpoint();
		else
			ENDPOINT = Config.getProdPushEndpoint();
	}


	/**
	 * Create a subscription to the given topic or return an existing subscription.
	 *
	 * @param subscriptionType type of subscription: push or pull, see Constants!
	 * @param topicId name of the topic
	 * @param suffix suffix to separate subscriptions, result: "subscription-push/pull-topicId-suffix"
	 * @throws Exception the exception
	 */
	public Subscription CreateSubscription(String subscriptionType, String topicId, String suffix) throws Exception{

		LOGGER.info("creating subscription...");

		TopicName topicName 		= TopicName.newBuilder().setTopic(topicId).setProject(PROJECT_ID).build();
		final String namePrefix 	= "subscription-";
		PushConfig pushConfig		= null;
		String subscriptionId		= "";

		if(subscriptionType.equals(SubscriptionType.PULL)){
			subscriptionId 	= namePrefix + SubscriptionType.PULL + "-" + topicName.getTopic() + "-" + suffix;
			pushConfig 			= PushConfig.getDefaultInstance();
		}else if(subscriptionType.equals(SubscriptionType.PUSH)){
			subscriptionId 	= namePrefix + SubscriptionType.PUSH + "-" + topicName.getTopic() + "-" + suffix;
			pushConfig 			= PushConfig.newBuilder().setPushEndpoint(ENDPOINT).build();
		}else{
			LOGGER.error("wrong subscription type: " + subscriptionType);
			return null;
		}

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
	 * @throws Exception the exception
	 */
	public void Subscribe(Subscription subscription) throws Exception{

		SubscriptionName subscriptionName 	= subscription.getNameAsSubscriptionName();
		Subscriber subscriber 				= null;

		ExecutorProvider executorProvider =	InstantiatingExecutorProvider.newBuilder()
			.setExecutorThreadCount(1)
			.build();

		Stream<PubsubMessage> eventStream = Stream.empty();

		// Instantiate an asynchronous message receiver
		MessageReceiver receiver = (message, consumer) ->{

			// handle incoming message, then ack/nack the received message
			LOGGER.info("Id : " 		+ message.getMessageId());
			LOGGER.info("Data : " 		+ message.getData().toStringUtf8());
			LOGGER.info("Attributes: "  + new Gson().toJson(message.getAttributesMap()).toString());

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
}