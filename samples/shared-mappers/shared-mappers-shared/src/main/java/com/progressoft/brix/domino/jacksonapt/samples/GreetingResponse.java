package com.progressoft.brix.domino.jacksonapt.samples;

import com.progressoft.brix.domino.jacksonapt.annotation.JSONMapper;

import java.io.Serializable;

@JSONMapper
public class GreetingResponse{

	public static GreetingResponse_MapperImpl MAPPER=new GreetingResponse_MapperImpl();

	private String greeting;
	private String serverInfo;
	private String userAgent;

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}