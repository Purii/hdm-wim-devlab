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

package de.hdm.wim.devlab.machinelearning.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.api.client.json.JsonParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.collect.ImmutableList;

import de.hdm.wim.devlab.machinelearning.appengine.Constants;
import de.hdm.wim.devlab.machinelearning.server.db.DBConnection;
import de.hdm.wim.devlab.machinelearning.util.PubsubUtils;


/**
 * 
 * Eingehende Nachrichten werden abgerufen und weiterverarbeiter. Diese Klasse stellt das Herz des Webservice da.
 * 
 */
@SuppressWarnings("serial")
public class ReceiveMessageServlet extends HttpServlet {

	private static final Publisher publisher = null;

	@SuppressWarnings({ "static-access" })
	@Override
	public final void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
		// Verifikation des Subscription-Tokens
		String subscriptionToken = System.getProperty(Constants.BASE_PACKAGE + ".subscriptionUniqueToken");
		if (!subscriptionToken.equals(req.getParameter("token"))) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().close();
			return;

		}

		ServletInputStream inputStream = req.getInputStream();

		// JSON Message wird auf das POJO Modell geparsed
		JsonParser parser = JacksonFactory.getDefaultInstance().createJsonParser(inputStream);
		parser.skipToKey("message");
		PubsubMessage message = parser.parseAndClose(PubsubMessage.class);

		// Speichern der Nachricht im Google-Cloud-Datastore
		Entity messageToStore = new Entity("PubsubMessage");
		messageToStore.setProperty("message", new String(message.decodeData(), "UTF-8"));
		messageToStore.setProperty("receipt-time", System.currentTimeMillis());
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(messageToStore);

		// Memcache leeren
		MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
		memcacheService.delete(Constants.MESSAGE_CACHE_KEY);

		// Im Erfolgsfall wird ein Successcode generiert
		resp.setStatus(HttpServletResponse.SC_OK);

		// DB Beschreibung siehe DBConnection()
		Connection con = DBConnection.connection();
		// Abruf der benötigten Variablen
		Entity msg = new Entity("PubsubMessage");
		Map<String, String> attr = message.getAttributes();
		String uId = (String) attr.get("userId");
		String dId = (String) attr.get("documentId");
		String dA = (String) attr.get("documentAffiliation");

		msg.setProperty("message", new String(message.decodeData(), "UTF-8"));
		String ms = (String) msg.getProperty("message");
		try {
			Statement stmt = con.createStatement();
			// SQL Statement um Input in die Datenbank zu kopieren
			stmt.executeUpdate("INSERT INTO testlearner (userId, documentId, message, documentAffiliation) VALUES ('"
					+ uId + "','" + dId + "','" + ms + "','" + dA
					+ "') ON DUPLICATE KEY UPDATE rate= (rate+1), favor = (IF((rate+1)>=5,'true',''))");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

				}
			}
			// Abruf der AzureRequest Klasse und Rückgabe des Ergebnisses
			
		        
		        HttpPost post;
		        HttpClient client;
		        StringEntity entity;
		        
		        try {
		        	
		        	
		        	
		        	
		        	//Finaler JSON String der die übergebenen Variablen an übernimmt.
		        	
		            String jsonBody = "{\n" +
		                    "  \"Inputs\": {\n" +
		                    "    \"input1\": {\n" +
		                    "      \"ColumnNames\": [\n" +
		                    "        \"userId\",\n" +
		                    "        \"documentId\"\n" +
		                    "      ],\n" +
		                    "      \"Values\": [\n" +
		        			"	[\n" +
		        			"        \""+uId+"\",\n" +
		        			"        \""+dId+"\"\n" +
		                    "      ]\n" +
		                    "      ]\n" +
		                    "    }\n" +
		                    "  },\n" +
		                    "  \"GlobalParameters\": {}\n" +
		                    "}";
		        	
		            // erzeugen eines HttpPost und eines HttpClient object
		            post = new HttpPost("");
		            client = HttpClientBuilder.create().build();
		            
		            // kopieren des JSON Body in die apache StringEntity
		            entity = new StringEntity(jsonBody, HTTP.UTF_8);
		            entity.setContentEncoding(HTTP.UTF_8);
		            entity.setContentType("text/json");
		            

		            // ahinzufügen des HTTP headers
		            post.setHeader("Accept", "text/json");
		            post.setHeader("Accept-Charset", "UTF-8");
		        
		            // setzen der Credentials
		            post.setHeader("Authorization", ("Bearer ""));
		            post.setEntity(entity);

		            // Aufruf der REST-Anfrage und Erhalt des Response 
		            HttpResponse authResponse = client.execute(post);
		            
		            String azureRemote = EntityUtils.toString(authResponse.getEntity());
		            
		        
		        
		    
			String feedback = azureRemote;
			StringBuilder sb = new StringBuilder();
			// Cleaning des HTTP-Strings
			feedback = feedback.replaceAll(",", "");
			feedback = feedback.replaceAll("}", "");
			feedback = feedback.replaceAll("\"", "");
			feedback = feedback.replaceAll("]", "");
			feedback = feedback.replaceAll(":", "");
			feedback = feedback.replace(".", "");
			feedback = feedback.substring(203);
			feedback = feedback.replaceAll("[0-9]", "");
			sb.append(feedback);
			// Mappen der Ergebnisse für die Output-Message
			//Map<String, String> feed = message.getAttributes();
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", uId.toString());
			map.put("documentId", dId.toString());
			map.put("favor", sb.toString());
			
			

			Pubsub client1 = PubsubUtils.getClient();

			// Publish message an Pubsub.
			PubsubMessage pubsubMessage = new PubsubMessage();
			String returnmessage = new String("LearnEvent");
			pubsubMessage.setAttributes(map);
			pubsubMessage.encodeData(returnmessage.getBytes("UTF-8"));

			String fullTopicName = String.format("projects/wim-devlab-ml/topics/pullml");

			PublishRequest publishRequest = new PublishRequest();
			publishRequest.setMessages(ImmutableList.of(pubsubMessage));
			client1.projects().topics().publish(fullTopicName, publishRequest).execute();
			try {
				publisher.shutdown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.getWriter().close();
		}
		        catch (Exception e) {
	                
	                e.toString();
	            }
	}

}
}	
	  

