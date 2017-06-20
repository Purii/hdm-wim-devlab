package de.hdm.wim.eventServices.eventProcessing.cep.patterns;

import de.hdm.wim.eventServices.eventProcessing.cep.events.TokenEvent;
import de.hdm.wim.eventServices.eventProcessing.cep.events.TokenDateEvent;
import de.hdm.wim.eventServices.eventProcessing.helper.DateHelper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by Ben on 20.01.2017.
 */
public class RelationToDatePattern {
    /**
     * Run.
     *
     * @param env         the env
     * @param tokenStream the message stream
     */
    public void run(StreamExecutionEnvironment env, DataStream<TokenEvent> tokenStream ) {

        Pattern<TokenEvent, ?> relationToDatePattern = Pattern
                .<TokenEvent>begin("time direction token")
                .where(evt -> evt.get_token().equals("next")
                        || evt.get_token().equals("previous")
                        || evt.get_token().equals("past")
                )
                .next("day of week token")
                .where(evt -> evt.get_token().equals("monday")
                        || evt.get_token().equals("tuesday")
                        || evt.get_token().equals("wednesday")
                        || evt.get_token().equals("thursday")
                        || evt.get_token().equals("friday")
                        || evt.get_token().equals("saturday")
                        || evt.get_token().equals("sunday")
                );


        // Create a pattern stream from our project pattern
        PatternStream<TokenEvent> relationToDatePatternStream = CEP.pattern(
                tokenStream,
                relationToDatePattern);

        // Generate TokenDateEvent for each matched date pattern
        DataStream<TokenDateEvent> result = relationToDatePatternStream.select(
                pattern -> {

                    LocalDate date = DateHelper.GetNextOrPrevDoW(
                            pattern.get("time direction token").get_token(),
                            pattern.get(("day of week token")).get_token()
                    );

                    List<TokenEvent> tokens = new ArrayList<>();
                    tokens.add( pattern.get("time direction token"));
                    tokens.add( pattern.get("day of week token"));

                    return new TokenDateEvent(date, tokens);
                }
        );

        // print to stdout
        result.print();
    }
}
