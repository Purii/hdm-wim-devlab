package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.UserContextEvent;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.UserInactiveEvent;
import de.hdm.wim.sharedLib.events.UserInformationEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.hadoop.security.UserGroupInformation;

import java.util.Map;

/**
 * Created by chris on 24.06.2017.
 */
public class UserContextPattern {

	/**
	 * Run.
	 *
	 * @param env           the env
	 * @param psmStream 	the IEvent stream
	 */
	public void run(StreamExecutionEnvironment env, DataStream<UserInformationEvent> psmStream) throws Exception {
		//Test Pattern for false User Feedback
		//This Pattern triggers when a User clicks on a Feedback mutiple times.
		Pattern<UserInformationEvent, ?> userContext = Pattern
			.<UserInformationEvent>begin("first")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_INFO))
			.followedBy("second")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_INFO ))
			.followedBy("third")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.USER_INFO));


		PatternStream<UserInformationEvent> patternStream = CEP.pattern(psmStream, userContext);

		DataStream<UserContextEvent> userContextEventDataStream = patternStream.select(new PatternSelectFunction<UserInformationEvent, UserContextEvent>() {
			@Override
			public UserContextEvent select(Map<String, UserInformationEvent> pattern) throws Exception {
				String project1 = pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.USER_WORKS_ON_PROJECTS);
				String project2 = pattern.get("second").getAttributes().get(Constants.PubSub.AttributeKey.USER_WORKS_ON_PROJECTS);
				String project3 = pattern.get("third").getAttributes().get(Constants.PubSub.AttributeKey.USER_WORKS_ON_PROJECTS);
				System.out.println(project1+"  "+ project2 + "   "+ project3);
				if(project1.equals(project2) && project1.equals(project3)){
					UserContextEvent ucevt = new UserContextEvent();
					ucevt.setContext(project1);
					ucevt.setEventSource(Constants.PubSub.EventSource.EVENT);
					System.out.println("Project " + project1 + " seems to be interesting");
					return ucevt;
				}
				return null;
			}
		});
		PublishHelper ph = new PublishHelper(false);
		userContextEventDataStream.print();
		//	ph.Publish((IEvent) documentContextEventDataStream, Constants.PubSub.Topic.INSIGHTS);

	}
}