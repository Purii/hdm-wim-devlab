/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hdm.wim.devlab.machinelearning.azure;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


/**
 * Diese Klasse dient dem Zugriff auf das maschinelle Lernmodell, welches auf der Azure-Cloud abgespeichert wurde.
 * Essentiell bei diesem Aufruf ist die Form des zu übergebenen JSON. Dieses muss exakt wie untern entwicklet genutzt werden.
 * 
 * @author Daniel Lepiorz
 */
public class AzureRequest {

    
    /**
     * Aufruf der REST API für die Übergabe von Wertanfragen an das Modell.
     * @return Antwort der REST API mit dem Wert true im Erfolgsfall oder false im negativen Fall.
     */	
    @SuppressWarnings("deprecation")
	public static String azureRemote() {
        
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
        			"        \""+uid+"\",\n" +
        			"        \""+did+"\"\n" +
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
            
            return EntityUtils.toString(authResponse.getEntity());
            
        }
        catch (Exception e) {
            
            return e.toString();
        }
    
    }
    
    /**
     * Command-Line-Ausgabe für Testzwecke.
     */
    public static void main(String[] args) {
      
		System.out.println(azureRemote());
        }
        
    }
    
