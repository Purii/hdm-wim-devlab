package de.hdm.wim.pubSubTesting;

import de.hdm.wim.sharedLib.Constants.PubSub.Topic;
import de.hdm.wim.sharedLib.events.Event;
import java.util.List;

/**
 * Created by ben on 4/06/2017.
 */
public class PubSubHome {

	private static MessageRepository messageRepository = MessageRepositoryImpl.getInstance();
	private static int MAX_MESSAGES = 10;

	private PubSubHome() {
	}

	/**
	 * Retrieve received messages in html.
	 *
	 * @return html representation of messages (one per row)
	 */
	public static String getReceivedEvents() {
		List<Event> eventList = messageRepository.retrieve(MAX_MESSAGES);
		return convertToHtmlTable(eventList);
	}

	public static String getListOfTopics(){
		List<String> topics = Topic.list;

		StringBuilder sb = new StringBuilder();
		for (String topic : topics) {
			sb.append("<option value=\"" + topic + "\">" + topic + "</option>");
		}
		return sb.toString();
	}

	private static String convertToHtmlTable(List<Event> events) {
		StringBuilder sb = new StringBuilder();
		for (Event event : events) {
			sb.append("<tr>");
			sb.append("<td>" + event.getId() + "</td>");
			//sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getTopic() + "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getData() + "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + event.getAttributes() + "</td>");
			sb.append("<td>" + event.getPublishTime() + "</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}
}