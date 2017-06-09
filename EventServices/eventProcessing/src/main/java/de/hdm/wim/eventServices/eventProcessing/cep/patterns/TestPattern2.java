package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.eventServices.eventProcessing.cep.events.MessageEvent;
import de.hdm.wim.eventServices.eventProcessing.cep.events.TokenEvent;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.classes.PubSubMessage;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * Created by nilsb on 09.06.2017.
 */
public class TestPattern2 {

	/**
	 * Run.
	 *
	 * @param env           the env
	 * @param psmStream 	the PubSubMessage stream
	 */
	public void run(StreamExecutionEnvironment env, DataStream<PubSubMessage> psmStream ) {


		//Test Pattern for successful User Request
		//Successful means Offer Event follows Request Event and Feedback Event follows Offer Event within 2 minutes
		Pattern<PubSubMessage, ?> successfulRequest = Pattern
			.<PubSubMessage>begin("User Request")
			.where(evt -> evt.getAttributes().containsValue(Constants.EventSource.SPEECH_TOKENIZATION)
					&& evt.getAttributes().containsValue(Constants.EventType.REQUEST)
			)
			.followedBy("Offer Event")
			.where(evt -> evt.getAttributes().containsValue(Constants.EventSource.SEMANTIC_REPRESENTATION)
					&& evt.getAttributes().containsValue(Constants.EventType.OFFER)
			)
			.followedBy("Feedback Event")
			.within(Time.minutes(10))
			.where(evt -> evt.getAttributes().containsValue(Constants.EventSource.USER_INTERFACE)
					&& evt.getAttributes().containsValue(Constants.EventType.FEEDBACK)
			);


		// Create a pattern stream from our project pattern
		PatternStream<PubSubMessage> successfulRequestStream = CEP.pattern(
			psmStream,
			successfulRequest);

		// Generate ProjectEvents for each matched project pattern
		DataStream<PubSubMessage> result = successfulRequestStream.select(
			pattern -> {
				System.out.println("################");
				System.out.println("Der unwahrscheinliche Fall ist tatsächlich eingetreten");
				System.out.println("################");
				return pattern.get("User Request");
			}
		);
	}
}
