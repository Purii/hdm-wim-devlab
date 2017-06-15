package de.hdm.wim.sharedLib;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ben on 17/04/2017.
 */
public class Constants {

	public static class PubSub{

		/**
		 * Keys for the message attributes
		 */
		public static class AttributeKey{
			public static final String EVENT_SOURCE	= "EventSource";
			public static final String EVENT_TYPE	= "EventType";
		}

		/**
		 * Source of event
		 */
		public static class EventSource{
			public static final String SPEECH_TOKENIZATION 		= "speech-tokenization";
			public static final String EVENT 					= "event";
			public static final String MACHINE_LEARNING 		= "machine-learning";
			public static final String USER_INTERFACE 			= "user-interface";
			public static final String SEMANTIC_REPRESENTATION 	= "semantic-representation";

			// for message generator only
			public static final List<String> list = Arrays.asList(
				SPEECH_TOKENIZATION,
				EVENT,
				MACHINE_LEARNING,
				USER_INTERFACE,
				SEMANTIC_REPRESENTATION
			);
		}

		/**
		 * Topic
		 * Format: "my-test-topic-x"
		 */
		public static class Topic {
			// für Testzwecke
			public static final String TOPIC_1 = "topic-1";
			public static final String TOPIC_2 = "topic-2";
			public static final String TOPIC_3 = "topic-3";
			public static final String TOPIC_4 = "topic-4";
			public static final String TOPIC_5 = "topic-5";
			public static final String TOPIC_EVENT 		= "topic-event";
			public static final String TOPIC_SEM_REP 	= "topic-semRep";
			public static final String TOPIC_ML 		= "topic-ml";
			public static final String TOPIC_GUI 		= "topic-gui";
			public static final String PUSH_TEST 		= "topic-push-test-123";

			
			// für die Produktion
			public static final String FEEDBACK_GUI 	= "feedback-gui";
			public static final String INSIGHTS 		= "insights";
			public static final String OFFERS 			= "offers";
			public static final String SESSIONINSIGHTS 	= "sessioninsights";
			public static final String TOKEN 			= "token";
			public static final String INFORMATION		= "information";
			


			// for message generator only
			public static final List<String> list = Arrays.asList(
				TOPIC_1,
				TOPIC_2,
				TOPIC_3,
				TOPIC_4,
				TOPIC_5,
				FEEDBACK_GUI,
				INSIGHTS,
				OFFERS,
				TOKEN,
				SESSIONINSIGHTS,
				INFORMATION
			);
		}

		/**
		 * Type of events.
		 */
		public static class EventType {
			public static final String ACTION 		= "action";
			public static final String USER_INFO		= "user-information";
			public static final String DOCUMENT_INFO		= "document-information";
			public static final String USER_CONTEXT = "user-context";
			public static final String DOCUMENT_CONTEXT		= "document-context";
			public static final String DOCUMENT_RELEVANT	= "document-relevant";
			public static final String DOCUMENT_HIGHLY_RELEVANT	= "document-highly-relevant";
			public static final String SESSION_START	= "session-start";
			public static final String SESSION_END	= "session-end";
			public static final String USER_INACTIVE	= "user-inactive";
			public static final String USER_LOGIN	= "user-login";
			public static final String USER_LOGOUT	= "user-logout";
			public static final String USER_JOINED_SESSION	= "user-joined-session";
			public static final String USER_LEFT_SESSION	= "user-left-session";
			public static final String LEARN	= "learning";
			public static final String REQUEST 		= "request";
			public static final String USER 		= "user";
			public static final String TIME			= "time";
			public static final String DATE			= "date";
			public static final String STAYALIVE 	= "stayalive";
			public static final String INSIGHT  	= "insight";
			public static final String GOOGLEOFFER 	= "googleoffer";
			public static final String OFFER	    = "offer";
			public static final String TOKEN		= "token";
			public static final String RICHTOKEN	= "richtoken";
			public static final String FEEDBACK		= "feedback";

			// for message generator only
			public static final List<String> list = Arrays.asList(
				ACTION,
				REQUEST,
				USER,
				FEEDBACK,
				TIME,
				DATE,
				STAYALIVE,
				INSIGHT,
				GOOGLEOFFER,
				OFFER,
				TOKEN,
				RICHTOKEN,
				FEEDBACK
			);
		}

		// NEVER CHANGE ANY OF THIS! except you know what you are doing..
		public static class Config {
			public static final String PUSH_ENDPOINT_PREFIX		= "/_ah/push-handlers";
			public static final String SECRET_TOKEN 			= "secretToken123";
			public static final String APP_ID					= "hdm-wim-devlab";
			public static final String PUBLISH_ENDPOINT 		= "/pubsub/publish";
			public static final String PUSH_ENDPOINT			= PUSH_ENDPOINT_PREFIX + "/pubsub/push";
			public static final String APPSPOT_URL				= "https://" + APP_ID + ".appspot.com";
			public static final String LOCAL_ADDRESS			= "http://localhost:8080";
		}

		public class SubscriptionType{
			public static final String PUSH	= "push";
			public static final String PULL = "pull";
		}

	}

	public class WeekDays {
		public static final String monday 		= "monday";
		public static final String tuesday 		= "tuesday";
		public static final String wednesday 	= "wednesday";
		public static final String thursday		= "thursday";
		public static final String friday		= "friday";
		public static final String saturday 	= "saturday";
		public static final String sunday 		= "sunday";
	}

	public class RequestParameters {
		public static final String TOPIC 			= "topic";
		public static final String ATTRIBUTES 		= "attributes";
		public static final String ATTRIBUTE_KEY 	= "key";
		public static final String ATTRIBUTE_VALUE 	= "value";
		public static final String PAYLOAD 			= "payload";
		public static final String SECRET_TOKEN 	= "secretToken";
	}
}
