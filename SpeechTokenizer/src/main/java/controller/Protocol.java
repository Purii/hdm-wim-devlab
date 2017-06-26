/**@author Emre Kesiciler, Nermin Hasani, Inci Koekpinar*/


package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.objectweb.asm.Label;

import com.google.api.client.auth.oauth2.TokenErrorResponse;

import models.TextInformation;
import rest.Rest;

public class Protocol {
	final static Logger logger = Logger.getLogger(Protocol.class);
	
    /**
	* createProtocol 
	* 
	* This method will create the protocol.
	* 
	* @return The protocolname of the created protocol.
	* 
	* @param listTokens An ArrayList with an TextInformation-Object.
	* 
	*/
	public String createProtocol(ArrayList<TextInformation> listTokens){
		ArrayList<String> listParticipants = new ArrayList<String>();
		
		   int startTime = 0;
		   int iteration = 0;
		   int endTime = 0;
		   
		   for(TextInformation token : listTokens){
			   if(iteration==0){
				   startTime = token.getTimestamp();
			   } else {
				   break;
			   }
			   iteration++;  
		   }
		   
		   for(TextInformation token : listTokens){
				   endTime = token.getTimestamp();
		   }
		   
		   Date date = new Date(startTime*1000L);   
		   // format of the date  
		   SimpleDateFormat jdf = new SimpleDateFormat("MM-dd-yyyy_hh-mm");  
		   jdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));  
		   String java_date = jdf.format(date);  
		   String protocolname = "";
		   String sessionId = "";
		   
		   for(TextInformation token : listTokens){
			   sessionId = token.getSessionID();
		   }
		   
		   Writer writer = null;

		   try {
			   String protocolStartTime = parseDate(startTime);
			   String protocolEndTime = parseDate(endTime);
			   String hoursform = parseToAMAndPM(new Date(startTime*1000L));
			   
			   
			   String separator = System.getProperty("line.separator");
		        //writer = new BufferedWriter(new OutputStreamWriter(
		        //     new FileOutputStream("/opt/logs/Session_"+sessionId+"_Date_"+java_date+".txt"), "utf-8"));
			   
		       writer = new BufferedWriter(new OutputStreamWriter(
		            new FileOutputStream("/opt/speech/protocol/Session_"+sessionId+"_Date_"+java_date+".txt"), "utf-8"));
			   
		       //writer = new BufferedWriter(new OutputStreamWriter(
			   //        new FileOutputStream("Session_"+sessionId+"_Date_"+java_date+".txt"), "utf-8"));
		       
		       System.out.println("end");
			   logger.info("Start writing protocol");
		       writer.write("Protocol");
		    
		       writer.write(separator+""+separator+"SessionId:        "+sessionId);
		       
			   for(TextInformation participants : listTokens){
					listParticipants.add(participants.getUserID());	
			   }
			   Set<String> uniqueListPrlistParticipants = new LinkedHashSet<String>(listParticipants);
			   
			   writer.write(separator+""+separator+"Participants:     "+uniqueListPrlistParticipants.toString().replace("[", "").replace("]", ""));
		       writer.write(separator+""+separator+"Meeting start:     "+protocolStartTime +" "+hoursform);
		       writer.write(separator+""+separator+"Meeting end:       "+protocolEndTime+" "+hoursform); 
		       writer.write(separator+""+separator+""+separator+"Meeting");
		       
				for(TextInformation entry : listTokens){
					writer.write(separator+""+separator+""+entry.getUserID()+": "+entry.getTextresultat());	
				}
		       
		       protocolname = "Session_"+sessionId+"_Date_"+java_date+".txt";
	
		   } catch (Exception err) {
			   logger.error("Could not write Protocol: " + err);
		   } finally {
		      try {writer.close();} catch (Exception ex) { logger.error("Could not write Protocol: " + ex);}
		   }
		return protocolname;
	}
	
    /**
	* parseToAMAndPM 
	* 
	* This method will change the hoursform to am or pm.
	* 
	* @return The hoursform.
	* 
	* @param date an Date-Object.
	* 
	*/
	private String parseToAMAndPM(Date date) {
		String hoursform = "";   
		if(date.getHours()<=12) {
			 hoursform = "AM";
		}
		else 
		{			
		    hoursform = "PM";
		}
		return hoursform; 
	}

    /**
	* parseDate 
	* 
	* This method will parse unix time to normal date.
	* 
	* @return The convertedtime from the unix time.
	* 
	* @param unixTime The unix time.
	* 
	*/
	private String parseDate(int unixTime) {
		Date newdate = new Date(unixTime*1000L); 
		SimpleDateFormat protocolDate = new SimpleDateFormat("MM.dd.yyyy hh:mm");  
		protocolDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));  
		String convertedTime = protocolDate.format(newdate);
		return convertedTime;
	}
}
