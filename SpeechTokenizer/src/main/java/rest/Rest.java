
package rest;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.CustomKeywordFilter;
import controller.ElasticsearchCommunication;
import controller.GoogleDriveCommunication;
import controller.Protocol;
import models.ElasticsearchResult;
import models.KeywordFilter;
import models.TextInformation;

@Path("/rest")
public class Rest {
	final static Logger logger = Logger.getLogger(Rest.class);
	private ElasticsearchCommunication elasticsearchCommunication = new ElasticsearchCommunication();
	private CustomKeywordFilter customKeywordFilter = new CustomKeywordFilter();
	private Protocol protocol = new Protocol();
	
	@GET @Path("test")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findByName() throws JSONException, IOException {
		return Response.status(200).entity("test").build();
	}
	
	@POST @Path("token")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response receiveToken(Object objToken) throws Exception {		
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
		
		if(ElasticsearchCommunication.checkExistingFilter() == true){
			JSONObject jsonObject = startTokenaziation(elasticsearch, token);
			return Response.status(200).entity(jsonObject.toString()).build();
		} else {
			//creating new Elasticsearch index with the custom filter
			ElasticsearchCommunication.createCustomFilter();
			
			JSONObject jsonObject = startTokenaziation(elasticsearch, token);

			//sendToGooglePubSub(jsonObject);
			return Response.status(200).entity(jsonObject.toString()).build();
		}
	}
	
	public JSONObject startTokenaziation(ElasticsearchResult elasticsearch, TextInformation token) throws Exception{
		JSONObject jsonObject = new JSONObject();
		GoogleDriveCommunication googleDriveCommunication = new GoogleDriveCommunication();
		
		KeywordFilter keywordfilter = customKeywordFilter.keywordFilter(token.getTextresultat());
		
		elasticsearch.setListTokens(elasticsearchCommunication.sendToElasticSearch(keywordfilter.getTokenWithoutKeywords()));
		ArrayList<String> listTokens = new ArrayList<String>(elasticsearch.getListTokens());
		listTokens.addAll(keywordfilter.getListFilteredKeywords());
		
		//creating jsonobject for response
		try {
			jsonObject = createJSONObjectForResponse(elasticsearch, listTokens);
		} catch (Exception err) {
			logger.error("Could not parse JSONObject:"+err);
		}
		
		//creating the protocol
		String protocolname = protocol.createProtocol(token.getTimestamp(), token.getSessionID(), token.getTextresultat(), token.getUserID());
		
		//uploading the document to google drive
		GoogleDriveCommunication.saveProtocolOnGoogleDrive(protocolname);
		//*TODO Upload Google Drive
		//googleDriveCommunication.uploadToGoogleDrive();
		return jsonObject;
	}
	
	public JSONObject createJSONObjectForResponse(ElasticsearchResult elasticsearch, ArrayList<String> listTokens) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sessionID", elasticsearch.getSessionID());
		jsonObject.put("userID", elasticsearch.getUserID());
		jsonObject.put("timestamp", elasticsearch.getTimestamp());
		jsonObject.put("kontexts", elasticsearch.getListKontexte());
		jsonObject.put("tokens", listTokens);
		return jsonObject;
	}
}
