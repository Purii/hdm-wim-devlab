package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.classes.Message;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;

/**
 * To be implemented by specific Event-Classes
 */
public abstract class Event {
    protected abstract Message getAsMessage();

    /**
     * Publish Event as Message to PubSub
     *
     * @throws Exception
     */
    public void publishToPubSub() throws Exception {
       
    }
}
