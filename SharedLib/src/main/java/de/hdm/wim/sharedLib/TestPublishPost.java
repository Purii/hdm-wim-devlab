package de.hdm.wim.sharedLib;

import de.hdm.wim.sharedLib.Constants.Topic;
import de.hdm.wim.sharedLib.classes.Message;
import de.hdm.wim.sharedLib.helper.PublishHelper;
import java.util.concurrent.TimeUnit;

/**
 * Created by ben on 5/06/2017.
 *
 * Publishes the generated messages via POST
 */
public class TestPublishPost {
	private static final long _messagePeriodSeconds = 1;

	public static void main(String[] args) throws Exception {
		int i 				= 1;
		PublishHelper ph 	= new PublishHelper();

		while (i <= 2) {
			Message message   = Message.generate("blubb_"+i, Topic.pushTest);

			ph.Publish(message);

			Thread.sleep(TimeUnit.SECONDS.toMillis(_messagePeriodSeconds));

			i++;
		}
	}
}
