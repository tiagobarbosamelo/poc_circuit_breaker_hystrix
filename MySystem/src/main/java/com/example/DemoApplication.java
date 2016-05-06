package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.command.MyCommand;
import com.example.services.ExternalSafeRequestorService;
import com.example.services.ExternalUnsafeRequestor;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.Setter;

@EnableCircuitBreaker
@EnableHystrixDashboard
@RestController
@SpringBootApplication
public class DemoApplication {
	
	@Autowired
	private ExternalUnsafeRequestor externalUnsafeRequestorService;
	
	@Autowired
	private ExternalSafeRequestorService externalSafeRequestorService;
	
	private MyCommand myCommand;

	@RequestMapping(value = "/unsafe")
	public String unsafe() {
		return externalUnsafeRequestorService.callExternalResource();
	}
	
	@RequestMapping(value = "/safe")
	public ResponseEntity<String> safe() {
		return externalSafeRequestorService.safe();
	}
	
	@RequestMapping(value = "/newsafe")
	public ResponseEntity<String> newSafe() {
		myCommand = new MyCommand(setter());
		String result =  myCommand.execute();
		
		if(!result.equals("Fallback in action!")){
			return new ResponseEntity<String>(result, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Fail to request", HttpStatus.NOT_FOUND);
	}
	
	private HystrixCommand.Setter setter() {

        Setter setter = HystrixCommandProperties.Setter();
        setter.withExecutionTimeoutInMilliseconds(1_000);
        setter.withRequestCacheEnabled(false);
        setter.withCircuitBreakerRequestVolumeThreshold(5);
        
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("External");

        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("/newsafe");

        return HystrixCommand.Setter.withGroupKey(groupKey).
                andCommandKey(commandKey ).
                andCommandPropertiesDefaults(setter);
    }
	

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
