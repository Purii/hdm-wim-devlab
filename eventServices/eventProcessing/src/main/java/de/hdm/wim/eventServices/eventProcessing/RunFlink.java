package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.FalseUserFeedback;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.TestPattern2;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.StayAliveEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import com.google.pubsub.v1.PubsubMessage;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Map;

/**
 * Created by Ben on 30.05.2017.
 */
public class RunFlink {

        /**
         * The entry point of application.
         *
         * @param args the input arguments
         * @throws Exception the exception
         */
        public static void main(String[] args) throws Exception {

            final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
            env.setParallelism(1); // set Parallelism to 1 Task, to get chat order right
/*
            MessageEventSource evtSrc 	= new MessageEventSource();
            TokenSource tknSrc 			= new TokenSource();

            // create a DataStream of MessageEvent from our source
            DataStream<MessageEvent> messageStream = env
                    .addSource(evtSrc)
					.returns(MessageEvent.class);

            // create a DataStream of String from our tokenSource
            DataStream<TokenEvent> tokenStream = env
                    .addSource(tknSrc)
					.returns(TokenEvent.class);

            // run the test pattern
//            TestPattern testPattern = new TestPattern();
//            testPattern.run(env,messageStream);

            // run the sender pattern
//            SenderPattern senderPattern = new SenderPattern();
//            senderPattern.run(env,messageStream);

            // run the relation to date pattern
            RelationToDatePattern relationToDatePattern = new RelationToDatePattern();
            relationToDatePattern.run(env, tokenStream);
*/

			DataStream<PubsubMessage> messageStream = env
				.socketTextStream("localhost", 9999)
				.flatMap(new Splitter());

			DataStream<IEvent> eventStream = env
				.socketTextStream("localhost", 9999)
				.flatMap(new EventSplitter());

			DataStream<StayAliveEvent> stayAliveEventDataStream = env
				.socketTextStream("localhost", 9999)
				.flatMap(new StayAliveEventSplitter());

	//		DataStream<IEvent> eventStream = messageStream;

			FalseUserFeedback falseUserFeedback = new FalseUserFeedback();
			falseUserFeedback.run(env, eventStream);

            // print message stream

            //tokenStream.print();

            env.execute("CEP chat stream job");
		}
	public static class Splitter implements FlatMapFunction<String, PubsubMessage> {
		@Override
		public void flatMap(String value, Collector<PubsubMessage> out) throws Exception {
			PubsubMessage evt = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			out.collect(evt);
		}
	}
	public static class EventSplitter implements FlatMapFunction<String, IEvent> {
		@Override
		public void flatMap(String value, Collector<IEvent> out) throws Exception {
			PubsubMessage msg = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			Helper helper = new Helper();
			IEvent evt = helper.convertPubsubMessageToIEvent(msg);
			out.collect(evt);
		}
	}
	public static class StayAliveEventSplitter implements FlatMapFunction<String, StayAliveEvent> {
		@Override
		public void flatMap(String value, Collector<StayAliveEvent> out) throws Exception {
			PubsubMessage msg = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			Helper helper = new Helper();
			StayAliveEvent evt = (StayAliveEvent) helper.convertPubsubMessageToIEvent(msg);
			out.collect(evt);
		}
	}
}