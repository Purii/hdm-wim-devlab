package de.hdm.wim.pubSubWebapp;

import de.hdm.wim.pubSubWebapp.Helper.PubSubPushHandler;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.TOPIC_1;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handler to get all topics, still WIP!
 *
 * @author Benedikt Benz
 * @createdOn 25.06.2017
 */
@WebServlet(
	name = "Push with PubSub catch them all",
	value = Config.PUSH_ENDPOINT_PREFIX + "/catchThemAll"
)
public class PubSubPushHandlerCatchThemAll extends HttpServlet {

	/**
	 * The Psh.
	 */
	PubSubPushHandler psh = new PubSubPushHandler();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		psh.process(req, resp, TOPIC_1.HANDLER_ID);
	}
}
