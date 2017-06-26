/**@author Emre Kesiciler, Nermin Hasani, Inci Kökpinar*/
package controller;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;

import rest.Rest;

import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

public class GoogleDriveCommunication {
	final static Logger logger = Logger.getLogger(GoogleDriveCommunication.class);
	/** Application name. */
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    /** Directory to store user credentials for this application. */
    //private static final java.io.File DATA_STORE_DIR = new java.io.File(
    //        System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    //linux
    private static final java.io.File DATA_STORE_DIR = new java.io.File("/opt/logs");
    	
    	
    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_FILE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**authorize
     * 
     * Creates an authorized Credential object.
     * 
     * @return an authorized Credential object.
     * 
     * @throws Exception  The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	 *   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
 	 *   of the method or constructor and propagate outside the method or constructor boundary.
     */
    public static Credential authorize() throws Exception {
        // Load client secrets.
        InputStream in =
            GoogleDriveCommunication.class.getResourceAsStream("client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        
        logger.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**getDriveService
     * 
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * 
     * @throws Exception  The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	 *   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
 	 *   of the method or constructor and propagate outside the method or constructor boundary.
     */
    public static Drive getDriveService() throws Exception {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    
    /**
	* saveProtocolOnGoogleDrive 
	* 
	* This method will upload the protocol to google drive. 
	* 
	* @param protocolName The protocol name which for the protocol which will be uploaded to google drive.
	* 
	* @throws Exception 
	* 	The class Exception and any subclasses that are not also subclasses of RuntimeException are checked exceptions. 
	*   Checked exceptions need to be declared in a method or constructor's throws clause if they can be thrown by the execution 
	*   of the method or constructor and propagate outside the method or constructor boundary.
	*/
    public static void saveProtocolOnGoogleDrive(String protocolName) throws Exception{
    	try{
    		Drive service = getDriveService();   
            logger.info("Start writing protocol:" +protocolName);
            String folderId = "0B6rkNNvwifY2WTBRSEl2dmpwd28";
            File fileMetadata = new File();
            fileMetadata.setName(protocolName);
            fileMetadata.setMimeType("application/vnd.google-apps.document");
            fileMetadata.setParents(Collections.singletonList(folderId));
            java.io.File filePath = new java.io.File("/opt/speech/protocol/Session_12ss34dd5_Date_06-25-2017_10-33.txt");
            logger.info("Trying to upload file:"+ filePath);
            FileContent mediaContent = new FileContent("application/text/plain", filePath);
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, parents")
                    .execute();
            logger.info("Protocol created:" +file.getId());
    	} catch(Exception err){
    		logger.info("Protocol creation error:" +err);
    	}
    	
    }
    
}
