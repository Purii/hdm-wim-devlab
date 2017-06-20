package de.hdm.wim.eventServices.eventProcessing.cep.events;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ben on 17.01.2017.
 */
public class TokenDateEvent extends TokenEvent {

    private LocalDate _date;
    private List<TokenEvent> _tokens = new ArrayList<>();

    /**
     * Instantiates a new Token date event.
     *
     * @param _date   the date
     * @param _tokens the tokens
     */
    public TokenDateEvent(LocalDate _date, List<TokenEvent> _tokens) {
        //super(tokens, sender, timestamp, messageId, _token, _sender);
        this._date = _date;
        this._tokens = _tokens;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate get_date() {
        return _date;
    }

    /**
     * Sets date.
     *
     * @param _date the date
     */
    public void set_date(LocalDate _date) {
        this._date = _date;
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
        if (obj instanceof TokenDateEvent) {
            TokenDateEvent other = (TokenDateEvent) obj;

            return other.canEquals(this) && super.equals(other)
                    && _tokens == other._tokens
                    && _date == other._date;
        } else {
            return false;
        }
    }

    @Override
    public boolean canEquals(Object obj) {
        return obj instanceof TokenDateEvent;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(_date, _tokens);
    }

    @Override
    public String toString() {

        // beautify tokens for presentation
        // TODO: remove this
        StringBuilder builder = new StringBuilder();
        for(TokenEvent tkn:_tokens){
            builder.append(tkn);
        }

        String beautifiedTokens = builder.toString();

        return "[TokenDateEvent]" + "\n"
                + "Date: " + _date + "\n"
                + "Tokens: "+ "\n" + beautifiedTokens;
    }
}
