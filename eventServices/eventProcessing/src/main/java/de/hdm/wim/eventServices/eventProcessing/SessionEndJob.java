package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.SessionEndPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.UserJoinedSessionEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.log4j.Logger;

/**
 * Created by Chris on 25.06.2017.
 */
public class SessionEndJob {

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
		env.setParallelism(1);

		DataStream<Tuple2<String, Integer>> usersInSession = env
			.socketTextStream("localhost", 9999)
			.flatMap(new UserPerSessionSplitter())
			.keyBy(0)
			.sum(1);

		SessionEndPattern sessionEndPattern = new SessionEndPattern();
		sessionEndPattern.run(env, usersInSession);

		env.execute("CEP chat stream job");

	}

	public static class UserPerSessionSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

	@Override
	public void flatMap(String value, Collector<Tuple2<String,Integer>> out) throws Exception {
		UserJoinedSessionEvent evt = new GsonBuilder().create().fromJson(value, UserJoinedSessionEvent.class);
		if(evt.getEventType().equals(Constants.PubSub.EventType.USER_JOINED_SESSION)){
			out.collect(new Tuple2<String, Integer>(evt.getAttributes().get(Constants.PubSub.AttributeKey.SESSION_ID),1));
		}
		if(evt.getEventType().equals(Constants.PubSub.EventType.USER_LEFT_SESSION)){
			out.collect(new Tuple2<String, Integer>(evt.getAttributes().get(Constants.PubSub.AttributeKey.SESSION_ID),-1));
		}
	}
}}
