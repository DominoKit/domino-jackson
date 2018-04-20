package org.dominokit.jacksonapt.samples;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		GreetingRequest greetingRequest=GreetingRequest.MAPPER.read(input);

		GreetingResponse response = new GreetingResponse();

		response.setServerInfo(getServletContext().getServerInfo());
		response.setUserAgent(getThreadLocalRequest().getHeader("User-Agent"));

		response.setGreeting("Hello, " + greetingRequest.name+ "["+greetingRequest.age+"]"+"("+greetingRequest.random+")" + "!");

		return GreetingResponse.MAPPER.write(response);
	}
}
