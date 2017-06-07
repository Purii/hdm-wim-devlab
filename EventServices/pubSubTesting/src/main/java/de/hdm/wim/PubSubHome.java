package de.hdm.wim;

import de.hdm.wim.sharedLib.classes.Message;
import java.util.List;

/**
 * Created by ben on 4/06/2017.
 */
public class PubSubHome {

	private static MessageRepository messageRepository = MessageRepositoryImpl.getInstance();
	private static int MAX_MESSAGES = 10;

	/**
	 * Retrieve received messages in html.
	 *
	 * @return html representation of messages (one per row)
	 */
	public static String getReceivedMessages() {
		List<Message> messageList = messageRepository.retrieve(MAX_MESSAGES);
		return convertToHtmlTable(messageList);
	}

	private static String convertToHtmlTable(List<Message> messages) {
		StringBuilder sb = new StringBuilder();
		for (Message message : messages) {
			sb.append("<tr>");
			sb.append("<td>" + message.getId() + "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + message.getTopic() + "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + message.getData() + "</td>");
			sb.append("<td class=\"mdl-data-table__cell--non-numeric\">" + message.getAttributes() + "</td>");
			sb.append("<td>" + message.getPublishTime() + "</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}

	private PubSubHome() { }
}