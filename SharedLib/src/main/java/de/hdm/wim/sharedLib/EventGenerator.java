package de.hdm.wim.sharedLib;

import java.util.Random;

/**
 * Created by nils on 30/05/2017.
 */
public class EventGenerator {
	private final Random random             = new Random();

	/**
	 * Instantiates a new Event generator.
	 *
	 */
	protected EventGenerator() {
	}

	/**
	 * Generate event event.
	 *
	 * @return the event
	 */
    public Event GenerateEvent(){

        String data = "something";

        return new Event(getRandomEventSource() ,getRandomEventType() , data);
    }


	private String getRandomEventType(){
    	int i = random.nextInt(6);

		switch (i) {
			case 0:
				return Enums.EventType.action;
			case 1:
				return Enums.EventType.date;
			case 2:
				return Enums.EventType.feedback;
			case 3:
				return Enums.EventType.request;
			case 4:
				return Enums.EventType.time;
			case 5:
				return Enums.EventType.user;
			default:
				return "";
		}
	}


	private String getRandomEventSource(){
		int i = random.nextInt(5);

		switch (i) {
			case 0:
				return Enums.EventSource.event;
			case 1:
				return Enums.EventSource.machineLearning;
			case 2:
				return Enums.EventSource.semanticRepresentation;
			case 3:
				return Enums.EventSource.speechTokenization;
			case 4:
				return Enums.EventSource.userInterface;
			default:
				return "";
		}
	}

}
