package de.hdm.wim.pubSubWebapp;

import de.hdm.wim.pubSubWebapp.Helper.PubSubPushHandler;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.TOPIC_2;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handler for Handler for {@link de.hdm.wim.sharedLib.Constants.PubSub.Topic.TOPIC_2#TOPIC_ID}
 *
 * @author Benedikt Benz
 * @createdOn 04.06.2017
 */
@WebServlet(
	name = "Push with PubSub " + TOPIC_2.HANDLER_ID,
	value = Config.PUSH_ENDPOINT_PREFIX + TOPIC_2.HANDLER_ID
)
public class PubSubPushHandler2 extends HttpServlet {

	/**
	 * Instatiate a new PubSubPushHandler
	 */
	PubSubPushHandler psh = new PubSubPushHandler();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		psh.process(req, resp, TOPIC_2.HANDLER_ID);
	}
}