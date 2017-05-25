package de.hdm.wim.event.pubsub.Helper;

import com.google.api.gax.core.ApiFuture;
import com.google.api.gax.core.ApiFutures;
import com.google.cloud.pubsub.spi.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 23/04/2017.
 */
public class PublishHelper {

	private static final Logger logger = Logger.getLogger(PublishHelper.class);

	/**
	 * Publish a message.
	 *
	 * @param message the message
	 * @param topicName the topic name
	 * @throws Exception the exception
	 */
	public void publishMessage(String message, TopicName topicName) throws Exception {

		Publisher publisher 						= null;
		List<ApiFuture<String>> messageIdFutures 	= new ArrayList<>();

    	try {
			publisher = Publisher.defaultBuilder(topicName).build();

				ByteString data 					= ByteString.copyFromUtf8(message);
				PubsubMessage pubsubMessage 		= PubsubMessage.newBuilder()
														.setData(data)
														.build();

				//TODO: add attributes to message
				// Once published, returns a server-assigned message id (unique within the topic)
				ApiFuture<String> messageIdFuture 	= publisher.publish(pubsubMessage);
				messageIdFutures.add(messageIdFuture);

		} finally {
			// wait on any pending publish requests.
			List<String> messageIds = ApiFutures.allAsList(messageIdFutures).get();

			for (String messageId : messageIds) {
				logger.info("published with message ID: " + messageId);
			}

			if (publisher != null) {
				// When finished with the publisher, shutdown to free up resources.
				publisher.shutdown();
			}
		}
	}

	/**
	 * Publish messages.
	 *
	 * @param messages the messages
	 * @param topicName the topic name
	 * @throws Exception the exception
	 */
	public void publishMessages(List<String> messages, TopicName topicName) throws Exception {

		Publisher publisher 						= null;
		List<ApiFuture<String>> messageIdFutures 	= new ArrayList<>();

		try {
			publisher = Publisher.defaultBuilder(topicName).build();

			// schedule publishing one message at a time : messages get automatically batched
			for (String message : messages) {
				ByteString data 					= ByteString.copyFromUtf8(message);
				PubsubMessage pubsubMessage 		= PubsubMessage.newBuilder()
					.setData(data)
					.build();

				//TODO: add attributes to message
				// Once published, returns a server-assigned message id (unique within the topic)
				ApiFuture<String> messageIdFuture 	= publisher.publish(pubsubMessage);
				messageIdFutures.add(messageIdFuture);
			}

		} finally {
			// wait on any pending publish requests.
			List<String> messageIds = ApiFutures.allAsList(messageIdFutures).get();

			for (String messageId : messageIds) {
				logger.info("published with message ID: " + messageId);
			}

			if (publisher != null) {
				// When finished with the publisher, shutdown to free up resources.
				publisher.shutdown();
			}
		}
	}
}