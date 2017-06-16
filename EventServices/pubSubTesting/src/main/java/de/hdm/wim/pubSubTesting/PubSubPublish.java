package de.hdm.wim.pubSubTesting;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.spi.v1.Publisher;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import de.hdm.wim.sharedLib.Constants.PubSub.Config;
import de.hdm.wim.sharedLib.Constants.RequestParameters;
import de.hdm.wim.sharedLib.pubsub.helper.TopicHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

/**
 * Created by ben on 4/06/2017.
 */
@WebServlet(
	name = "Publish with PubSub",
	value = Config.PUBLISH_ENDPOINT
)
public class PubSubPublish extends HttpServlet {

	private static final Logger LOGGER 		= Logger.getLogger(PubSubPublish.class);

	private Publisher publisher;

	public PubSubPublish() { }

	public PubSubPublish(Publisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {

		Publisher publisher 					= this.publisher;
		HashMap<String, Boolean> responseToAjax = new HashMap<>();
		Gson gson 								= new Gson();

		try {
			// get the parameters
			String topicId 						= req.getParameter(RequestParameters.TOPIC);
			String payload 						= req.getParameter(RequestParameters.PAYLOAD);
			Enumeration<String> parameterNames 	= req.getParameterNames();

			Map<String,String> attributes 	= new HashMap<>();
			List<String> keyNames 			= new ArrayList<>();
			List<String> valueNames			= new ArrayList<>();

			while(parameterNames.hasMoreElements()){

				String paramName 		= parameterNames.nextElement();
				String[] paramValues 	= req.getParameterValues(paramName);

				if(paramName.equals(RequestParameters.ATTRIBUTE_KEY))
					for (int i = 0; i < paramValues.length; i++)
						keyNames.add(paramValues[i]);

				if(paramName.equals(RequestParameters.ATTRIBUTE_VALUE))
					for (int i = 0; i < paramValues.length; i++)
						valueNames.add(paramValues[i]);
			}

			// fill in attributes
			if(keyNames.size() == valueNames.size()) {
				for (int i = 0; i < keyNames.size(); i++) {

					LOGGER.info("keyNames.get(" + i + ") - " + keyNames.get(i));
					LOGGER.info("valueNames.get(" + i + ") - " + valueNames.get(i));

					attributes.put(
						keyNames.get(i),
						valueNames.get(i)
					);
				}
			}

			String attrJson = gson.toJson(attributes);
			LOGGER.info(attrJson);

			// make sure topic exists
			TopicHelper th = new TopicHelper();
			th.createTopicIfNotExists(topicId);

			// create a publisher on the topic
			if (publisher == null) {
				publisher = Publisher.defaultBuilder(
					TopicName.create(ServiceOptions.getDefaultProjectId(), topicId))
					.build();
			}

			// construct a pubsub message
			PubsubMessage pubsubMessage;
			if(attributes.isEmpty()){
				pubsubMessage =
					PubsubMessage.newBuilder()
						.setData(ByteString.copyFromUtf8(payload))
						.build();
			}else {
				pubsubMessage =
					PubsubMessage.newBuilder()
						.setData(ByteString.copyFromUtf8(payload))
						.putAllAttributes(attributes)
						.build();
			}

			publisher.publish(pubsubMessage);

			// redirect to home page
			resp.sendRedirect("/");

			responseToAjax.put("success", true);
			String jsonResponse = new Gson().toJson(responseToAjax);

			LOGGER.info(jsonResponse);

			// send success response
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(jsonResponse);

		} catch (Exception e) {
			resp.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			LOGGER.error(e.getMessage());

			responseToAjax.put("success", false);
			String jsonResponse = new Gson().toJson(responseToAjax);

			LOGGER.info(jsonResponse);

			// send error response
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(jsonResponse);
		}
	}

	private String validateParameter(HttpServletRequest request, String parameterName, String defaultValue){

		if(request.getParameter(parameterName) != null)
			return request.getParameter(parameterName);
		else
			return defaultValue;
	}
}