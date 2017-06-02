package de.hdm.wim.eventServices.eventProcessing.cep.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ben on 17.01.2017.
 */
public class TokenTopicEvent extends TokenEvent {

    private String _topic;
    private List<TokenEvent> _tokens = new ArrayList<>();

    /**
     * Instantiates a new Token topic event.
     *
     * @param _topic  the topic
     * @param _tokens the tokens
     */
    public TokenTopicEvent(String _topic, List<TokenEvent> _tokens) {
        this._topic = _topic;
        this._tokens = _tokens;
    }

    /**
     * Gets topic.
     *
     * @return the topic
     */
    public String get_topic() {
        return _topic;
    }

    /**
     * Sets topic.
     *
     * @param _topic the topic
     */
    public void set_topic(String _topic) {
        this._topic = _topic;
    }

    /**
     * Gets tokens.
     *
     * @return the tokens
     */
    public List<TokenEvent> get_tokens() {
        return _tokens;
    }

    /**
     * Sets tokens.
     *
     * @param _tokens the tokens
     */
    public void set_tokens(List<TokenEvent> _tokens) {
        this._tokens = _tokens;
    }

    /**
     * Add token.
     *
     * @param token the token
     */
    public void add_token(TokenEvent token){
        this._tokens.add(token);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TokenTopicEvent) {
            TokenTopicEvent other = (TokenTopicEvent) obj;

            return other.canEquals(this) && super.equals(other)
                    && _topic.equals(other._topic)
                    && _tokens.equals(other._tokens);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(_topic, _tokens);
    }

    @Override
    public boolean canEquals(Object obj) {
        return obj instanceof TokenTopicEvent;
    }

    @Override
    public String toString() {
        return "[TokenTopicEvent] Tokens:" + _tokens
                + " , Topic:" + _topic;
    }
}