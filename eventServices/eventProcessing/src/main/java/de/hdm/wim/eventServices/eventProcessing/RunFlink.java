package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.DocumentContextPattern;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.HighlyRelevantDocumentPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.StayAliveEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import com.google.pubsub.v1.PubsubMessage;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

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

			KeyedStream<StayAliveEvent, String> keyedStayAliveEventDataStream = stayAliveEventDataStream
				.keyBy(new KeySelector<StayAliveEvent, String>() {
					@Override
					public String getKey(StayAliveEvent value) throws Exception {
						return value.getAttributes().get(Constants.PubSub.AttributeKey.USER_ID);
					}
				});

	//		WindowedStream<StayAliveEvent, String, TimeWindow> ping = keyedStayAliveEventDataStream
	//		.timeWindow(Time.seconds(10));

	/*		DataStream<Tuple2<String, Long>> ping = keyedStayAliveEventDataStream
				.timeWindow(Time.seconds(10))
				.apply(new WindowFunction<StayAliveEvent, StayAliveEvent, String, TimeWindow>() {
					@Override
					public void apply(String s, TimeWindow window, Iterable<StayAliveEvent> input, Collector<StayAliveEvent> out) throws Exception {
						long count = 0;
						for(StayAliveEvent in: input){
							count++;
							out.collect(new Tuple2<String, Long>(in.getAttributes().get(Constants.PubSub.AttributeKey.USER_ID),count));
						}
					}
				}); */


			//		DataStream<IEvent> eventStream = messageStream;

			HighlyRelevantDocumentPattern falseUserFeedback = new HighlyRelevantDocumentPattern();
			falseUserFeedback.run(env, eventStream);

	//		PassiveLogoutPattern passiveLogoutPattern = new PassiveLogoutPattern();
	//		passiveLogoutPattern.run(env, keyedStayAliveEventDataStream);

			DocumentContextPattern documentContextPattern = new DocumentContextPattern();
			documentContextPattern.run(env, eventStream);



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
			IEvent evt = helper.convertToIEvent(msg);
			out.collect(evt);
		}
	}
	public static class StayAliveEventSplitter implements FlatMapFunction<String, StayAliveEvent> {
		@Override
		public void flatMap(String value, Collector<StayAliveEvent> out) throws Exception {
			PubsubMessage msg = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			Helper helper = new Helper();
			StayAliveEvent evt = (StayAliveEvent) helper.convertToIEvent(msg);
			out.collect(evt);
		}
	}
}