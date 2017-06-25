package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.HighlyRelevantDocumentPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.SuccessfulFeedbackEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.log4j.Logger;

/**
 * Created by Chris on 25.06.2017.
 */
public class DocRelevantJob {

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		env.setParallelism(1);


		DataStream<SuccessfulFeedbackEvent> feedbackStream = env
			.socketTextStream("localhost", 9999)
			.flatMap(new DocRelevantSplitter());


		HighlyRelevantDocumentPattern highlyRelevantPattern = new HighlyRelevantDocumentPattern();
		highlyRelevantPattern.run(env, feedbackStream);

		env.execute("CEP chat stream job");
	}

	public static class DocRelevantSplitter implements FlatMapFunction<String, SuccessfulFeedbackEvent> {

		@Override
		public void flatMap(String value, Collector<SuccessfulFeedbackEvent> out) throws Exception {
			SuccessfulFeedbackEvent evt = new GsonBuilder().create().fromJson(value, SuccessfulFeedbackEvent.class);
			if(evt.getEventType().equals(Constants.PubSub.EventType.SUCCESSFUL_FEEDBACK)) {
				out.collect(evt);
			}
		}
	}
}
