package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * @author Gezim
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#offerevent">OfferEvent</a>
 */
public class OfferEvent extends IEvent {
	public OfferEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.OFFER);
	}

	public String getOffer(){
		return this.attributes.get(AttributeKey.DOCUMENT_OFFER).toString();
	}

	public void setOffer(String documentOffer){
		this.attributes.put(AttributeKey.DOCUMENT_OFFER, documentOffer);
	}

}
