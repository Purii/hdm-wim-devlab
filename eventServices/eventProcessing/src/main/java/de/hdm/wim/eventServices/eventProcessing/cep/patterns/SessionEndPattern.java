package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.SessionEndEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import java.util.Map;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author Nils Bachmann
 * @createdOn 24.06.2017
 *
 */
public class SessionEndPattern {

	/**
	 * Run.
	 *
	 * @param env           	the env
	 * @param userCountStream 	the Tuple of session id and count of users in that session
	 */
	public void run(StreamExecutionEnvironment env, DataStream<Tuple2<String, Integer>> userCountStream) throws Exception {

	//Test Pattern for false User Feedback
	//This Pattern triggers when a User clicks on a Feedback mutiple times.
	Pattern<Tuple2<String, Integer>, ?> sessionEnd = Pattern
		.<Tuple2<String, Integer>>begin("first")
		.where(tpl -> tpl.getField(1).equals(0));

	PatternStream<Tuple2<String, Integer>> sessionEndStream = CEP.pattern(userCountStream, sessionEnd);

	DataStream<SessionEndEvent> SessionEndEventDataStream = sessionEndStream.select(new PatternSelectFunction<Tuple2<String, Integer>, SessionEndEvent>() {
		@Override
		public SessionEndEvent select(Map<String, Tuple2<String, Integer>> pattern) throws Exception {
			SessionEndEvent ssEndEvt = new SessionEndEvent();
			ssEndEvt.setSessionId(pattern.get("first").getField(0).toString());
			ssEndEvt.setEventSource(Constants.PubSub.EventSource.EVENT);
			ssEndEvt.setData("anyting");
			System.out.println("Session " + ssEndEvt.getSessionId() + " ended!");
			return ssEndEvt;
		}
	});
	SessionEndEventDataStream.print();
	PublishHelper ph = new PublishHelper(false);
	//	ph.Publish((IEvent) highlyRelevantDoc, Constants.PubSub.Topic.INSIGHTS);

}
}
