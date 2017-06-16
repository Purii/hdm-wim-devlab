package rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.spi.SyncFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.SAAJResult;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.omg.CORBA.CurrentHolder;

import models.Token;
import models.Synonym;
import models.Analysis;
import models.Analyzer;
import models.CustomAnalyzer;
import models.CustomFilter;
import models.ElasticsearchResult;
import models.Filter;
import models.KeywordFilter;
import models.Settings;
import models.Stemmer;
import models.Stop;
import models.TextInformation;

@Path("/rest")
public class Rest {

	@GET @Path("test")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findByName() throws JSONException, IOException {
		return Response.status(200).entity("test").build();
	}

	@POST @Path("token")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response receiveToken(Object objToken) throws JsonGenerationException, JsonMappingException, IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		
		//parse received json to JAVA-String
		String jsonInString = mapper.writeValueAsString(objToken);
		
		//Die jeweiligen Attribute des erhaltenen JSONS in ein JAVA-Objekt mappen
		TextInformation token = mapper.readValue(jsonInString, TextInformation.class);
		
		/*
		 
		//Zugriff auf den Textresult 
		System.out.println(token.getTextresultat());
		
		//Zugriff auf die userID
		System.out.println(token.getUserID());
		
		//Zugriff auf die sessionID 
		System.out.println(token.getSessionID());
		
		//Zugriff auf die timestamp 
		System.out.println(token.getTimestamp());
		
		sendToElasticSearch(token.getTextresultat());
		*/
		
		ElasticsearchResult elasticsearch = new ElasticsearchResult();
		elasticsearch.setUserID(token.getUserID());
		elasticsearch.setSessionID(token.getSessionID());
		elasticsearch.setTimestamp(token.getTimestamp());
		
		ArrayList<String> listKontexte = new ArrayList<String>();
		listKontexte.add("Videokonferenz");
		elasticsearch.setListKontexte(listKontexte);
		
		if(checkExistingFilter() == true){
			KeywordFilter keywordfilter = keywordFilter(token.getTextresultat());
			
			elasticsearch.setListTokens(sendToElasticSearch(keywordfilter.getTokenWithoutKeywords()));
			ArrayList<String> listTokens = new ArrayList<String>(elasticsearch.getListTokens());
			listTokens.addAll(keywordfilter.getListFilteredKeywords());
			
			System.out.println(listTokens.removeAll(Arrays.asList(null,"")));
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sessionID", elasticsearch.getSessionID());
			jsonObject.put("userID", elasticsearch.getUserID());

			jsonObject.put("timestamp", elasticsearch.getTimestamp());
			
			jsonObject.put("kontexts", elasticsearch.getListKontexte());
			jsonObject.put("tokens", listTokens);
			
			keywordFilter(token.getTextresultat());
			//System.out.println(keywordFilteredToken);
			
			//sendToGooglePubSub(jsonObject);
			return Response.status(200).entity(jsonObject.toString()).build();
		} else {
			customFilter();
			//String keywordFilteredToken = keywordFilter(token.getTextresultat());
			//System.out.println(keywordFilteredToken);
			
			//elasticsearch.setListTokens(sendToElasticSearch(keywordFilteredToken));
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("sessionID", elasticsearch.getSessionID());
			jsonObject.put("userID", elasticsearch.getUserID());

			jsonObject.put("timestamp", elasticsearch.getTimestamp());
			
			jsonObject.put("kontexts", elasticsearch.getListKontexte());
			jsonObject.put("tokens", elasticsearch.getListTokens());
			
			//sendToGooglePubSub(jsonObject);
			return Response.status(200).entity(jsonObject.toString()).build();
		}
	}
	
	
	//keywordfiltering
	private KeywordFilter keywordFilter(String textResultat){
        String csvFile = "keyword-list.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<String> listPrefilteredKeywords = new ArrayList<String>();
        ArrayList<String> listFilteredOutput = new ArrayList<String>();
        String newToken = "";
        int iteration = 0;
        String nt = "";
        
        KeywordFilter keywordfilter = new KeywordFilter();
        
        try {
        	 
            br = new BufferedReader(new FileReader(csvFile));
            String[] country = null;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                country = line.split(cvsSplitBy);
            }
            
            String receivedText = textResultat.toLowerCase();
         
            
            for(int i=0; i<country.length;i++){
            	
            	String csvString = country[i].toString().replace("\"", "").toLowerCase();
            	boolean isInList = textResultat.toLowerCase().contains(csvString);
            	
            	if(isInList==true){
            		if(iteration==0){
            			listPrefilteredKeywords.add(csvString);
            			System.out.println(listPrefilteredKeywords);
                		nt = receivedText.replace(csvString, "");
                		System.out.println(newToken);
            		} else {
            			listPrefilteredKeywords.add(csvString);
            			System.out.println(listPrefilteredKeywords);
            			nt = nt.replace(csvString, "");
            		}
            		iteration++;
            	}
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        keywordfilter.setListFilteredKeywords(listPrefilteredKeywords);
        keywordfilter.setTokenWithoutKeywords(nt);
        
        return keywordfilter;
	}
	
	
	private ArrayList<String> sendToElasticSearch(String token) throws JSONException, IOException{
			JSONObject body = new JSONObject();
			body.put("analyzer", "customAnalyzer");
			body.put("text", token);
			
			URL url = new URL( "http://localhost:9200/custom/_analyze" );
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod( "POST" );
			connection.setDoInput( true );
			connection.setDoOutput( true );
			connection.setUseCaches( false );
			connection.setRequestProperty( "Content-Type",
			                              "application/json" );
			connection.setRequestProperty( "Content-Length", String.valueOf(body.length()) );
			
			OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
			writer.write( body.toString() );
			writer.flush();
			
			
			BufferedReader reader = new BufferedReader(
			                         new InputStreamReader(connection.getInputStream()) );
			
			String linie = "";
			for ( String line; (line = reader.readLine()) != null; )
			{
			 linie = line;
			}

			
			ObjectMapper mapper = new ObjectMapper();
		  	
			//String jsonInString = mapper.writeValueAsString(linie);
			
			JSONObject js = new JSONObject(linie);
			
			org.codehaus.jettison.json.JSONObject objTokenArray = js;

			JSONArray arr = objTokenArray.getJSONArray("tokens");
			ArrayList<String> listTokens = new ArrayList<String>();
			
			for (int i=0; i<arr.length(); i++){
				org.codehaus.jettison.json.JSONObject objToken = arr.getJSONObject(i);
				listTokens.add(objToken.getString("token"));
			}	
				
			writer.close();
			reader.close();
			
			return listTokens;
	}
	
	private static Boolean checkExistingFilter() throws IOException, JSONException{
		
		try{
			URL url = new URL( "http://localhost:9200/custom" );
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod( "GET" );
			connection.setDoInput( true );
			connection.setDoOutput( true );
			connection.setUseCaches( false );
			connection.setRequestProperty( "Content-Type",
			                              "application/json" );

			BufferedReader reader = new BufferedReader(
			                         new InputStreamReader(connection.getInputStream()) );
			
			String linie = "";
			for ( String line; (line = reader.readLine()) != null; )
			{
			 linie = line;
			}
			reader.close();
			return true;
		}catch(Exception err){
			return false;
		}
		
	}
	private static void customFilter() throws JSONException, IOException{	
		Synonym synonym = new Synonym();
		Filter filter = new Filter();
		Stemmer stemmer = new Stemmer();
		Analyzer anaylzer = new Analyzer();
		Analysis analysing = new Analysis();
		ArrayList<String> listFilter = new ArrayList<String>();
		Stop stop = new Stop();
		CustomFilter customfilter = new CustomFilter();
		Settings settings = new Settings();
		CustomAnalyzer customAnalyzer = new CustomAnalyzer();
		
		//stop
		stop.setType("stop");
		stop.setStopwords_path("stopwords/stop.txt");
		
		//synonym
		synonym.setType("synonym");
		synonym.setSynonyms_path("analysis/synonym.txt");
		
		//stemmer
		stemmer.setType("stemmer");
		stemmer.setName("minimal_english");
		
		//filter
		filter.setMy_stemmer(stemmer);
		filter.setMy_synonym(synonym);
		filter.setMy_stop(stop);
		
		//customAnalyzer
		listFilter.add("lowercase");
		listFilter.add("my_stop");
		listFilter.add("my_stemmer");
		listFilter.add("my_synonym");
		customAnalyzer.setFilter(listFilter);
		customAnalyzer.setTokenizer("standard");
		customAnalyzer.setType("custom");

		//Analyzer
		anaylzer.setCustomAnalyzer(customAnalyzer);
		
		//Analysis
		analysing.setAnalyzer(anaylzer);
		analysing.setFilter(filter);
		
		//Settings
		settings.setAnalysis(analysing);

		customfilter.setSettings(settings);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(customfilter);
		
		System.out.println(jsonInString);
		
		try{		
			URL url = new URL( "http://localhost:9200/custom" );
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod( "PUT" );
			connection.setDoInput( true );
			connection.setDoOutput( true );
			connection.setUseCaches( false );
			connection.setRequestProperty( "Content-Type",
			                              "application/json" );
			connection.setRequestProperty( "Content-Length", String.valueOf(jsonInString.length()) );
			
			OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
			writer.write( jsonInString.toString() );
			writer.flush();
				
			BufferedReader reader = new BufferedReader(
			                         new InputStreamReader(connection.getInputStream()) );
			
			String linie = "";
			for ( String line; (line = reader.readLine()) != null; )
			{
			 linie = line;
			}
				
			writer.close();
			reader.close();
			
			System.out.println(linie);
		} catch(Exception err){
			System.out.println(err);
		}
		
	}
}
