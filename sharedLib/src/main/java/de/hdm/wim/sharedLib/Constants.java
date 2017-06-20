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
			// general
			public static final String EVENT_SOURCE	= "EventSource";
			public static final String EVENT_TYPE	= "EventType";

			// ML
			public static final String USER_ID		= "userId";
			public static final String DOCUMENT_ID	= "documentId";
			public static final String PROJECT_ID	= "projectId";
			public static final String DOCUMENT_AFFILIATION	= "documentAffiliation";
			public static final String USERCLICK	= "userClick";

			// SemRep
			public static final String SESSION_ID	= "sessionId";
			public static final String TOKEN_ID		= "tokenId";
			public static final String DEPARTMENT_ID		= "departmentId";
			public static final String DEPARTMENT_NAME		= "departmentName";
			public static final String DEPARTMENT_SHORT		= "departmentShort";
			public static final String DEPARTMENT_HAS_PROJECTS		= "departmentHasProjects";
			public static final String DEPARTMENT_HAS_WORKER		= "departmentHasWorker";
			public static final String DEPARTMENT_BELONGS_TO_COMPANY		= "departmentBelongsToCompany";
			public static final String PRENAME		= "prename";
			public static final String EMAIL		= "eMail";
			public static final String PROJECT_NAME		= "projectName";
			public static final String PROJECT_ROLE		= "projectRole";
			public static final String SURNAME		= "surname";
			public static final String DOCUMENT_AUTHOR		= "documentAuthor";
			public static final String DOCUMENT_CALL		= "documentCall";
			public static final String DOCUMENT_NAME		= "documentName";
			public static final String DOCUMENT_URL		= "documentURL";
			public static final String DOCUMENT_ADDED		= "documentAdded";
			public static final String DOCUMENT_VERSION		= "documentVersion";
			public static final String DOCUMENT_FAVORIT		= "documentFavorit";
			public static final String DOCUMENT_WRITTEN_BY		= "documentType";
			public static final String DOCUMENT_FAVORED_BY		= "documentType";
			public static final String DOCUMENT_KEYWORDS		= "documentKeywords";
			public static final String DOCUMENT_FOLDER		= "documentFolder";
			public static final String PROJECT_BELONGS_TO_COMPANY	= "projectBelongsToCompany";
			public static final String DOCUMENT_PRIO	= "documentPriority";
			public static final String DOCUMENT_TYPE = "documentType";
			public static final String PROJECT_BELONGS_TO_DEPARTMENT	= "projectBelongsToDepartment";
			public static final String PROJECT_HAS_MEMBERS	= "projectHasMembers";
			public static final String PROJECT_HAS_DOCUMENTS	= "projectHasDocuments";
			public static final String DOCUMENT	= "document";
			public static final String DOCUMENT_OFFER	= "documentOffer";
			public static final String FAVORITE	= "favorite";
			public static final String DOCUMENT_BELONGS_TO_PROJECT ="belongsToProject";

			// Event

			public static final String DOCUMENT_IDS	= "documentIds";
			public static final String DOCUMENT_NAMES	= "documentNames";
			public static final String USER_IDS	= "userIds";
			public static final String USER_NAMES	= "userNames";

			// GUI

			// Speech
			public static final String CONTEXTS = "contexts";
			public static final String TOKENS = "tokens";
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
			public static final String RICHTOKEN 		= "richtoken";

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
				RICHTOKEN,
				FEEDBACK_GUI,
				INSIGHTS,
				OFFERS,
				TOKEN,
				SESSIONINSIGHTS,
				INFORMATION
			);
		}

		/**
		 * Type of event.
		 */
		// TODO: is this still up-to-date ?
		public static class EventType {


			//Test
			public static final String ACTION 		= "action";
			public static final String GOOGLEOFFER 	= "googleoffer";
			public static final String RICHTOKEN	= "richtoken";
			public static final String REQUEST 		= "request";
			public static final String USER 		= "user";
			public static final String TIME			= "time";
			public static final String DATE			= "date";
			public static final String OFFER	    = "offer";

			//Produktion
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
			public static final String ALL_DEPARTMENTS		= "all-departments";
			public static final String ALL_PROJECTS		= "all-projects";
			public static final String ALL_PROJECTROLES		= "all-projectroles";
			public static final String LEARN		= "learning";
			public static final String STAYALIVE 	= "stayalive";
			public static final String INSIGHT  	= "insight";
			public static final String FEEDBACK		= "feedback";
			public static final String PROJECT_INFO = "project-information";
			public static final String INFO_TOALL_DOCUMENTS = "information-toall-documents";
			public static final String DEPARTMENT_INFO = "department-information";
			public static final String OFFERS	    = "offer";
			public static final String TOKEN		= "token";

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
		/**
		 * Config for using PubSub
		 */
		public static class Config {
			public static final String PUSH_ENDPOINT_PREFIX		= "/_ah/push-handlers";
			public static final String SECRET_TOKEN 			= "secretToken123";
			public static final String APP_ID					= "hdm-wim-devlab";
			public static final String PUBLISH_ENDPOINT 		= "/publish";
			public static final String HANDLER					= "/event";
			public static final String LOCAL_ADDRESS			= "http://localhost:8080";

			public static final String PUSH_ENDPOINT			= PUSH_ENDPOINT_PREFIX + HANDLER;
			public static final String APPSPOT_URL				= "https://" + APP_ID + ".appspot.com";
		}

		/**
		 * The two types of subscription
		 */
		public class SubscriptionType{
			public static final String PUSH	= "push";
			public static final String PULL = "pull";
		}
	}

	/**
	 * Http request parameters
	 */
	public class RequestParameters {
		public static final String TOPIC 			= "topic";
		public static final String ATTRIBUTES 		= "attributes";
		public static final String ATTRIBUTE_KEY 	= "key";
		public static final String ATTRIBUTE_VALUE 	= "value";
		public static final String PAYLOAD 			= "payload";
		public static final String SECRET_TOKEN 	= "secretToken";
	}
}
