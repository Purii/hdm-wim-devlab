package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import rest.Rest;

public class Protocol {
	final static Logger logger = Logger.getLogger(Rest.class);
	public String createProtocol(int time, String sessionId, String textresultat, String userId){
		   //Unix seconds  
		   //convert seconds to milliseconds  
		   Date date = new Date(time*1000L);   
		   // format of the date  
		   SimpleDateFormat jdf = new SimpleDateFormat("MM-dd-yyyy_hh-mm");  
		   jdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));  
		   String java_date = jdf.format(date);  
		   String protocolname = "";
		   
		   Writer writer = null;

		   try {
			   Date newdate = new Date(time*1000L); 
			   SimpleDateFormat protocolDate = new SimpleDateFormat("MM.dd.yyyy hh:mm");  
			   protocolDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));  
			   String protocolTime = protocolDate.format(newdate);
			   String hoursform = "";
			   
			   if(date.getHours()<=12) {
				 hoursform = "AM";
			   }
			   else 
			   {			
				hoursform = "PM";
				System.out.println("PM");
			   }
			   
			   String separator = System.getProperty("line.separator");
		        //writer = new BufferedWriter(new OutputStreamWriter(
		        //     new FileOutputStream("/opt/logs/Session_"+sessionId+"_Date_"+java_date+".txt"), "utf-8"));
			   writer = new BufferedWriter(new OutputStreamWriter(
			           new FileOutputStream(sessionId+"Date"+java_date+".txt"), "utf-8"));
		       logger.info("Start writing protocol");
		       writer.write("Protocol");
		       writer.write(separator+""+separator+"SessionId:       "+sessionId);
		       writer.write(separator+""+separator+"Participants:    "+userId);
		       writer.write(separator+""+separator+"Date:            "+protocolTime +" "+hoursform);
		       writer.write(separator+""+separator+""+separator+"Meeting");
		       writer.write(separator+""+separator+""+userId+": "+textresultat);
		       protocolname = sessionId+"Date"+java_date+".txt";
	
		   } catch (Exception err) {
			   logger.error("Could not write Protocol: " + err);
		   } finally {
		      try {writer.close();} catch (Exception ex) { logger.error("Could not write Protocol: " + ex);}
		   }
		return protocolname;
	}
}
