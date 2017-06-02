package de.hdm.wim.eventServices.eventProcessing.cep.source;

import de.hdm.wim.eventServices.eventProcessing.cep.events.MessageEvent;
import de.hdm.wim.eventServices.eventProcessing.cep.events.TokenEvent;
import de.hdm.wim.eventServices.eventProcessing.classes.Chat;
import de.hdm.wim.eventServices.eventProcessing.classes.Participant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * Created by Ben on 15.01.2017.
 */
public class TokenSource implements SourceFunction<TokenEvent> {

    private volatile boolean isRunning = true;

    @Override
    public void run(SourceContext<TokenEvent> sourceContext) throws Exception {

        // set up 2 participants
        final Participant participant1 = new Participant("Mike", "Turbo");
        final Participant participant2 = new Participant("Sheila", "Pure");

        // define tokens
        List<String> tokens0 = Arrays.asList("Hey");
        List<String> tokens1 = Arrays.asList("Hello");
        List<String> tokens2 = Arrays.asList("lets", "talk", "about", "current",  "activities", "concerning", "HighNet", "project");
        List<String> tokens3 = Arrays.asList("ok", "shall", "we", "look", "at", "tasks", "leading","to", "milestone", "ahead" );
        List<String> tokens4 = Arrays.asList("sure", "we", "working", "on", "network","issues", "for", "diagnosis", "module", "item 3", "task", "list", "blabla", "we", "will", "come up", "with", "something", "viable", "shortly");
        List<String> tokens5 = Arrays.asList("that", "sounds", "great", "what", "about", "expenses", "do", "you", "think", "you", "will", "be", "able", "to", "stay", "whithin", "limits", "we", "aggreed", "upon", "last week");
        List<String> tokens6 = Arrays.asList("that", "should", "be", "no", "problem", "i", "will", "leave", "detailed", "report", "on", "google", "drive");
        List<String> tokens7 = Arrays.asList("ok", "thanks", "lets", "make", "appointment", "for", "our", "next", "meeting");
        List<String> tokens8 = Arrays.asList("let", "me", "check", "my", "calendar", "how", "about", "next", "thursday", "at", "16", "hours", "your", "time");
        List<String> tokens9 = Arrays.asList("perfect", "see", "you", "then", "bye");
        List<String> tokens10 = Arrays.asList("have", "a", "nice", "day", "bye");
        List<String> tokens11 = Arrays.asList("expenses", "previous", "monday");
        List<String> tokens12 = Arrays.asList("Let", "us", "make", "an", "appointment", "for", "next", "Monday", "at", "09", "am");
        List<String> tokens13 = Arrays.asList("Let's", "plan", "a", "meeting", "at", "16:00", "on", "next", "monday");
        List<String> tokens14 = Arrays.asList("expenses", "blaaa", "at", "1pm");

        // set up chat
        Chat chat = new Chat();
        chat.addParticipant(participant1);
        chat.addParticipant(participant2);

        chat.addMessage(new MessageEvent(tokens0, participant1, LocalDateTime.now(),0));
        chat.addMessage(new MessageEvent(tokens1, participant2, LocalDateTime.now(),1));
        chat.addMessage(new MessageEvent(tokens2, participant1, LocalDateTime.now(),2));
        chat.addMessage(new MessageEvent(tokens3, participant2, LocalDateTime.now(),3));
        chat.addMessage(new MessageEvent(tokens4, participant1, LocalDateTime.now(),4));
        chat.addMessage(new MessageEvent(tokens5, participant2, LocalDateTime.now(),5));
        chat.addMessage(new MessageEvent(tokens6, participant1, LocalDateTime.now(),6));
        chat.addMessage(new MessageEvent(tokens7, participant2, LocalDateTime.now(),7));
        chat.addMessage(new MessageEvent(tokens8, participant1, LocalDateTime.now(),8));
        chat.addMessage(new MessageEvent(tokens9, participant2, LocalDateTime.now(),9));
        chat.addMessage(new MessageEvent(tokens10, participant1, LocalDateTime.now(),10));
        chat.addMessage(new MessageEvent(tokens11, participant2, LocalDateTime.now(),11));
        chat.addMessage(new MessageEvent(tokens12, participant1, LocalDateTime.now(),12));
        chat.addMessage(new MessageEvent(tokens13, participant2, LocalDateTime.now(),13));
        chat.addMessage(new MessageEvent(tokens14, participant1, LocalDateTime.now(),14));

        int messageIndex = 0;

        // go through all the messages in chat
        while (isRunning && messageIndex < chat.messages.size()) {

            MessageEvent chatMessage = chat.messages.get(messageIndex);
            int tokenCount = chatMessage.getTokens().size();

            // add each token from message to sourceContext
            for(int tokenIndex = 0; tokenIndex < tokenCount; tokenIndex++){

                TokenEvent tknEvt =
                        new TokenEvent(
                                chatMessage.getMessageId(),
                                chatMessage.getTokens().get(tokenIndex),
                                chatMessage.getSender()
                        );

                sourceContext.collect(tknEvt);
            }
            messageIndex++;
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
