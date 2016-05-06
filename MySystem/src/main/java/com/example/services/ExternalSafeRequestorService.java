package com.example.services;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ExternalSafeRequestorService {
	
	
	@HystrixCommand(fallbackMethod = "fallback")
	public ResponseEntity<String> safe(){
		RestTemplate restTempalte = new RestTemplate();
		URI uri = URI.create("http://localhost:9090/external");
		return new ResponseEntity<String>(restTempalte.getForObject(uri, String.class), HttpStatus.OK);
	}
	
	public ResponseEntity<String> fallback(){
		return new ResponseEntity<String>("Fail to request!", HttpStatus.NOT_FOUND);
	}

}
