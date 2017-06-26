package de.hdm.wim.sharedLib.testing;

import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import de.hdm.wim.sharedLib.helper.Helper;

/**
 * TestEventConverter
 * this class is only for testing purposes!
 *
 * @author Benedikt Benz
 * @createdOn 20.06.2017
 * @deprecated
 */
public class TestEventConverter {

	public static void main(String[] args) throws Exception {

		String payload = "test";
		ByteString bs = ByteString.copyFromUtf8(payload);
		PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(bs).build();

		Helper helper = new Helper();
		//LearnEvent le = helper.eventConverter(pubsubMessage);

	}
}