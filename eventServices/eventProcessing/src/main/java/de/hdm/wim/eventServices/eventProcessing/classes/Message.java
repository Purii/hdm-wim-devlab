package de.hdm.wim.eventServices.eventProcessing.classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 16.01.2017.
 */
public class Message {

    /**
     * The Tokens.
     */
    private List<String> _tokens = new ArrayList<>();
    /**
     * The Sender.
     */
    private Participant _sender;
    /**
     * The Timestamp.
     */
    private LocalDateTime _timestamp;

    /**
     * The Message id.
     */
    private int _messageId;

    /**
     * Instantiates a new Message.
     *
     * @param tokens    the tokens
     * @param sender    the sender
     * @param timestamp the timestamp
     * @param messageId the message id
     */
    public Message(List<String> tokens, Participant sender, LocalDateTime timestamp, int messageId) {
        this._tokens = tokens;
        this._sender = sender;
        this._timestamp = timestamp;
        this._messageId = messageId;
    }

    /**
     * Instantiates a new Message.
     */
    public Message(){}

    /**
     * Gets tokens.
     *
     * @return the tokens
     */
    public List<String> getTokens() {
        return _tokens;
    }

    /**
     * Sets tokens.
     *
     * @param tokens the tokens
     */
    public void setTokens(List<String> tokens) {
        this._tokens = tokens;
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public Participant getSender() {
        return _sender;
    }

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(Participant sender) {
        this._sender = sender;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return _timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this._timestamp = timestamp;
    }

    /**
     * Gets message id.
     *
     * @return the message id
     */
    public int getMessageId() {
        return _messageId;
    }

    /**
     * Sets message id.
     *
     * @param messageId the message id
     */
    public void setMessageId(int messageId) {
        this._messageId = messageId;
    }
}
