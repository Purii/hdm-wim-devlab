package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.DocumentContextEvent;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.UserInactiveEvent;
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
 * Created by nilsb on 23.06.2017.
 */
public class DocumentContextPattern {

	/**
	 * Run.
	 *
	 * @param env           the env
	 * @param psmStream 	the IEvent stream
	 */
	public void run(StreamExecutionEnvironment env, DataStream<IEvent> psmStream) throws Exception {
		//Test Pattern for false User Feedback
		//This Pattern triggers when a User clicks on a Feedback mutiple times.
		Pattern<IEvent, ?> docContext = Pattern
			.<IEvent>begin("first")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.DOCUMENT_INFO))
			.followedBy("second")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.DOCUMENT_INFO ))
			.followedBy("third")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.DOCUMENT_INFO));


		PatternStream<IEvent> patternStream = CEP.pattern(psmStream, docContext);

		DataStream<DocumentContextEvent> documentContextEventDataStream = patternStream.select(new PatternSelectFunction<IEvent, DocumentContextEvent>() {
			@Override
			public DocumentContextEvent select(Map<String, IEvent> pattern) throws Exception {
				String project1 = pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT);
				String project2 = pattern.get("second").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT);
				String project3 = pattern.get("second").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT);
				System.out.println(project1+"  "+ project2 + "   "+ project3);
				if(project1.equals(project2) && project1.equals(project3)){
					DocumentContextEvent dcevt = new DocumentContextEvent();
					dcevt.setContext(project1);
					dcevt.setEventSource(Constants.PubSub.EventSource.EVENT);
					System.out.println("Project " + project1 + " seems to be interesting");
					return dcevt;
				}
				return null;
			}
		});
		PublishHelper ph = new PublishHelper(false);
		documentContextEventDataStream.print();
		//	ph.Publish((IEvent) documentContextEventDataStream, Constants.PubSub.Topic.INSIGHTS);

	}
}
