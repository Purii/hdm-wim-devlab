package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import com.google.pubsub.v1.PubsubMessage;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.PassiveLogoutPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.StayAliveEvent;
import de.hdm.wim.sharedLib.events.SuccessfulFeedbackEvent;
import de.hdm.wim.sharedLib.events.UserJoinedSessionEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.log4j.Logger;

/**
 * The type Run flink.
 *
 * @author Benedikt Benz
 * @createdOn 30.05.2017
 */
public class RunFlink {

	private static final Logger LOGGER 				 = Logger.getLogger(RunFlink.class);


	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {

            final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
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

	/*		DataStream<PubsubMessage> messageStream = env
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
		*/


		/*	DataStream<String> anyStream = env
				.socketTextStream("localhost", 9999);
			anyStream.print();*/

			DataStream<Tuple2<String, Integer>> someStream = env
				.socketTextStream("localhost", 9999)
				.flatMap(new SomeSplitter())
				.keyBy(0)
				.timeWindow(Time.seconds(15), Time.seconds(3))
				.sum(1);



			/*DataStream<Tuple2<String,Integer>> usersInSession = env
				.socketTextStream("localhost", 9999)
				.flatMap(new UserPerSessionSplitter())
				.keyBy(0)
				.sum(1);

			usersInSession.print();*/


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

			/*HighlyRelevantDocumentPattern falseUserFeedback = new HighlyRelevantDocumentPattern();
			falseUserFeedback.run(env, eventStream);*/

	//		PassiveLogoutPattern passiveLogoutPattern = new PassiveLogoutPattern();
	//		passiveLogoutPattern.run(env, keyedStayAliveEventDataStream);



			PassiveLogoutPattern passiveLogoutPattern = new PassiveLogoutPattern();
			passiveLogoutPattern.run(env, someStream);

	/*		DataStream<SuccessfulFeedbackEvent> feedbackStream = env
				.socketTextStream("localhost", 9999)
				.flatMap(new DocRelevantSplitter());


			HighlyRelevantDocumentPattern highlyRelevantPattern = new HighlyRelevantDocumentPattern();
			highlyRelevantPattern.run(env, feedbackStream);*/


			/*SessionEndPattern sessionEndPattern = new SessionEndPattern();
			sessionEndPattern.run(env, usersInSession);*/

			/*UserContextPattern userContextPattern = new UserContextPattern();
			userContextPattern.run(env, userContext);*/

			// print message stream

            //tokenStream.print();

            env.execute("CEP chat stream job");
		}

	/**
	 * The type Splitter.
	 */
	public static class Splitter implements FlatMapFunction<String, PubsubMessage> {

		@Override
		public void flatMap(String value, Collector<PubsubMessage> out) throws Exception {
			PubsubMessage evt = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			out.collect(evt);
		}
	}

	/**
	 * The type Event splitter.
	 */
	public static class EventSplitter implements FlatMapFunction<String, IEvent> {

		@Override
		public void flatMap(String value, Collector<IEvent> out) throws Exception {
			PubsubMessage msg = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			Helper helper = new Helper();
			IEvent evt = helper.convertToIEvent(msg);

			switch(evt.getEventType()) {
				case Constants.PubSub.EventType.ADDITIONAL_USER_INFO:
					out.collect(evt);
				case Constants.PubSub.EventType.ALL_COMPANIES:
					out.collect(evt);
				case Constants.PubSub.EventType.ALL_DEPARTMENTS:
					out.collect(evt);
				case Constants.PubSub.EventType.ALL_PROJECTROLES:
					out.collect(evt);
				case Constants.PubSub.EventType.ALL_PROJECTS:
					out.collect(evt);
				case Constants.PubSub.EventType.DEPARTMENT_INFO:
					out.collect(evt);
				case Constants.PubSub.EventType.DOCUMENT_CALL:
					out.collect(evt);
				case Constants.PubSub.EventType.DOCUMENT_CONTEXT:
					out.collect(evt);
				case Constants.PubSub.EventType.DOCUMENT_HIGHLY_RELEVANT:
					out.collect(evt);
				case Constants.PubSub.EventType.DOCUMENT_INFO:
					out.collect(evt);
				case Constants.PubSub.EventType.FEEDBACK:
					out.collect(evt);
				case Constants.PubSub.EventType.INFO_TOALL_DOCUMENTS:
					out.collect(evt);
				case Constants.PubSub.EventType.LEARN:
					out.collect(evt);
				case Constants.PubSub.EventType.OFFER:
					out.collect(evt);
				case Constants.PubSub.EventType.PROJECT_INFO:
					out.collect(evt);
				case Constants.PubSub.EventType.SESSION_END:
					out.collect(evt);
				case Constants.PubSub.EventType.SESSION_START:
					out.collect(evt);
				case Constants.PubSub.EventType.STAYALIVE:
					out.collect(evt);
				case Constants.PubSub.EventType.SUCCESSFUL_FEEDBACK:
					out.collect(evt);
				case Constants.PubSub.EventType.TOKEN:
					out.collect(evt);
				case Constants.PubSub.EventType.USER_CONTEXT:
					out.collect(evt);
				case Constants.PubSub.EventType.USER_INACTIVE:
					out.collect(evt);
				case Constants.PubSub.EventType.USER_INFO:
					out.collect(evt);
				case Constants.PubSub.EventType.USER_JOINED_SESSION:
					out.collect(evt);
				case Constants.PubSub.EventType.USER_LEFT_SESSION:
					out.collect(evt);
				case Constants.PubSub.EventType.USER_LOGIN:
					out.collect(evt);
				case Constants.PubSub.EventType.USER_START:
					out.collect(evt);
				default: out.collect(evt);
			}
		}
	}

	/**
	 * The type Stay alive event splitter.
	 */
	public static class StayAliveEventSplitter implements FlatMapFunction<String, StayAliveEvent> {

		@Override
		public void flatMap(String value, Collector<StayAliveEvent> out) throws Exception {
			PubsubMessage msg = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			Helper helper = new Helper();
			StayAliveEvent evt = (StayAliveEvent) helper.convertToIEvent(msg);
			out.collect(evt);
		}
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

	/**
	 * The type Doc relevant splitter.
	 */
	public static class DocRelevantSplitter implements FlatMapFunction<String, SuccessfulFeedbackEvent> {

		@Override
		public void flatMap(String value, Collector<SuccessfulFeedbackEvent> out) throws Exception {
			SuccessfulFeedbackEvent evt = new GsonBuilder().create().fromJson(value, SuccessfulFeedbackEvent.class);
			if(evt.getEventType().equals(Constants.PubSub.EventType.SUCCESSFUL_FEEDBACK)) {
				out.collect(evt);
			}
		}
	}

	/**
	 * The type User per session splitter.
	 */
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
	}
}