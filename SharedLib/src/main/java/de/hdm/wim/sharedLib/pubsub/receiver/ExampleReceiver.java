package de.hdm.wim.sharedLib.pubsub.receiver;

import com.google.cloud.pubsub.spi.v1.AckReplyConsumer;
import com.google.cloud.pubsub.spi.v1.MessageReceiver;
import com.google.gson.Gson;
import com.google.pubsub.v1.PubsubMessage;
import org.apache.log4j.Logger;

/**
 * Created by ben on 15/06/2017.
 */
public class ExampleReceiver implements MessageReceiver {
	private static final Logger LOGGER 	= Logger.getLogger(ExampleReceiver.class);

	@Override
	public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
		// handle incoming message, then ack/nack the received message
		LOGGER.info("ExampleReceiver");

		LOGGER.info("Id : " 		+ message.getMessageId());
		LOGGER.info("Data : " 		+ message.getData().toStringUtf8());
		LOGGER.info("Attributes: "  + new Gson().toJson(message.getAttributesMap()).toString());

		consumer.ack();
	}
}
