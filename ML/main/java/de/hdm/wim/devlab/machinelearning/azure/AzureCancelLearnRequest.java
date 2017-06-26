package de.hdm.wim.devlab.machinelearning.azure;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class AzureCancelLearnRequest {
	
	/**
     * Call REST API for canceling the batch job 
     * 
     * @param job job to be started 
     * @return response from the REST API
     */    
    public static String besCancelJob(String job) {
        HttpDelete post;
        HttpClient client;
        StringEntity entity;

        try {
            // create HttpPost and HttpClient object
            post = new HttpDelete(""+job);
            client = HttpClientBuilder.create().build();

            // add HTTP headers
            post.setHeader("Accept", "text/json");
            post.setHeader("Accept-Charset", "UTF-8");

            // set Authorization header based on the API key
            post.setHeader("Authorization", ("Bearer "+""));

            // Call REST API and retrieve response content
            HttpResponse authResponse = client.execute(post);

            if (authResponse.getEntity()==null)
            {
                return authResponse.getStatusLine().toString();
            }
            return EntityUtils.toString(authResponse.getEntity());

        }
        catch (Exception e) {

            return e.toString();
        }
    }

}
