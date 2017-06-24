package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.DocumentHighlyRelevantEvent;
import de.hdm.wim.sharedLib.events.FeedbackEvent;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.Map;

/**
 * Created by nilsb on 13.06.2017.
 */
public class HighlyRelevantDocumentPattern {

	/**
	 * Run.
	 *
	 * @param env           the env
	 * @param psmStream 	the IEvent stream
	 */
	public void run(StreamExecutionEnvironment env, DataStream<IEvent> psmStream ) throws Exception {

		//Test Pattern for false User Feedback
		//This Pattern triggers when a User clicks on a Feedback mutiple times.
		Pattern<IEvent, ?> falseUserFeedback = Pattern
			.<IEvent>begin("first")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.SUCCESSFUL_FEEDBACK))
			.followedBy("second")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.SUCCESSFUL_FEEDBACK))
			.within(Time.seconds(5));

		PatternStream<IEvent> patternStream = CEP.pattern(psmStream, falseUserFeedback);

		DataStream<DocumentHighlyRelevantEvent> highlyRelevantDoc = patternStream.select(new PatternSelectFunction<IEvent, DocumentHighlyRelevantEvent>() {
			@Override
			public DocumentHighlyRelevantEvent select(Map<String, IEvent> pattern) throws Exception {
				String userId1 = pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.USER_ID);
				String userId2 = pattern.get("second").getAttributes().get(Constants.PubSub.AttributeKey.USER_ID);
				String docId1 = pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_ID);
				String docId2 = pattern.get("second").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_ID);
				if(userId1.equals(userId2) && docId1.equals(docId2)){
					DocumentHighlyRelevantEvent dhrevt = new DocumentHighlyRelevantEvent();
					dhrevt.setUserId(userId1);
					dhrevt.setDocumentId(docId1);
					System.out.println("Document "+ docId1 + " is highly relevant");
					return dhrevt;
				}
				return null;
			}
		});
		PublishHelper ph = new PublishHelper(false);
	//	ph.Publish((IEvent) highlyRelevantDoc, Constants.PubSub.Topic.INSIGHTS);

	}
}

