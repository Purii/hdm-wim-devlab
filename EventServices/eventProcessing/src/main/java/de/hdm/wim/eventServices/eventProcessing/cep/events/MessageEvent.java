package de.hdm.wim.eventServices.eventProcessing.cep.events;

import de.hdm.wim.eventServices.eventProcessing.classes.Participant;
import de.hdm.wim.eventServices.eventProcessing.classes.Message;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ben on 15.01.2017.
 */
public class MessageEvent extends Message {

    /**
     * Instantiates a new Message event.
     *
     * @param tokens    the tokens
     * @param sender    the sender
     * @param timestamp the timestamp
     * @param messageId the message id
     */
    public MessageEvent(List<String> tokens, Participant sender, LocalDateTime timestamp, int messageId) {
        super(tokens, sender, timestamp, messageId);
    }

    /**
     * Instantiates a new Message event.
     */
    public MessageEvent(){}

    @Override
    public int hashCode() {
        return Objects.hash(super.getTokens(), super.getSender(), super.getTimestamp() ,super.getMessageId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) obj;
            return messageEvent.canEquals(this)
                    && getTokens()       == messageEvent.getTokens()
                    && getSender()       == messageEvent.getSender()
                    && getTimestamp()    == messageEvent.getTimestamp()
                    && getMessageId()    == messageEvent.getMessageId();
        } else {
            return false;
        }
    }

    /**
     * Can equals boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    public boolean canEquals(Object obj){
        return obj instanceof MessageEvent;
    }

    @Override
    public String toString() {
        return "[MessageEvent] ID: " + getMessageId() +
                " , Sender: "       + getSender().toString() +
                " , TimeStamp: "    + getTimestamp().toString() +
                " , Tokens: "       + getTokens().toString() ;
    }
}