package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.classes.Message;

/**
 * Created by patrick on 13.06.17.
 */
public class InsightEvent extends Event {
    @Override
    protected Message getAsMessage() {
        return null;
    }
}
