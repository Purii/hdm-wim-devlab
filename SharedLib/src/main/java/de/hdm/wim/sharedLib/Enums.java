package de.hdm.wim.sharedLib;

/**
 * Created by ben on 17/04/2017.
 */
public class Enums {

	/**
	 * Source of event
	 */
	public static class EventSource{
		public static final String speechTokenization 		= "speech-tokenization";
		public static final String event 					= "event";
		public static final String machineLearning 			= "machine-learning";
		public static final String userInterface 			= "user-interface";
		public static final String semanticRepresentation	= "semantic-representation";
	}

    /**
     * Topic
	 * Format: "my-test-topic-x"
     */
    public class Topic {
		public static final String topic1 	= "topic-1";
		public static final String topic2 	= "topic-2";
		public static final String topic3 	= "topic-3";
		public static final String topic4 	= "topic-4";
		public static final String topic5	= "topic-5";
    }

	/**
	 * Event types.
	 */
	public static class EventType {
		public static final String action 	= "action";
		public static final String request 	= "request";
		public static final String user 	= "user";
		public static final String feedback	= "feedback";
		public static final String time		= "time";
		public static final String date		= "date";
	}

	public class WeekDays {
		public static final String monday 		= "monday";
		public static final String tuesday 		= "tuesday";
		public static final String wednesday 	= "wednesday";
		public static final String thursday 	= "thursday";
		public static final String friday		= "friday";
		public static final String saturday 	= "saturday";
		public static final String sunday 		= "sunday";
	}

	/**
	 * Event types.
	 * Format: "my-test-subscription-topic-x"
	 */
	public class Subscription {
		public static final String subscriptionTopic1 = "subscription-topic-1";
	}


}