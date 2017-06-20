package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.TestPattern2;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import com.google.pubsub.v1.PubsubMessage;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
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

	//		TestPattern2 testPattern2 = new TestPattern2();
	//		testPattern2.run(env, messageStream);

            // print message stream

            //tokenStream.print();

            env.execute("CEP chat stream job");
		}
	public static class Splitter implements FlatMapFunction<String, PubsubMessage> {
		@Override
		public void flatMap(String value, Collector<PubsubMessage> out) throws Exception {
			PubsubMessage psm = new GsonBuilder().create().fromJson(value, PubsubMessage.class);
			Helper helper = new Helper();
			//out.collect(new PubsubMessage(psm.getData(),psm.getMessageId(), psm.getPublishTime(), psm.getAttributesMap()));
		}
	}

}