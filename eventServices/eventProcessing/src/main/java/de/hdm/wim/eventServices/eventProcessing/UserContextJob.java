package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.UserContextPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.UserInformationEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author Christian Schneider
 * @createdOn 25.06.2017
 */
public class UserContextJob {

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		env.setParallelism(1);

		DataStream<UserInformationEvent> userStream = env
			.socketTextStream("localhost", 9999)
			.flatMap(new UserContextSplitter());

		UserContextPattern userContextPattern = new UserContextPattern();
		userContextPattern.run(env, userStream);

		env.execute("CEP chat stream job");
	}

	public static class UserContextSplitter implements FlatMapFunction<String, UserInformationEvent> {

		@Override
		public void flatMap(String value, Collector<UserInformationEvent> out) throws Exception {
			UserInformationEvent evt = new GsonBuilder().create().fromJson(value, UserInformationEvent.class);
			if(evt.getEventType().equals(Constants.PubSub.EventType.USER_INFO)) {
				out.collect(evt);
			}
		}
	}
}
