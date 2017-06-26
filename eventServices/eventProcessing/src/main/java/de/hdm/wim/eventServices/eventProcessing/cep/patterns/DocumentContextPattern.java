package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.DocumentContextEvent;
import de.hdm.wim.sharedLib.events.DocumentInformationEvent;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import java.util.Map;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author Nils Bachmann
 * @createdOn 23.06.2017
 */
public class DocumentContextPattern {

	/**
	 * Run.
	 *
	 * @param env           the env
	 * @param psmStream 	the IEvent stream
	 */
	public void run(StreamExecutionEnvironment env, DataStream<DocumentInformationEvent> psmStream) throws Exception {
		//Test Pattern for false User Feedback
		//This Pattern triggers when a User clicks on a Feedback mutiple times.
		Pattern<DocumentInformationEvent, ?> docContextPattern = Pattern
			.<DocumentInformationEvent>begin("first")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.DOCUMENT_INFO))
			.followedBy("second")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.DOCUMENT_INFO ))
			.followedBy("third")
			.where(evt -> evt.getAttributes().containsValue(Constants.PubSub.EventSource.SEMANTIC_REPRESENTATION)
				&& evt.getAttributes().containsValue(Constants.PubSub.EventType.DOCUMENT_INFO));


		PatternStream<DocumentInformationEvent> patternStream = CEP.pattern(psmStream, docContextPattern);

		DataStream<DocumentContextEvent> documentContextEventDataStream = patternStream.select(new PatternSelectFunction<DocumentInformationEvent, DocumentContextEvent>() {
			@Override
			public DocumentContextEvent select(Map<String, DocumentInformationEvent> pattern) throws Exception {
				String project1 = pattern.get("first").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT);
				String project2 = pattern.get("second").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT);
				String project3 = pattern.get("third").getAttributes().get(Constants.PubSub.AttributeKey.DOCUMENT_BELONGS_TO_PROJECT);
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
		//	ph.Publish((IEvent) documentContextEventDataStream, Constants.PubSub.Topic.INSIGHTS);

	}
}
