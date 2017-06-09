package de.hdm.wim.eventServices.eventProcessing;

import com.google.gson.GsonBuilder;
import de.hdm.wim.eventServices.eventProcessing.cep.events.MessageEvent;
import de.hdm.wim.eventServices.eventProcessing.cep.events.TokenEvent;
import de.hdm.wim.eventServices.eventProcessing.cep.patterns.RelationToDatePattern;
import de.hdm.wim.eventServices.eventProcessing.cep.source.MessageEventSource;
import de.hdm.wim.eventServices.eventProcessing.cep.source.TokenSource;
import de.hdm.wim.sharedLib.classes.PubSubMessage;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple1;
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

			DataStream<PubSubMessage> messageStream = env
				.socketTextStream("localhost", 9999)
				.flatMap(new Splitter());


            // print message stream

            //tokenStream.print();

            env.execute("CEP chat stream job");
		}
	public static class Splitter implements FlatMapFunction<String, PubSubMessage> {
		@Override
		public void flatMap(String value, Collector<PubSubMessage> out) throws Exception {
			PubSubMessage psm = new GsonBuilder().create().fromJson(value, PubSubMessage.class);
			out.collect(new PubSubMessage(psm.getData(),psm.getMessageId(), psm.getPublishTime(), psm.getAttributes()));

		}
	}
}