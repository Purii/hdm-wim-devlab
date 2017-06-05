package de.hdm.wim.sharedLib.helper;

import static de.hdm.wim.sharedLib.Constants.Config.localPublishEndpoint;

import de.hdm.wim.sharedLib.classes.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ben on 5/06/2017.
 */
public class PublishHelper {

	private static String _request = localPublishEndpoint;

	public PublishHelper(){	}

	public PublishHelper(String request){
		this._request 	= request;
	}

	public void Publish(Message message) throws Exception{

		Map<String,Object> params = new LinkedHashMap<>();

		params.put("topic", 	message.getTopic());
		params.put("payload", 	message.getData());

		sendPost(params);
	}

	private static void sendPost(Map<String,Object> params) throws Exception{

		URL url = new URL(_request);

		//build request url
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String,Object> param : params.entrySet()) {
			if (postData.length() != 0) postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		System.out.println("postData: " + postData);

		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		// set up connection
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

		System.out.print("test1:");

		for (int c; (c = in.read()) >= 0;) {
			System.out.print((char) c);
		}
	}
}
