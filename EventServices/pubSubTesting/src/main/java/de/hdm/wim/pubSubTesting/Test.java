package de.hdm.wim.pubSubTesting;

import de.hdm.wim.sharedLib.Constants.RequestParameters;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Created by ben on 7/06/2017.
 */
@WebServlet(
	name = "test",
	value = "/test"
)
public class Test extends HttpServlet {

	private static final Logger LOGGER 		= Logger.getLogger(Test.class);

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException{

		// get the parameters
		//String topicId 		= req.getParameter(RequestParameters.TOPIC);
		String payload 		= req.getParameter(RequestParameters.PAYLOAD);
		//String attributes 	= req.getParameter(RequestParameters.ATTRIBUTES);


		//System.out.println("topicId: " 		+ topicId);
		System.out.println("payload: " 		+ payload);
		//System.out.println("attributes: " 	+ attributes);

/*		Map<String, String> data = new HashMap<>();
		data.put("redirect", redirectURL);
		String json = new Gson().toJson(data);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);*/

		resp.sendRedirect("/");

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {
		doGet(req,resp);

	}
}
