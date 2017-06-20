package de.hdm.wim.pubSubWebapp;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.Event;
import de.hdm.wim.sharedLib.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Created by ben on 4/06/2017.
 */
@WebServlet(
	name = "Push with PubSub " + Config.HANDLER_2,
	value = Config.PUSH_ENDPOINT_PREFIX + Config.HANDLER_2
)
public class PubSubPushHandler2 extends HttpServlet {

	private static final Logger LOGGER 	= Logger.getLogger(PubSubPushHandler2.class);
	private final Gson gson 			= new Gson();
	private final JsonParser jsonParser = new JsonParser();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		String pubsubVerificationToken = Config.SECRET_TOKEN;

		// Do not process message if request token does not match pubsubVerificationToken
		if (req.getParameter(RequestParameters.SECRET_TOKEN).compareTo(pubsubVerificationToken) != 0) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		Event event = getEvent(req);

		try {
			LOGGER.info("Handler: " + Config.HANDLER_2);
			LOGGER.info("event.getData(): " + event.getData());

			//Here we serialize the event to a String.
			final String output = new Gson().toJson(event);

			//And write the string to output
			resp.setContentLength(output.length());
			resp.getOutputStream().write(output.getBytes());
			resp.getOutputStream().flush();
			resp.getOutputStream().close();

			// 200, 201, 204, 102 status codes are interpreted as success by the Pub/Sub system = ACK
			//resp.setStatus(HttpServletResponse.SC_OK);

			// NACK
			//resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private Event getEvent(HttpServletRequest request) throws IOException {
		Helper helper = new Helper();

		// parse message object from "message" field in the request body json
		String requestBody 		= request.getReader().lines() .reduce("\n", (accumulator, actual) -> accumulator + actual);
		JsonElement jsonRoot 	= jsonParser.parse(requestBody);
		String eventStr 		= jsonRoot.getAsJsonObject().get("message").toString();
		Event event 			= gson.fromJson(eventStr, Event.class);

		// decode from base64
		String decoded = helper.decodeBase64(event.getData());
		event.setData(decoded);
		return event;
	}
}