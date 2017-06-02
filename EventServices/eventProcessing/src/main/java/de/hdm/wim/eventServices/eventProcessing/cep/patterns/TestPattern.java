package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.eventServices.eventProcessing.cep.events.MessageEvent;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by Ben on 19.01.2017.
 */
public class TestPattern {

    /**
     * Run.
     *
     * @param env           the env
     * @param messageStream the message stream
     */
    public void run(StreamExecutionEnvironment env, DataStream<MessageEvent> messageStream ) {

        // Match all event with MessageId = 1
        Pattern<MessageEvent, ?> testPattern = Pattern
                .<MessageEvent>begin("first")
                .where(evt -> evt.getMessageId() == 1);

        // Create a pattern stream from our project pattern
        PatternStream<MessageEvent> testPatternStream = CEP.pattern(
                messageStream,
                testPattern);

        // Generate ProjectEvents for each matched project pattern
        DataStream<MessageEvent> result = testPatternStream.select(
                pattern -> {
                    return pattern.get("first");
                }
        );

        // print to stdout
        result.print();
    }
}
