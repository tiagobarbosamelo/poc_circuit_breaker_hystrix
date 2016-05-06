package com.example.command;

import java.net.URI;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;

public class MyCommand extends HystrixCommand<String> {

	
	public MyCommand(com.netflix.hystrix.HystrixCommand.Setter setter) {
		super(setter);
	}
	
	@Override
	protected String run() throws Exception {

		RestTemplate restTempalte = new RestTemplate();
		URI uri = URI.create("http://localhost:9090/external");
		return restTempalte.getForObject(uri, String.class);
	}

	@Override
	protected String getFallback() {
		return "Fallback in action!";
	}

}
