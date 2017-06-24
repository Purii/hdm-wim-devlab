package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.DocumentContextPattern;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.HighlyRelevantDocumentPattern;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.events.*;
import de.hdm.wim.sharedLib.helper.Helper;
import com.google.pubsub.v1.PubsubMessage;
import jdk.nashorn.internal.parser.Token;
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

			switch(evt.getEventType()) {
				case Constants.PubSub.EventType.ADDITIONAL_USER_INFO:
					out.collect((AdditionalUserInformationEvent) evt);
				case Constants.PubSub.EventType.ALL_COMPANIES:
					out.collect((AllCompaniesEvent) evt);
				case Constants.PubSub.EventType.ALL_DEPARTMENTS:
					out.collect((AllDepartmentsEvent) evt);
				case Constants.PubSub.EventType.ALL_PROJECTROLES:
					out.collect((AllProjectRolesEvent) evt);
				case Constants.PubSub.EventType.ALL_PROJECTS:
					out.collect((AllProjectsEvent) evt);
				case Constants.PubSub.EventType.DEPARTMENT_INFO:
					out.collect((DepartmentInformationEvent) evt);
				case Constants.PubSub.EventType.DOCUMENT_CALL:
					out.collect((DocumentCallEvent) evt);
				case Constants.PubSub.EventType.DOCUMENT_CONTEXT:
					out.collect((DocumentCallEvent) evt);
				case Constants.PubSub.EventType.DOCUMENT_HIGHLY_RELEVANT:
					out.collect((DocumentHighlyRelevantEvent) evt);
				case Constants.PubSub.EventType.DOCUMENT_INFO:
					out.collect((DocumentInformationEvent) evt);
				case Constants.PubSub.EventType.FEEDBACK:
					out.collect((FeedbackEvent) evt);
				case Constants.PubSub.EventType.INFO_TOALL_DOCUMENTS:
					out.collect((InformationToAllDocumentsEvent) evt);
				case Constants.PubSub.EventType.LEARN:
					out.collect((LearnEvent) evt);
				case Constants.PubSub.EventType.OFFER:
					out.collect((OfferEvent) evt);
				case Constants.PubSub.EventType.PROJECT_INFO:
					out.collect((ProjectInformationEvent) evt);
				case Constants.PubSub.EventType.SESSION_END:
					out.collect((SessionEndEvent) evt);
				case Constants.PubSub.EventType.SESSION_START:
					out.collect((SessionStartEvent) evt);
				case Constants.PubSub.EventType.STAYALIVE:
					out.collect((StayAliveEvent) evt);
				case Constants.PubSub.EventType.SUCCESSFUL_FEEDBACK:
					out.collect((SuccessfulFeedbackEvent) evt);
				case Constants.PubSub.EventType.TOKEN:
					out.collect((TokenEvent) evt);
				case Constants.PubSub.EventType.USER_CONTEXT:
					out.collect((UserContextEvent) evt);
				case Constants.PubSub.EventType.USER_INACTIVE:
					out.collect((UserInactiveEvent) evt);
				case Constants.PubSub.EventType.USER_INFO:
					out.collect((UserInformationEvent) evt);
				case Constants.PubSub.EventType.USER_JOINED_SESSION:
					out.collect((UserJoinedSessionEvent) evt);
				case Constants.PubSub.EventType.USER_LEFT_SESSION:
					out.collect((UserLeftSessionEvent) evt);
				case Constants.PubSub.EventType.USER_LOGIN:
					out.collect((UserLoginEvent) evt);
				case Constants.PubSub.EventType.USER_START:
					out.collect((UserStartEvent) evt);
				default: out.collect(evt);
			}
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