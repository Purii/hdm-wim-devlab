package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import models.KeywordFilter;
import rest.Rest;

public class CustomKeywordFilter {
	final static Logger logger = Logger.getLogger(Rest.class);
	
		public KeywordFilter keywordFilter(String textResultat){
			/*Linux-Path please uncomment when uplaoding to the server*/
	        String csvFile = "/opt/keyword-list/keyword-list.csv";
			
			//String csvFile = "WebContent/keyword-list.csv";
			
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        ArrayList<String> listPrefilteredKeywords = new ArrayList<String>();
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
	                		nt = receivedText.replace(csvString, "");
	            		} else {
	            			listPrefilteredKeywords.add(csvString);
	            			nt = nt.replace(csvString, "");
	            		}
	            		iteration++;
	            	}
	            }
	        } catch (FileNotFoundException e) {
	        	logger.error("Could not filter from keyword-list.csv: " +  e);
	        } catch (IOException e) {
	        	logger.error("Could not filter from keyword-list.csv: " +  e);
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        listPrefilteredKeywords.removeAll(Arrays.asList("", null));
	        keywordfilter.setListFilteredKeywords(listPrefilteredKeywords);
	        keywordfilter.setTokenWithoutKeywords(nt);
	        
	        logger.info("Filtered Keywords from keyword-list.csv: " +  keywordfilter.getListFilteredKeywords());
	        logger.info("Token without Keywords from keyword-list.csv: " +  keywordfilter.getTokenWithoutKeywords());
	        return keywordfilter;
		}
}
