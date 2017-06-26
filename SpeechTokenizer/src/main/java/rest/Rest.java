/**@author Emre Kesiciler, Nermin Hasani, Inci Koekpinar*/
package rest;

import com.google.gson.Gson;
import controller.CustomKeywordFilter;
import controller.ElasticsearchCommunication;
import controller.GoogleDriveCommunication;
import controller.Protocol;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.CEP_SESSIONINSIGHTS;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.GUI_FEEDBACK;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.ST_TOKEN;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.TokenEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import de.hdm.wim.sharedLib.pubsub.helper.PublishHelper;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.ElasticsearchResult;
import models.KeywordFilter;
import models.TextInformation;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

@Path("/rest")
public class Rest {
	final static Logger logger = Logger.getLogger(Rest.class);
	private static String sessionType = "";
	private static ArrayList<TextInformation> listTextinformations;
	private final Helper helper = new Helper();
	private ElasticsearchCommunication elasticsearchCommunication = new ElasticsearchCommunication();
	private CustomKeywordFilter customKeywordFilter = new CustomKeywordFilter();
	private Protocol protocol = new Protocol();
	private ArrayList<String> listEvent = null;
	
	/**
	* receiveCEPSessionInsightsPush 
	* 
	* Receive SessionStopEvent form the Complex-Event-Processing team. 
	* 
	* @param json JSON which we will receive from CEP.
	*
	* @return A Response-Object with the response-status 200 for success and 500 for no success.
	*  
	* @throws Exception 
	* 	The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	*   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
	*   of the method or constructor and propagate outside the method or constructor boundary.
	*/
	@POST
	@Path(Config.PUSH_ENDPOINT_PREFIX + CEP_SESSIONINSIGHTS.HANDLER_ID)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response receiveCEPSessionInsightsPush(String json) throws Exception {
		// TODO: change to TokenEvent in production
		IEvent event = helper.convertToIEvent(json);

		if(event.getEventType().equals("session-end")){
			sessionType = "end";
			String protocolName = protocol.createProtocol(listTextinformations);		
			GoogleDriveCommunication.saveProtocolOnGoogleDrive(protocolName);
		}
		
		// just to display converted event in response
		String eventJson = new Gson().toJson(event);

		// statuscode 200 to ACK messages, so pubsub knows they arrived and it does not have to re-send them
		if(true){
			return Response.status(200).entity(eventJson).build();
		}
		// statuscode 500 to NACK messages, so pubsub know sth. went wrong and the message has to be re-sent
		else{
			return Response.status(500).entity(eventJson).build();
		}
	}
	

	/**
	* receiveGUISessionInsightsPush 
	* 
	* Receive SessionStartEvent form the GUI-Team. 
	* 
	* @param json JSON which we will receive from GUI.
	*
	* @return A Response-Object with the response-status 200 for success and 500 for no success.
	* 
	* @throws Exception 
	* 	The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	*   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
	*   of the method or constructor and propagate outside the method or constructor boundary.
	* 
	*/
	@POST
	@Path(Config.PUSH_ENDPOINT_PREFIX + GUI_FEEDBACK.HANDLER_ID)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response receiveGUISessionInsightsPush(String json) throws Exception {
		// TODO: change to TokenEvent in production
		IEvent event = helper.convertToIEvent(json);
		
		if(event.getEventType().equals("session-start")){
			sessionType = "start";
			listTextinformations = new ArrayList<TextInformation>();
		} 
		
		// just to display converted event in response
		String eventJson = new Gson().toJson(event);

		// statuscode 200 to ACK messages, so pubsub knows they arrived and it does not have to re-send them
		if(true){
			return Response.status(200).entity(eventJson).build();
		}
		// statuscode 500 to NACK messages, so pubsub know sth. went wrong and the message has to be re-sent
		else{
			return Response.status(500).entity(eventJson).build();
		}
	}
	

	 /**
	 * receiveToken
	 * 
	 * This method is used to receive tokens and to start the tokenization
	 * 
	 * @param objToken
	 * 	A Token-Object with all tokeninformation. 
	 *
	 * @return A Response-Object with the token send status.
	 * 
	 * @throws Exception 
	 * 	The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	 *   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
	 *   of the method or constructor and propagate outside the method or constructor boundary.
	 * 
	 */
	@POST @Path("token")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response receiveToken(Object objToken) throws Exception {
		if(sessionType=="start"){			
			ObjectMapper mapper = new ObjectMapper();
			ElasticsearchResult elasticsearch = new ElasticsearchResult();
			ArrayList<String> listKontexte = new ArrayList<String>();

			if(logger.isInfoEnabled()){
				logger.info("Received Information from GUI: " + objToken);
			}

			//parse received json to JAVA-String
			String jsonInString = mapper.writeValueAsString(objToken);
			if(logger.isInfoEnabled()){
				logger.info("Parsed GUI-Information to JSON: " + jsonInString);
			}

			//Die jeweiligen Attribute des erhaltenen JSONS in ein JAVA-Objekt mappen
			TextInformation token = mapper.readValue(jsonInString, TextInformation.class);

			elasticsearch.setUserID(token.getUserID());
			elasticsearch.setSessionID(token.getSessionID());
			elasticsearch.setTimestamp(token.getTimestamp());

			listKontexte.add("Videokonferenz");
			elasticsearch.setListKontexte(listKontexte);

			PublishHelper ph = new PublishHelper(false);

			listTextinformations.add(token);
			try{
				if(ElasticsearchCommunication.checkExistingFilter() == true){
					TokenEvent tokenEvent 	= startTokenaziation(elasticsearch, token);
					String tokenEventString =  new Gson().toJson(tokenEvent);
					
					ph.Publish(tokenEvent, Topic.ST_TOKEN.TOPIC_ID, true);

					return Response.status(200).entity(tokenEventString).build();
				} else {
					//creating new Elasticsearch index with the custom filter
					ElasticsearchCommunication.createCustomFilter();

					TokenEvent tokenEvent 	= startTokenaziation(elasticsearch, token);
					String tokenEventString =  new Gson().toJson(tokenEvent);

					ph.Publish(tokenEvent, Topic.ST_TOKEN.TOPIC_ID, true);
			
					return Response.status(200).entity(tokenEventString).build();
				}
			}
			catch ( Exception e	){
				return Response.status(500).entity(e.toString()).build();
			}
			
		} else if(sessionType == "end"){
			Response.status(500).entity("session is ended").build();
		} 
		return Response.status(500).entity("no session found").build(); 
	}

	/**
	* startTokenaziation 
	* 
	* This method will start the tokenization with the elasticsearch-service and will check the custom keyword filter list.
	* Then custom filter list will delete keywords which are in filter from the textresult. After that elasticsearch will set
	* the tokenlist in an array.   
	*
	* @param elasticsearch	A ElasticsearchResult-Object with all tokeninformation which will be send to elasticsearch. 
	* 
	* @param token	A TextInformation-Object with all tokeninformation which will . 
	* 
	* @return A TextInformation-Object with all the informations which we received from GUI.
	*  
	* @throws Exception 
	* 	The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	*   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
	*   of the method or constructor and propagate outside the method or constructor boundary.
	*/
	public TokenEvent startTokenaziation(ElasticsearchResult elasticsearch, TextInformation token) throws Exception{
		TokenEvent tokenevent = new TokenEvent();

		KeywordFilter keywordfilter = customKeywordFilter.keywordFilter(token.getTextresultat());

		elasticsearch.setListTokens(elasticsearchCommunication.sendToElasticSearch(keywordfilter.getTokenWithoutKeywords()));
		ArrayList<String> listTokens = new ArrayList<String>(elasticsearch.getListTokens());
		listTokens.addAll(keywordfilter.getListFilteredKeywords());

		//creating jsonobject for response
		try {
			tokenevent = createTokenEventForResponse(elasticsearch, listTokens);
		} catch (Exception err) {
			logger.error("Could not parse JSONObject:"+err);
		}
		return tokenevent;
	}
	
	/**
	* createTokenEventForResponse 
	* 
	* This method 
	*
	* @param elasticsearch	A ElasticsearchResult-Object with all tokeninformation which will be send to elasticsearch. 
	* 
	* @param listTokens	A list with all tokeninformation. 
	* 
	* @return A TokenEvent-Object with all the informations which will be responsed.
	*  
	* @throws Exception 
	* 	The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	*   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
	*   of the method or constructor and propagate outside the method or constructor boundary.
	*/

	public TokenEvent createTokenEventForResponse(ElasticsearchResult elasticsearch, ArrayList<String> listTokens) throws Exception{
		TokenEvent tokenevent = new TokenEvent();
		tokenevent.setData("test123");
		tokenevent.setSessionId(elasticsearch.getSessionID());
		tokenevent.setUserId(elasticsearch.getUserID());
		//	tokenevent.put("timestamp", elasticsearch.getTimestamp());
		tokenevent.setContexts(elasticsearch.getListKontexte().toString());
		tokenevent.setTokens(listTokens.toString());
		return tokenevent;
	}
}
