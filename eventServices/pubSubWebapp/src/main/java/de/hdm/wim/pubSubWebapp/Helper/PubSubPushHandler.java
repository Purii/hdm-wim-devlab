package de.hdm.wim.pubSubWebapp.Helper;

import de.hdm.wim.sharedLib.Constants;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.IEvent;
import de.hdm.wim.sharedLib.helper.Helper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Created by ben on 24/06/2017.
 */
public class PubSubPushHandler {

	protected static final Logger LOGGER = Logger.getLogger(PubSubPushHandler.class);
	protected static Helper HELPER = new Helper();
	protected static EventRepository EVENTREPOSITORY;

	public PubSubPushHandler(EventRepository eventRepository) {
		this.EVENTREPOSITORY = eventRepository;
	}

	public PubSubPushHandler() {
		this.EVENTREPOSITORY = EventRepositoryImpl.getInstance();
	}

	public void process(HttpServletRequest req, HttpServletResponse resp, String handler)
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
			LOGGER.info("Handler: " + handler + " event.getData(): " + event.getData());

			EVENTREPOSITORY.save(event, handler);

			// 200, 201, 204, 102 status codes are interpreted as success by the Pub/Sub system = ACK
			resp.setStatus(HttpServletResponse.SC_OK);

		} catch (Exception e) {
			// NACK
			LOGGER.error(e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
