package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.DocumentContextPattern;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.HighlyRelevantDocumentPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.DocumentInformationEvent;
import de.hdm.wim.sharedLib.events.SuccessfulFeedbackEvent;
import de.hdm.wim.sharedLib.events.UserInformationEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.log4j.Logger;

/**
 * Created by Chris on 25.06.2017.
 */
public class DocContextJob {

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		env.setParallelism(1);

		DataStream<DocumentInformationEvent> docStream = env
			.socketTextStream("localhost", 9999)
			.flatMap(new DocContextSplitter());

		DocumentContextPattern documentContextPattern = new DocumentContextPattern();
		documentContextPattern.run(env, docStream);

		env.execute("CEP chat stream job");
	}

	public static class DocContextSplitter implements FlatMapFunction<String, DocumentInformationEvent> {

		@Override
		public void flatMap(String value, Collector<DocumentInformationEvent> out) throws Exception {
			DocumentInformationEvent evt = new GsonBuilder().create().fromJson(value, DocumentInformationEvent.class);
			if(evt.getEventType().equals(Constants.PubSub.EventType.DOCUMENT_INFO)) {
				out.collect(evt);
			}
		}
	}
}
