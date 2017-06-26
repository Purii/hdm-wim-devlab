package de.hdm.wim.sharedLib.pubsub.helper;

import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;

/**
 * EndPoint Helper to construct the endpoints
 *
 * @author Benedikt Benz
 * @createdOn 14.06.2017
 */
public class EndpointHelper {

	/**
	 * Get push endpoint string.
	 *
	 * @param isLocal the is local
	 * @param handler the handler EXAMPLE: {@link de.hdm.wim.sharedLib.Constants.PubSub.Topic.CEP_SESSIONINSIGHTS#HANDLER_ID}
	 * @return the string
	 */
	public static String GetPushEndpoint(boolean isLocal, String handler){
		String endpoint;

		if(isLocal)
			endpoint = Config.LOCAL_ADDRESS;
		else
			endpoint = Config.APPSPOT_URL;

		return endpoint + Config.PUSH_ENDPOINT_PREFIX + handler + "?" + RequestParameters.SECRET_TOKEN + "=" + Config.SECRET_TOKEN;
	}

	/**
	 * Get publish endpoint string.
	 *
	 * @param isLocal the is local
	 * @return the endpoint as String
	 */
	public static String GetPublishEndpoint(boolean isLocal){
		String endpoint;

		if(isLocal)
			endpoint = Config.LOCAL_ADDRESS;
		else
			endpoint = Config.APPSPOT_URL;

		return endpoint + Config.PUBLISH_ENDPOINT;
	}
}
