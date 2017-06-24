package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.eventServices.eventProcessing.helper.ActiveUsers;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.SessionEndEvent;
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
public class SessionEndPattern2 {public void run(StreamExecutionEnvironment env, DataStream<IEvent> kStream) throws Exception {

	//Test Pattern for false User Feedback
	//This Pattern triggers when a User clicks on a Feedback mutiple times.
	Pattern<IEvent, ?> userJoined = Pattern
		.<IEvent>begin("first")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_JOINED_SESSION));

	Pattern<IEvent, ?> userLeft = Pattern
		.<IEvent>begin("first")
		.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.USER_INTERFACE)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_LEFT_SESSION ))
		.or(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.EVENT)
			&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_INACTIVE));

	PatternStream<IEvent> userJoinedStream = CEP.pattern(kStream, userJoined);
	PatternStream<IEvent> userLeftStream = CEP.pattern(kStream, userLeft);


	DataStream<IEvent> userJoinedDataStream = userJoinedStream.select(
		new PatternSelectFunction<IEvent, IEvent>() {
			@Override
			public IEvent select(Map<String, IEvent> pattern) throws Exception {
				ActiveUsers au = new ActiveUsers();
				au.userJoinedSession();
				IEvent evt = (IEvent) new StayAliveEvent();
				return evt;
			}
		}
	);
	ActiveUsers au = new ActiveUsers();
	DataStream<IEvent> userLeftDataStream = userLeftStream.select(
		new PatternSelectFunction<IEvent, IEvent>() {
			@Override
			public IEvent select(Map<String, IEvent> pattern) throws Exception {

				au.userLeftSession();
				int activeUsers = au.getActiveUsers();
				au.setSessionId(pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.SESSION_ID));

				if(activeUsers<=0){
					SessionEndEvent sessionEndEvent = new SessionEndEvent();
					sessionEndEvent.setSessionId(pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.SESSION_ID));
					return sessionEndEvent;
				}
				IEvent evt = (IEvent) new StayAliveEvent();
				return evt;
			}
		}
	);

	userLeftDataStream.print();
	if(au.getActiveUsers()<=0) {
		SessionEndEvent sessionEndEvent = new SessionEndEvent();
		sessionEndEvent.setSessionId(au.getSessionId());
		PublishHelper ph = new PublishHelper(false);
		ph.Publish(sessionEndEvent, Constants.PubSub.Topic.CEP_SESSIONINSIGHTS);
	}

}
}
