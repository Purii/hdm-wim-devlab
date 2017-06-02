package de.hdm.wim.sharedLib;
import java.sql.Timestamp;


public class Event {

	private String data;
	private String eventSource;
	private String eventType;
	private int eventId;
	private Timestamp publishTime;
	private int idCounter = 10000;

	public Event(){
		publishTime = new Timestamp(System.currentTimeMillis());
		eventId = idCounter++;
	}

	public Event(String source, String type, String data){
		publishTime = new Timestamp(System.currentTimeMillis());
		eventId = idCounter++;
		eventSource = source;
		eventType = type;
		this.data = data;
	}

}