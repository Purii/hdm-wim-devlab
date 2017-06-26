package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.DocumentContextPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.DocumentInformationEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * The type Doc context job.
 *
 * @author Christian Schneider
 * @createdOn 25.06.2017
 */
public class DocContextJob {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @throws Exception the exception
	 */
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

	/**
	 * The type Doc context splitter.
	 */
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
