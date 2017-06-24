package org.semrep.rest.pubsub;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by ben on 22/06/2017.
 */
@WebServlet(
	name = "Push with PubSub " + Config.HANDLER_SEMREP_OFFERS,
	value = Config.PUSH_ENDPOINT_PREFIX + Config.HANDLER_SEMREP_OFFERS //Constants am Ende ändern pro Handler
)
public class PubSubHandler1 extends HttpServlet {

	private static final Logger LOGGER 	= Logger.getLogger(PubSubHandler1.class);
	private Helper helper 				= new Helper();

	//Pro Topic hier passiert die ganze action
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		LOGGER.info("Handler: " + Config.HANDLER_SEMREP_OFFERS);

		String pubsubVerificationToken = Constants.PubSub.Config.SECRET_TOKEN;

		// Do not process message if request token does not match pubsubVerificationToken
		if (req.getParameter(RequestParameters.SECRET_TOKEN).compareTo(pubsubVerificationToken) != 0) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String requestBody = req.getReader()
			.lines()
			.reduce("\n", (accumulator, actual) -> accumulator + actual);

		LOGGER.info(requestBody);

	//-	IEvent event = helper.convertToIEvent(requestBody);

		try {
		//-	LOGGER.info("Handler: " + Config.HANDLER_SEMREP_OFFERS + " event.getData(): " + event
		//		.getData());

			//Here we serialize the event to a String.
			//final String output = new Gson().toJson(event);

			// Beispiel für UserInformationEvent
			// aufruf methode getUserInformation hier: diese kann vom
			// return type her auch void haben und braucht keine annotations

			// inititalisierung Events:
//			LearnEvent learnEvent = new LearnEvent();
//			learnEvent.setData("test");
//			learnEvent.setDocumentId("");
//			learnEvent.setEventSource(EventSource.MACHINE_LEARNING);
//			learnEvent.setProjectId("test project id");
//			learnEvent.setDocumentAffiliation("false");
//			learnEvent.setUserId("test user id");
//			ph.Publish(learnEvent, Topic.TOPIC_1);



		//-	LOGGER.info("EventType: " + event.getEventType());
			/*if (event.getEventType().equals(Constants.PubSub.EventType.USER_INFO)){
				UserInformationEvent userInfEvent = (UserInformationEvent) event;
				userInfEvent.getEmail();
			}
*/
			// 200, 201, 204, 102 status codes are interpreted as success by the Pub/Sub system = ACK
			resp.setStatus(HttpServletResponse.SC_OK);

			// NACK
			//resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
