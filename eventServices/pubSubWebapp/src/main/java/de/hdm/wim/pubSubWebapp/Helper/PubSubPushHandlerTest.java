package de.hdm.wim.pubSubWebapp.Helper;

import de.hdm.wim.sharedLib.Constants;
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
 * Created by ben on 24/06/2017.
 */
public abstract class PubSubPushHandlerTest extends HttpServlet {

	protected static final Logger LOGGER = Logger.getLogger(PubSubPushHandlerTest.class);
	protected static Helper HELPER = new Helper();
	protected static EventRepository EVENTREPOSITORY;
	protected static String HANDLER;

	protected PubSubPushHandlerTest(EventRepository eventRepository, String handlerId) {
		EVENTREPOSITORY = eventRepository;
		HANDLER = handlerId;
	}

	protected PubSubPushHandlerTest() {
		EVENTREPOSITORY = EventRepositoryImpl.getInstance();
		HANDLER = getClass().getAnnotation(WebServlet.class).value().toString();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {
		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		String pubsubVerificationToken = Constants.PubSub.Config.SECRET_TOKEN;

		// Do not process message if request token does not match pubsubVerificationToken
		if (req.getParameter(RequestParameters.SECRET_TOKEN).compareTo(pubsubVerificationToken)
			!= 0) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String requestBody = req.getReader()
			.lines()
			.reduce("\n", (accumulator, actual) -> accumulator + actual);

		IEvent event = HELPER.convertToIEvent(requestBody);

		try {
			LOGGER.info("Handler: " + HANDLER + " event.getData(): " + event.getData());

			EVENTREPOSITORY.save(event, HANDLER);

			// 200, 201, 204, 102 status codes are interpreted as success by the Pub/Sub system = ACK
			resp.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			// NACK
			LOGGER.error(e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
