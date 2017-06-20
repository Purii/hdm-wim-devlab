package de.hdm.wim.sharedLib.pubsub.helper;

import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;

/**
 * Created by ben on 14/06/2017.
 */
public class EndpointHelper {

	/**
	 * Get push endpoint string.
	 *
	 * @param isLocal the is local
	 * @param handler the handler
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
	 * @return the string
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
