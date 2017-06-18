package de.hdm.wim.sharedLib.events;

import java.util.Hashtable;

/**
 * Created by ben on 13/06/2017.
 */
public interface IEvent {
	String data								= "";
	String id 								= "";
	String publishTime 						= "";
	Hashtable<String, String> attributes 	= new Hashtable<String, String>();

	String getData();
	void setData(String data);

	String getId();
	void setId(String id);

	String getPublishTime();
	void setPublishTime(String publishTime);

	String getEventType();
	//void setEventType(String EventType);

	String getEventSource();
	void setEventSource(String EventSource);


	// not sure if we need this
	/*
	String getAttribute(String key);
	void setAttribute(String key, String value);
	*/

	Hashtable<String, String> getAttributes();
	void setAttributes(Hashtable<String, String> attributes);
}
