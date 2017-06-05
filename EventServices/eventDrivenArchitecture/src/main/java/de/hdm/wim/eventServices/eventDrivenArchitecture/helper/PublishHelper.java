package de.hdm.wim.eventServices.eventDrivenArchitecture.helper;

import static de.hdm.wim.sharedLib.Constants.Config.localPublishEndpoint;

import de.hdm.wim.sharedLib.classes.Message;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created by ben on 5/06/2017.
 */
public class PublishHelper {

	private static String _request 		= localPublishEndpoint;
	private static final Logger _logger = Logger.getLogger(PublishHelper.class);

	/**
	 * Instantiates a new Publish helper.
	 */
	public PublishHelper(){	}

	/**
	 * Instantiates a new PublishHelper.
	 *
	 * @param request the request
	 */
	public PublishHelper(String request){
		this._request 	= request;
	}

	/**
	 * Publish a message
	 *
	 * @param message the message
	 * @throws Exception the exception
	 */
	public void Publish(Message message) throws Exception{

		Map<String,Object> params = new LinkedHashMap<>();

		params.put("topic", 	message.getTopic());
		params.put("payload", 	message.getData());

		sendPost(params);
	}

	private static void sendPost(Map<String,Object> params) throws Exception{
		URL url;
		HttpURLConnection conn = null;

		//build request url
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');

			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		_logger.info("postData: " + postData);

		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		// set up connection
		url  = new URL(_request);
		conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",   "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);

		// send request
		conn.getOutputStream().write(postDataBytes);

		// get response
		_logger.info("ResponseCode: " 	 + conn.getResponseCode());
		_logger.info("ResponseMessage: " + conn.getResponseMessage());
	}
}
