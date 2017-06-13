package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.classes.Message;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;

/**
 * To be implemented by specific Event-Classes
 * @Todo Attributes
 */
public abstract class Event {
    private String eventId;
    private String publishTime;
    private String data;

    protected abstract Message getAsMessage();

    /**
     * Publish Event as Message to PubSub
     *
     * @throws Exception
     */
    public void publishToPubSub() throws Exception {

    }

    public Event(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setMessageId(String eventId) {
        this.eventId = eventId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
