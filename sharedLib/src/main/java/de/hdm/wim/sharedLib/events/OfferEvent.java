package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;

/**
 * The type Offer event.
 *
 * @author Gezim Grasniqi
 * @see <a href="https://github.com/Purii/hdm-wim-devlab/blob/master/docs/Events.md#offerevent">OfferEvent</a>
 */
public class OfferEvent extends IEvent {

	/**
	 * Instantiates a new Offer event.
	 */
	public OfferEvent(){
		this.attributes.put(AttributeKey.EVENT_TYPE, EventType.OFFER);
	}

	/**
	 * Get offer string.
	 *
	 * @return the string
	 */
	public String getOffer(){
		return this.attributes.get(AttributeKey.DOCUMENT_OFFER).toString();
	}

	/**
	 * Set offer.
	 *
	 * @param documentOffer the document offer
	 */
	public void setOffer(String documentOffer){
		this.attributes.put(AttributeKey.DOCUMENT_OFFER, documentOffer);
	}

}
