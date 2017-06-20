package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import models.Analysis;
import models.Analyzer;
import models.CustomAnalyzer;
import models.CustomFilter;
import models.Filter;
import models.Settings;
import models.Stemmer;
import models.Stop;
import models.Synonym;
import rest.Rest;

public class ElasticsearchCommunication {
	final static Logger logger = Logger.getLogger(Rest.class);
	
	//send Token to Elasticsearch to analyze
	public ArrayList<String> sendToElasticSearch(String token) throws JSONException, IOException{
		ArrayList<String> listTokens = new ArrayList<String>();
		try{
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
			
			logger.info("Send token to elasticsearch response: " + linie);
			
			JSONObject js = new JSONObject(linie);
			
			org.codehaus.jettison.json.JSONObject objTokenArray = js;

			JSONArray arr = objTokenArray.getJSONArray("tokens");
			
			
			for (int i=0; i<arr.length(); i++){
				org.codehaus.jettison.json.JSONObject objToken = arr.getJSONObject(i);
				listTokens.add(objToken.getString("token"));
			}	
				
			writer.close();
			reader.close();
			logger.info("Filtered Tokens from elasticsearch: " + listTokens);
		} catch(Exception err){
			logger.error("Could not send Text to elasticsearch: " + err);
		}
		return listTokens;	
	}
	
	/*
	 * Checking if the filter custom exists. If the filter did not exists than a new filter will be created
	 */
	public static Boolean checkExistingFilter() throws IOException, JSONException{	
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
			logger.error("Could not check existing Filter : " + err);
			return false;
		}
	}
	
	public static void createCustomFilter() throws JSONException, IOException{	
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
			
			logger.info("Setting custom Filter Response : " + linie);
		} catch(Exception err){
			logger.error("Could not set custom Filter : " + err);
		}
	}
}
