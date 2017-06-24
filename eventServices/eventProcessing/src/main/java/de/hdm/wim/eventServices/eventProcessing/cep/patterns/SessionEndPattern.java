package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.StayAliveEvent;
import de.hdm.wim.sharedLib.events.UserInactiveEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.PatternTimeoutFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Either;

import java.util.Map;

/**
 * Created by nilsb on 24.06.2017.
 */
public class SessionEndPattern {public void run(StreamExecutionEnvironment env, DataStream<IEvent> kStream) throws Exception {

	//Test Pattern for false User Feedback
	//This Pattern triggers when a User clicks on a Feedback mutiple times.
	Pattern<IEvent, ?> sessionEnd2 = Pattern
		.<IEvent>begin("first")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_JOINED_SESSION))
		.followedBy("second")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_JOINED_SESSION ))
		.followedBy("third")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_LEFT_SESSION ))
		.or(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.EVENT)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_INACTIVE))
		.followedBy("fourth")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_LEFT_SESSION ))
		.or(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.EVENT)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_INACTIVE));

	Pattern<IEvent, ?> sessionEnd3 = Pattern
		.<IEvent>begin("first")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_JOINED_SESSION))
		.followedBy("second")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_JOINED_SESSION ));

	PatternStream<IEvent> patternStream2 = CEP.pattern(kStream, sessionEnd2);

	DataStream<Either<UserInactiveEvent, StayAliveEvent>> userInactiveEventDataStream = patternStream2.select(
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

}
}
