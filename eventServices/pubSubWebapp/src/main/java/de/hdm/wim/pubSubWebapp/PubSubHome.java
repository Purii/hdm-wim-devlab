package de.hdm.wim.pubSubWebapp;

import de.hdm.wim.pubSubWebapp.Helper.EventRepository;
import de.hdm.wim.pubSubWebapp.Helper.EventRepositoryImpl;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.events.IEvent;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by ben on 4/06/2017.
 */
public class PubSubHome {

	private static final Logger LOGGER = Logger.getLogger(PubSubHome.class);
	private static EventRepository eventRepository = EventRepositoryImpl.getInstance();
	private static int MAX_MESSAGES = 10;


	private PubSubHome() {
	}

	/**
	 * Retrieve received messages in html.
	 *
	 * @return html representation of messages (one per row)
	 */
	public static String getReceivedMessages() {
		List<IEvent> eventList = eventRepository.retrieve(MAX_MESSAGES);

		return convertToHtmlTable(eventList);
	}

	/**
	 * Get list of topics string.
	 *
	 * @return the string
	 */
	public static String getListOfTopics(){
		List<String> topics = Topic.list;

		StringBuilder sb = new StringBuilder();
		for (String topic : topics) {
			sb.append("<option value=\"" + topic + "\">" + topic + "</option>");
		}
		return sb.toString();
	}

	private static String convertToHtmlTable(List<IEvent> events) {
		StringBuilder sb = new StringBuilder();
		for (IEvent event : events) {
			sb.append("<tr>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getId() + "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getData() + "</td>");
			sb.append(
				"<td class=\"mdl-data-table__cell--non-numeric\">" + event.getAttributesAsString()
					+ "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getPublishTime()
				+ "</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}
}