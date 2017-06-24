package de.hdm.wim.sharedLib.events;

import de.hdm.wim.sharedLib.Constants.PubSub.AttributeKey;
import de.hdm.wim.sharedLib.Constants.PubSub.EventType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GezimKrasniqi on 20.06.17.
 */
public class OfferEvent extends IEvent {
	private Map<String, String> attributes = new HashMap<String, String>();

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
