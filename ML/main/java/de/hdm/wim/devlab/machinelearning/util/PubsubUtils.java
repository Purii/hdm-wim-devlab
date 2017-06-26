/*
 * Copyright (c) 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package de.hdm.wim.devlab.machinelearning.util;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.PubsubScopes;
import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.apphosting.api.ApiProxy;
import com.google.common.base.Preconditions;

import de.hdm.wim.devlab.machinelearning.appengine.Constants;

/**
 * Utility-Klasse die zur Interaktion mit PubSub genutzt wird.
 */
public final class PubsubUtils {

	/**
	 * Name der eingesetzten Applikation (projektübergreifender Name)
	 */
	private static final String APPLICATION_NAME = "wim-devlab-ml";

	/**
	 * Konstruktur.
	 */
	private PubsubUtils() {
	}

	/**
	 * 
	 * Baut einen neuen PubSub-Client mit default HttpTransport und JsonFactory und gibt diesen zurück
	 *
	 * @return Pubsub client.
	 * @throws IOException wenn falschen Credentials genutzt wurden.
	 */
	public static Pubsub getClient() throws IOException {
		return getClient(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
	}

	/**
	 * Baut einen neuen PubSub-Client und gibt diesen zurück
	 *
	 * @param httpTransport HttpTransport für den Pubsub client.
	 * @param jsonFactory JsonFactory dür den Pubsub client.
	 * @return Pubsub client.
	 * @throws IOException wenn falschen Credentials genutzt wurden.ls.
	 */
	public static Pubsub getClient(final HttpTransport httpTransport, final JsonFactory jsonFactory)
			throws IOException {
		Preconditions.checkNotNull(httpTransport);
		Preconditions.checkNotNull(jsonFactory);
		GoogleCredential credential = GoogleCredential.getApplicationDefault();
		if (credential.createScopedRequired()) {
			credential = credential.createScoped(PubsubScopes.all());
		}
		// Please use custom HttpRequestInitializer for automatic
		// retry upon failures.
		HttpRequestInitializer initializer = new RetryHttpInitializerWrapper(credential);
		return new Pubsub.Builder(httpTransport, jsonFactory, initializer).setApplicationName(APPLICATION_NAME).build();
	}

	/**
	 * Gibt den Topic-Namen der Applikation zurück. 
	 *
	 * @return a topic name.
	 */
	public static String getAppTopicName() {
		return "topic-pubsub-api-appengine-sample";
	}
	
	public static String setAppTopicName(String AppTopic) {
		return AppTopic;
	}

	/**
	 * Gibt den Subscription-Namen der Applikation zurück. 
	 *
	 * @return a subscription name.
	 */
	public static String getAppSubscriptionName() {
		return "subscription-wim-devlab-ml";
	}

	/**
	 * Gibt die Endpoint-URL zurück.
	 *
	 * @return endpoint URL.
	 */
	public static String getAppEndpointUrl() {
		String subscriptionUniqueToken = System.getProperty(Constants.BASE_PACKAGE + ".subscriptionUniqueToken");

		return "https://" + getProjectId() + ".appspot.com/_ah/push-handlers/receive_message" + "?token="
				+ subscriptionUniqueToken;
	}

	/**
	 * Gibt die Project-Id zurück.
	 *
	 * @return the project ID.
	 */
	public static String getProjectId() {
		AppIdentityService identityService = AppIdentityServiceFactory.getAppIdentityService();

		// The project ID associated to an app engine application is the same
		// as the app ID.
		return identityService.parseFullAppId(ApiProxy.getCurrentEnvironment().getAppId()).getId();
	}
}
