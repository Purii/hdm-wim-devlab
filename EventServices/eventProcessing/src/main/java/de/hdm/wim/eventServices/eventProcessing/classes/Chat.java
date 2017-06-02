package de.hdm.wim.eventServices.eventProcessing.classes;

import de.hdm.wim.eventServices.eventProcessing.cep.events.MessageEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 16.01.2017.
 */
public class Chat {

    private LocalDateTime chatStart;
    private LocalDateTime chatEnd;

    private List<Participant> participants = new ArrayList<Participant>();
    /**
     * The Messages.
     */
    public List<MessageEvent> messages = new ArrayList<MessageEvent>();

    /**
     * Gets chat start.
     *
     * @return the chat start
     */
    public LocalDateTime getChatStart() {
        return chatStart;
    }

    /**
     * Sets chat start.
     *
     * @param chatStart start of the chat
     */
    public void setChatStart(LocalDateTime chatStart) {
        this.chatStart = chatStart;
    }

    /**
     * Gets chat end.
     *
     * @return the end of the chat
     */
    public LocalDateTime getChatEnd() {
        return chatEnd;
    }

    /**
     * Sets chat end (DateTime).
     *
     * @param chatEnd the end of the chat
     */
    public void setChatEnd(LocalDateTime chatEnd) {
        this.chatEnd = chatEnd;
    }

    /**
     * Gets list of all the participants.
     *
     * @return the participants
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Add participant to chat.
     *
     * @param participant the participant
     */
    public void addParticipant(Participant participant){
        this.participants.add(participant);
    }

    /**
     * Gets a list of messages.
     *
     * @return the messages
     */
    public List<MessageEvent> getMessages() {
        return messages;
    }

    /**
     * Add a new message object to the messages.
     *
     * @param message the message to add
     */
    public void addMessage(MessageEvent message) {
        this.messages.add(message);
    }
}
