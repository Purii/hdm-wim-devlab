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
 * The type PubSubPushHandler as abstract class, still WIP!
 *
 * @author Benedikt Benz
 * @createdOn 24.06.2017
 */
public abstract class PubSubPushHandlerTest extends HttpServlet {

	/**
	 * The constant LOGGER.
	 */
	protected static final Logger LOGGER = Logger.getLogger(PubSubPushHandlerTest.class);
	/**
	 * The constant HELPER.
	 */
	protected static Helper HELPER = new Helper();
	/**
	 * The constant EVENTREPOSITORY.
	 */
	protected static EventRepository EVENTREPOSITORY;
	/**
	 * The constant HANDLER.
	 */
	protected static String HANDLER;

	/**
	 * Instantiates a new Pub sub push handler test.
	 *
	 * @param eventRepository the event repository
	 * @param handlerId the handler id
	 */
	protected PubSubPushHandlerTest(EventRepository eventRepository, String handlerId) {
		EVENTREPOSITORY = eventRepository;
		HANDLER = handlerId;
	}

	/**
	 * Instantiates a new Pub sub push handler test.
	 */
	protected PubSubPushHandlerTest() {
		EVENTREPOSITORY = EventRepositoryImpl.getInstance();
		HANDLER = getClass().getAnnotation(WebServlet.class).value().toString();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {
		process(req, resp);
	}

	/**
	 * Process.
	 *
	 * @param req the req
	 * @param resp the resp
	 * @throws IOException the io exception
	 * @throws ServletException the servlet exception
	 */
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
