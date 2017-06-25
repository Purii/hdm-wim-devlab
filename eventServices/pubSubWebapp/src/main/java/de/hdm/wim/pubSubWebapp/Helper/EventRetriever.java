package de.hdm.wim.pubSubWebapp.Helper;

import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.events.IEvent;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Created by ben on 25/06/2017.
 */

@WebServlet(
	name = "MessageRetreiver",
	value = "/retrieveMessage"
)
public class EventRetriever extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(EventRetriever.class);

	private static EventRepository EVENTREPOSITORY;

	public EventRetriever(EventRepository eventRepository) {
		EVENTREPOSITORY = eventRepository;
	}

	public EventRetriever() {
		EVENTREPOSITORY = EventRepositoryImpl.getInstance();
	}

	private static String convertToHtmlTable(List<IEvent> events) {
		StringBuilder sb = new StringBuilder();
		for (IEvent event : events) {
			sb.append("<tr>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getId() + "</td>");
			sb.append(
				"<td class=\"mdl-data-table__cell--non-numeric\">" + event.getData() + "</td>");
			sb.append(
				"<td class=\"mdl-data-table__cell--non-numeric\">" + event.getAttributesAsString()
					+ "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getPublishTime()
				+ "</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		process(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		String topicId = req.getParameter(RequestParameters.TOPIC);

		List<IEvent> events = EVENTREPOSITORY.retrieve(10, topicId);

		String result = convertToHtmlTable(events);
	}
}
