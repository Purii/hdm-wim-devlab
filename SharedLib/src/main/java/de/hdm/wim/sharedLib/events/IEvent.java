package de.hdm.wim.sharedLib.events;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by ben on 13/06/2017.
 */
public interface IEvent {

	String data = "";
	String id = "";
	String publishTime = "";
	Map<String, String> attributes = new HashMap<String, String>();

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

	// not sure if we actually need this
	/*
	String getAttribute(String key);
	void setAttribute(String key, String value);
	*/

	Map<String, String> getAttributes();

	void setAttributes(Map<String, String> attributes);
}
