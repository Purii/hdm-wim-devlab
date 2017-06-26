/**@author Emre Kesiciler, Nermin Hasani, Inci Kökpinar*/
package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;

import models.KeywordFilter;
import rest.Rest;

public class CustomKeywordFilter {
	final static Logger logger = Logger.getLogger(Rest.class);
	
	  /**keywordFilter
	  * 
	  * This method will be used to filter the custom keywords which are in the custom-keywordlist. The problem is, that
	  * elasticsearch seperates keywords for example "New York" in "New, York". This means, that the keyword is loosing it´s 
	  * meaning.  
	  *
	  * @param textResultat
	  * 	The nonfiltered Textresultat. 
	  * 
	  * @return A KeywordFilter-Object with the filtered keywords and the new filtered token.
	  *
	  */
		public KeywordFilter keywordFilter(String textResultat){
			/*Linux-Path please uncomment when uplaoding to the server*/
	        //String csvFile = "/opt/keyword-list/keyword-list.csv";
			
			String csvFile = "/opt/speech/keyword-list/keyword-list.csv";
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
	                		nt.trim();
	            		} else {
	            			listPrefilteredKeywords.add(csvString);
	            			nt = nt.replace(csvString, "");
	            			nt.trim();
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
	        ArrayList<String> dedupped = new ArrayList<String>(new LinkedHashSet<String>(listPrefilteredKeywords));
	        keywordfilter.setListFilteredKeywords(dedupped);
	        
	        
	        if(nt != ""){
	        	keywordfilter.setTokenWithoutKeywords(duplicateFilter(nt).toString());
	        } else {
	        	 keywordfilter.setTokenWithoutKeywords(duplicateFilter(textResultat).toString());
	        }
	       
	        
	        logger.info("Filtered Keywords from keyword-list.csv: " +  keywordfilter.getListFilteredKeywords());
	        logger.info("Token without Keywords from keyword-list.csv: " +  keywordfilter.getTokenWithoutKeywords());
	        return keywordfilter;
		}
		
		
		/**duplicateFilter
		* 
		* This method will remove all double keywords.  
		*
		* @param token The keywords which are double and should be checked for duplicates.
		* 
		* @return A ArrayList with the filtered keywords and the new filtered token.
		*
		*/
		// static void a(Int id)
		public static ArrayList<String> duplicateFilter(String token)
		{
			// split text to array of words
			String[] words = token.split("\\s");
	 
			// clean duplicates
			for (int i = 0; i < words.length; i++)
			{
				for (int j = 0; j < words.length; j++)
				{
					if (words[i].equals(words[j]))
					{
						if (i != j)
							words[i] = "";
					}
				}
			}
	 
			// show the output
			ArrayList<String> listTokens = new ArrayList<String>();
			for (int i = 0; i < words.length; i++)
			{
	 
				if (words[i] != "")
				{
					listTokens.add(words[i]+" ");
	 
				}
			}
			return listTokens;
		}
}
