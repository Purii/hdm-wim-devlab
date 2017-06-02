package de.hdm.wim.eventServices.eventProcessing.cep.events;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ben on 17.01.2017.
 */
public class TokenTimeEvent extends TokenEvent {

    private LocalTime _time;
    private List<TokenEvent> _tokens = new ArrayList<>();

    /**
     * Instantiates a new Token time event.
     *
     * @param _time   the time
     * @param _tokens the tokens
     */
    public TokenTimeEvent(LocalTime _time, List<TokenEvent> _tokens) {
        this._time = _time;
        this._tokens = _tokens;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalTime get_time() {
        return _time;
    }

    /**
     * Sets time.
     *
     * @param _time the time
     */
    public void set_time(LocalTime _time) {
        this._time = _time;
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
        if (obj instanceof TokenTimeEvent) {
            TokenTimeEvent other = (TokenTimeEvent) obj;

            return other.canEquals(this) && super.equals(other)
                    && _time == other._time
                    && _tokens == other._tokens;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(_time, _tokens);
    }

    @Override
    public boolean canEquals(Object obj) {
        return obj instanceof TokenTimeEvent;
    }

    @Override
    public String toString() {
        return "[TimeEvent] Tokens: " + _tokens.toString()
                + " , Time: "    + _time;
    }
}
