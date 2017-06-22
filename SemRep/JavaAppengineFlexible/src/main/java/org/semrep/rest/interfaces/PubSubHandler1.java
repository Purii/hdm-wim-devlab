package org.semrep.rest.interfaces;

import com.google.gson.Gson;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.events.TokenEvent;
import de.hdm.wim.sharedLib.events.UserInformationEvent;
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
	name = "Push with PubSub " + Config.SEMREP_HANDLER_1,
	value = Config.PUSH_ENDPOINT_PREFIX + Config.SEMREP_HANDLER_1
)
public class PubSubHandler1 extends HttpServlet {

	private static final Logger LOGGER 	= Logger.getLogger(PubSubHandler1.class);
	private Helper helper 				= new Helper();

	//Pro Topic hier passiert die ganze action
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		String pubsubVerificationToken = Constants.PubSub.Config.SECRET_TOKEN;

		// Do not process message if request token does not match pubsubVerificationToken
		if (req.getParameter(RequestParameters.SECRET_TOKEN).compareTo(pubsubVerificationToken) != 0) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String requestBody = req.getReader()
			.lines()
			.reduce("\n", (accumulator, actual) -> accumulator + actual);

		IEvent event = helper.GetEventFromJson(requestBody);

		try {
			LOGGER.info("Handler: " + Config.SEMREP_HANDLER_1 + " event.getData(): " + event.getData());

			//Here we serialize the event to a String.
			final String output = new Gson().toJson(event);

			//And write the string to output
			resp.setContentLength(output.length());
			resp.getOutputStream().write(output.getBytes());
			resp.getOutputStream().flush();
			resp.getOutputStream().close();

			// Beispiel für UserInformationEvent
			if (event.getEventType().equals(Constants.PubSub.EventType.USER_INFO)){
				UserInformationEvent userInfEvent = (UserInformationEvent) event;
				userInfEvent.getEmail();
			}

			// 200, 201, 204, 102 status codes are interpreted as success by the Pub/Sub system = ACK
			resp.setStatus(HttpServletResponse.SC_OK);

			// NACK
			//resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
