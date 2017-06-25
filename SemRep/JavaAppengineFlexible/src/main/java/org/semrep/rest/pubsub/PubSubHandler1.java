package org.semrep.rest.pubsub;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.SEMREP_OFFERS;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Created by ben on 22/06/2017.
 */
@WebServlet(
	name = "Push with PubSub " + SEMREP_OFFERS.HANDLER_ID,
	value = Config.PUSH_ENDPOINT_PREFIX + SEMREP_OFFERS.HANDLER_ID
)
public class PubSubHandler1 extends HttpServlet {

	private static final Logger LOGGER 	= Logger.getLogger(PubSubHandler1.class);
	private Helper helper 				= new Helper();

	//Pro Topic hier passiert die ganze action
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		LOGGER.info("Handler: " + SEMREP_OFFERS.HANDLER_ID);

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

		IEvent event = helper.convertToIEvent(requestBody);

		try {
			LOGGER.info("Handler: " + SEMREP_OFFERS.HANDLER_ID + " event.getData(): " + event
				.getData());

			//Here we serialize the event to a String.
			//final String output = new Gson().toJson(event);

			// Beispiel für UserInformationEvent
			// aufruf methode getUserInformation hier: diese kann vom
			// return type her auch void haben und braucht keine annotations




			LOGGER.info("EventType: " + event.getEventType());
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
