package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.PassiveLogoutPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.StayAliveEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * The type Passive logout job.
 *
 * @author Christian Schneider
 * @createdOn 25.06.2017
 */
public class PassiveLogoutJob {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		env.setParallelism(1);

		DataStream<Tuple2<String, Integer>> someStream = env
			.socketTextStream("localhost", 9999)
			.flatMap(new SomeSplitter())
			.keyBy(0)
			.timeWindow(Time.seconds(15), Time.seconds(3))
			.sum(1);

		PassiveLogoutPattern passiveLogoutPattern = new PassiveLogoutPattern();
		passiveLogoutPattern.run(env, someStream);

		env.execute("CEP chat stream job");

	}

	/**
	 * The type Some splitter.
	 */
	public static class SomeSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
			StayAliveEvent evt = new GsonBuilder().create().fromJson(value, StayAliveEvent.class);
			if(evt.getEventType().equals(Constants.PubSub.EventType.STAYALIVE)){
				Tuple2<String, Integer> tuple = new Tuple2<String, Integer>(evt.getAttributes().get(Constants.PubSub.AttributeKey.USER_ID),1);
				out.collect(tuple);
			}
		}
	}
}
