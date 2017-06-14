package de.hdm.wim.sharedLib.pubsub.helper;

import com.google.cloud.pubsub.spi.v1.AckReplyConsumer;
import com.google.cloud.pubsub.spi.v1.MessageReceiver;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

/**
 * Created by ben on 13/06/2017.
 */
public class HdmReceiver implements MessageReceiver {

	/**
	 * Called when a message is received by the subscriber. The implementation must arrange for {@link
	 * AckReplyConsumer#ack()} or {@link
	 * AckReplyConsumer#nack()} to be called after processing the {@code message}.
	 *
	 * <p>This {@code MessageReceiver} passes all messages to a {@code BlockingQueue}.
	 * This method can be called concurrently from multiple threads,
	 * so it is important that the queue be thread-safe.
	 *
	 * This example is for illustration. Implementations may directly process messages
	 * instead of sending them to queues.
	 * <pre> {@code
	 * MessageReceiver receiver = new MessageReceiver() {
	 *   public void receiveMessage(final PubsubMessage message, final AckReplyConsumer consumer) {
	 *     if (blockingQueue.offer(message)) {
	 *       consumer.ack();
	 *     } else {
	 *       consumer.nack();
	 *     }
	 *   }
	 * };
	 * }</pre>
	 */

	@Override
	public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
		ByteString data = message.getData();
		System.out.println(data);
		consumer.ack();
	}
}
