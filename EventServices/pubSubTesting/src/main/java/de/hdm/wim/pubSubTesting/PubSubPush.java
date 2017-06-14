package de.hdm.wim.pubSubTesting;

import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.Event;
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
	name = "Push with PubSub",
	value = Config.PUSH_ENDPOINT
)
public class PubSubPush extends HttpServlet {

	private static final Logger LOGGER 	= Logger.getLogger(PubSubPush.class);
	private final Gson gson 			= new Gson();
	private final JsonParser jsonParser = new JsonParser();

	private MessageRepository messageRepository;

	PubSubPush(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public PubSubPush() {
		this.messageRepository = MessageRepositoryImpl.getInstance();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		LOGGER.info("doPost");

		String pubsubVerificationToken = Constants.PubSub.Config.SECRET_TOKEN;
		// Do not process message if request token does not match pubsubVerificationToken
		if (req.getParameter(RequestParameters.SECRET_TOKEN).compareTo(pubsubVerificationToken) != 0) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// parse message object from "message" field in the request body json
		// decode message data from base64
		Event event = getEvents(req);
		try {

			LOGGER.info(event.getData());

			messageRepository.save(event);
			// 200, 201, 204, 102 status codes are interpreted as success by the Pub/Sub system
			resp.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private Event getEvents(HttpServletRequest request) throws IOException {

		String requestBody 		= request.getReader().lines() .reduce("\n", (accumulator, actual) -> accumulator + actual);
		JsonElement jsonRoot 	= jsonParser.parse(requestBody);
		String eventStr 		= jsonRoot.getAsJsonObject().get("message").toString();
		Event event 			= gson.fromJson(eventStr, Event.class);

		// decode from base64
		String decoded = decode(event.getData());
		event.setData(decoded);
		return event;
	}

	/*
	private String encode(String data) throws Exception{
		byte[] byteString = data.getBytes(data);
		return new String(BaseEncoding.base64().encode(byteString));
	}
	*/

	private String decode(String data) {
		return new String(BaseEncoding.base64().decode(data));
	}
}