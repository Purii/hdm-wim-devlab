package de.hdm.wim.sharedLib.pubsub.helper;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.spi.v1.Publisher;
import com.google.gson.GsonBuilder;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.events.IEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created by ben on 11/06/2017.
 */
public class PublishHelper {

	private static final Logger LOGGER 	= Logger.getLogger(PublishHelper.class);
	private static String PROJECT_ID 	= Config.APP_ID;
	private static boolean USE_REST		= false;
	private static boolean IS_LOCAL		= false;
	private static String ENDPOINT;

	/**
	 * Instantiates a new PublishHelper.
	 *
	 * @param isLocal true if you are running a local webapp, by default {@value
	 * PublishHelper#IS_LOCAL}
	 */
	public PublishHelper(boolean isLocal){
		IS_LOCAL = isLocal;
		ENDPOINT = EndpointHelper.GetPublishEndpoint(IS_LOCAL);
	}

	/**
	 * Instantiates a new PublishHelper.
	 *
	 * @param isLocal true if you are running a local webapp, by default {@value
	 * PublishHelper#IS_LOCAL}
	 * @param projectId set the projectId, by default {@value PublishHelper#PROJECT_ID}
	 */
	public PublishHelper(boolean isLocal, String projectId){
		IS_LOCAL 	= isLocal;
		PROJECT_ID	= projectId;
		ENDPOINT 	= EndpointHelper.GetPublishEndpoint(IS_LOCAL);
	}

	/**
	 * Publish an event
	 *
	 * @param event the message
	 * @param topicId name of the topic, see {@link de.hdm.wim.sharedLib.Constants.PubSub.Topic}
	 * @param useREST true if you want to use REST
	 * @throws Exception the exception
	 */
	public void Publish(IEvent event, String topicId, boolean useREST) throws Exception{
		publish(event, topicId, useREST);
	}

	/**
	 * Publish an event. Using useREST default: {@value PublishHelper#USE_REST}
	 *
	 * @param event the message
	 * @param topicId name of the topic, see {@link de.hdm.wim.sharedLib.Constants.PubSub.Topic}
	 * @throws Exception the exception
	 */
	public void Publish(IEvent event, String topicId) throws Exception{
		publish(event,topicId, USE_REST);
	}

	private void publish(IEvent event, String topicId, boolean useREST) throws Exception{
		if(useREST) {
			LOGGER.info("using REST");

			Map<String, Object> params = new LinkedHashMap<>();
			String jsonAttributes = new GsonBuilder().create()
				.toJson(event.getAttributes(), Map.class);

			params.put(RequestParameters.TOPIC, topicId);
			params.put(RequestParameters.PAYLOAD, event.getData());
			params.put(RequestParameters.ATTRIBUTES, jsonAttributes);

			publishPOST(params);
		}else{
			LOGGER.info("not using REST");
			publishPUBSUB(event, topicId);
		}
	}

	/**
	 * Publish an event using PubSub.
	 *
	 * @param event the event to be published
	 * @param topicId the topic you want to publish to
	 * @throws Exception the exception
	 */
	private static void publishPUBSUB(IEvent event, String topicId) throws Exception{

		TopicName topicName 						= TopicName.newBuilder().setTopic(topicId).setProject(PROJECT_ID).build();
		List<ApiFuture<String>> messageIdFutures 	= new ArrayList<>();
		Publisher publisher 						= null;

		try {
			publisher 					= Publisher.defaultBuilder(topicName).build();
			ByteString data 			= ByteString.copyFromUtf8(event.getData());
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
														.setData(data)
														.putAllAttributes(event.getAttributes())
														.build();

			// Once published, returns a server-assigned message id (unique within the topic)
			ApiFuture<String> messageIdFuture 	= publisher.publish(pubsubMessage);
			messageIdFutures.add(messageIdFuture);

		} finally {
			// wait on any pending publish requests.
			List<String> messageIds = ApiFutures.allAsList(messageIdFutures).get();

			for (String messageId : messageIds) {
				LOGGER.info("published with message ID: " + messageId);
			}

			if (publisher != null) {
				// When finished with the publisher, shutdown to free up resources.
				publisher.shutdown();
			}
		}
	}

	/**
	 * Publish an event using http post to the given endpoint.
	 *
	 * @param params
	 * @throws Exception the exception
	 */
	private static void publishPOST(Map<String,Object> params) throws Exception{
		URL url;
		HttpURLConnection conn;

		//TODO: resend message if it didnt work
		//build request url
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append("&");

			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append("=");
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		LOGGER.info("endPoint: " + ENDPOINT);
		LOGGER.info("postData: " + postData);

		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		// set up connection
		url  = new URL(ENDPOINT);
		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",   "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);

		// send request
		conn.getOutputStream().write(postDataBytes);

		// get response
		LOGGER.info("ResponseCode: " 	+ conn.getResponseCode());
		LOGGER.info("ResponseMessage: " + conn.getResponseMessage());
	}
}
