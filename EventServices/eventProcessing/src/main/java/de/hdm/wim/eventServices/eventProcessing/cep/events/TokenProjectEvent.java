package de.hdm.wim.eventServices.eventProcessing.cep.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Ben on 17.01.2017.
 */
public class TokenProjectEvent extends TokenEvent {

    private String _projectName;
    private List<TokenEvent> _tokens = new ArrayList<>();

    /**
     * Instantiates a new Token project event.
     *
     * @param _projectName the project name
     * @param tokens       the tokens
     */
    public TokenProjectEvent(String _projectName, List<TokenEvent> tokens) {
        this._projectName = _projectName;
        this._tokens = tokens;
    }

    /**
     * Gets project name.
     *
     * @return the project name
     */
    public String get_projectName() {
        return _projectName;
    }

    /**
     * Sets project name.
     *
     * @param _projectName the project name
     */
    public void set_projectName(String _projectName) {
        this._projectName = _projectName;
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
        if (obj instanceof TokenProjectEvent) {
            TokenProjectEvent other = (TokenProjectEvent) obj;

            return other.canEquals(this) && super.equals(other)
                    && _projectName == other._projectName
                    && _tokens == other._tokens;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(_projectName, _tokens);
    }

    @Override
    public boolean canEquals(Object obj) {
        return obj instanceof TokenProjectEvent;
    }

    @Override
    public String toString() {
        return "[ProjectEvent] Tokens: " + _tokens.toString()
                + " , ProjectName: "    + _projectName;
    }
}
