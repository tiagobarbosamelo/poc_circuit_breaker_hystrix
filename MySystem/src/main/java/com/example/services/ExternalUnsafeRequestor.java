package com.example.services;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalUnsafeRequestor {
	
	public String callExternalResource(){
		RestTemplate restTempalte = new RestTemplate();
		URI uri = URI.create("http://localhost:9090/external");
		return restTempalte.getForObject(uri, String.class);
	}

}
