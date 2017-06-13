package de.hdm.wim.sharedLib.pubsub.helper;

import com.google.gson.GsonBuilder;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.Event;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created by ben on 11/06/2017.
 */
public class PublishHelper {

	private static final Logger LOGGER 	= Logger.getLogger(PublishHelper.class);
	private static String ENDPOINT;
	private static boolean IS_LOCAL; // true if you are running a local server with the webapp

	/**
	 * Instantiates a new Publish helper.
	 */
	//public PublishHelper(){	}

	/**
	 * Instantiates a new PublishHelper.
	 *
	 * @param isLocal true if you are running a local webapp, false in prod
	 */
	public PublishHelper(boolean isLocal){

		this.IS_LOCAL = isLocal;

		if(IS_LOCAL)
			ENDPOINT = Config.getLocalPublishEndpoint();
		else
			ENDPOINT = Config.getProdPublishEndpoint();
	}

	/**
	 * Publish an event
	 *
	 * @param event the message
	 * @throws Exception the exception
	 */
	public void Publish(Event event, String topic) throws Exception{

		Map<String,Object> params = new LinkedHashMap<>();
		String jsonAttributes 	  = new GsonBuilder().create()
			.toJson(event.getAttributes(), Map.class);

		params.put(RequestParameters.TOPIC, 		topic);
		params.put(RequestParameters.PAYLOAD, 		event.getData());
		params.put(RequestParameters.ATTRIBUTES,	jsonAttributes);

		sendPost(params);
	}

	private static void sendPost(Map<String,Object> params) throws Exception{
		URL url;
		HttpURLConnection conn;

		//build request url
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append("&");

			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append("=");
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

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
