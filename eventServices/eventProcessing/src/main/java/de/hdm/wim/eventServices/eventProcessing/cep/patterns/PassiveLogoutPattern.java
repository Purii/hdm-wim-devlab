package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.UserInactiveEvent;
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
 * @createdOn 22.06.2017
 */
public class PassiveLogoutPattern {

	/**
	 * Run.
	 *
	 * @param env           the env
	 * @param heartbeats 	the Tuple of user id and count of heartbeats within 15 seconds
	 */
/*	public void run(StreamExecutionEnvironment env, DataStream<IEvent> kStream) throws Exception {

		//Test Pattern for false User Feedback
		//This Pattern triggers when a User clicks on a Feedback mutiple times.
		Pattern<IEvent, ?> passiveLogout = Pattern
			.<IEvent>begin("first")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.STAYALIVE))
			.followedBy("second")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.STAYALIVE ));


		PatternStream<IEvent> patternStream = CEP.pattern(kStream, passiveLogout);

		DataStream<Either<UserInactiveEvent, StayAliveEvent>> userInactiveEventDataStream = patternStream.select(
			new PatternTimeoutFunction<IEvent, UserInactiveEvent>() {
				@Override
				public UserInactiveEvent timeout(Map<String, IEvent> pattern, long timeoutTimestamp) throws Exception {
					UserInactiveEvent uinevt = new UserInactiveEvent();
					uinevt.setEventSource(Constants.PubSub.EventSource.EVENT);
					uinevt.setUserId(pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.USER_ID));
					return uinevt;
				}
			},
			new PatternSelectFunction<IEvent, StayAliveEvent>() {
				@Override
				public StayAliveEvent select(Map<String, IEvent> pattern) throws Exception {
					StayAliveEvent saevt = new StayAliveEvent();
					saevt.setEventSource(Constants.PubSub.EventSource.EVENT);
					saevt.setUserId(pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.USER_ID));
					return saevt;
				}
			}
		);

		PublishHelper ph = new PublishHelper(false);
		//	ph.Publish((IEvent) highlyRelevantDoc, Constants.PubSub.Topic.INSIGHTS);

	} */

	public void run(StreamExecutionEnvironment env, DataStream<Tuple2<String, Integer>> heartbeats){
		Pattern<Tuple2<String, Integer>, ?> passiveLogout = Pattern
			.<Tuple2<String, Integer>>begin("first")
			.where ( tpl -> tpl.getField(1).equals(0)||tpl.getField(1).equals(1));

		PatternStream<Tuple2<String, Integer>> inactiveUserStream = CEP.pattern(heartbeats, passiveLogout);

		DataStream<UserInactiveEvent> UserInactiveEventDataStream = inactiveUserStream.select(new PatternSelectFunction<Tuple2<String, Integer>, UserInactiveEvent>() {
			@Override
			public UserInactiveEvent select(Map<String, Tuple2<String, Integer>> pattern) throws Exception {
				UserInactiveEvent uinevt = new UserInactiveEvent();
				uinevt.setUserId(pattern.get("first").getField(0).toString());
				uinevt.setEventSource(Constants.PubSub.EventSource.EVENT);
				uinevt.setData("somedata");
				System.out.println("User " + uinevt.getUserId() + " is inactive");
				return uinevt;
			}
		});
		UserInactiveEventDataStream.print();
	}
}
