package de.hdm.wim.pubSubWebapp;

import de.hdm.wim.pubSubWebapp.Helper.PubSubPushHandler;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.PubSub.Topic.CEP_INSIGHT;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ben on 4/06/2017.
 */
@WebServlet(
	name = "Push with PubSub " + CEP_INSIGHT.HANDLER_ID,
	value = Config.PUSH_ENDPOINT_PREFIX + CEP_INSIGHT.HANDLER_ID
)
public class PubSubPushHandlerCepInsight extends HttpServlet {

	PubSubPushHandler psh = new PubSubPushHandler();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		psh.process(req, resp, CEP_INSIGHT.HANDLER_ID);
	}
}