package org.semrep.rest.pubsub;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.GUI_INFORMATION;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.SEMREP_OFFERS;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.AdditionalUserInformationEvent;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.semrep.rest.interfacesPubSub.EventInterface;

/**
 * Created by ben on 22/06/2017.
 *
 * Handler für publishen und pullen von Events für GUI-Topic Events
 * Von der Event-Gruppe bereitgestellt
 */
@WebServlet(
	name = "Push with PubSub " + GUI_INFORMATION.HANDLER_ID,
	value = Config.PUSH_ENDPOINT_PREFIX + GUI_INFORMATION.HANDLER_ID
)
public class GuiInformationHandler extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(GuiInformationHandler.class);
	private Helper helper 				= new Helper();

	//Pro Topic hier passiert die ganze action
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		LOGGER.info("Handler: " + GUI_INFORMATION.HANDLER_ID);

		String pubsubVerificationToken = Constants.PubSub.Config.SECRET_TOKEN;

		// Do not process message if request token does not match pubsubVerificationToken
		if (req.getParameter(RequestParameters.SECRET_TOKEN).compareTo(pubsubVerificationToken) != 0) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		LOGGER.info(req.toString());

		if (req.getReader() == null || req.getReader().toString() ==  "") {
			LOGGER.info("ist leer ");
		}

		if (req.toString() == null || req.toString().toString() ==  "") {
			LOGGER.info("ist leer 2 ");
		}

		String requestBody = req.getReader()
			.lines()
			.reduce("\n", (accumulator, actual) -> accumulator + actual);

		LOGGER.info(requestBody);



		try {
			IEvent event = helper
				.convertToIEvent(requestBody);
			LOGGER.info( "event Cast 1 ");

			LOGGER.info(event.toString() + "event Cast 1.2");


			AdditionalUserInformationEvent evt = new AdditionalUserInformationEvent();
			evt.setData(event.getData());

			//AdditionalUserInformationEvent evt = new AdditionalUserInformationEvent();
			LOGGER.info(evt.toString() + "event Cast 1.3");

			//evt = (AdditionalUserInformationEvent) event;

			LOGGER.info(event + "event Cast 2");

			EventInterface.insertNewUserInformation(evt);

			LOGGER.info("Handler: " + GUI_INFORMATION.HANDLER_ID + " event.getData(): " + evt
				.getData());




			LOGGER.info("EventType: " + event.getEventType());
			/*if (event.getEventType().equals(Constants.PubSub.EventType.USER_INFO)){
				UserInformationEvent userInfEvent = (UserInformationEvent) event;
				userInfEvent.getEmail();
			}
*/
			// 200, 201, 204, 102 status codes are interpreted as success by the Pub/Sub system = ACK
			resp.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			// NACK
			LOGGER.error(e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
