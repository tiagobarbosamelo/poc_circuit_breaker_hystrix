package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MyExternalDependencyApplication {

	static int latency = 400;
	static int errorRate = 10;

	@RequestMapping(value = "/configure")
	public String configure(
			@RequestParam(required = false, value = "latency", defaultValue = "0") int latency_value,
			@RequestParam(required = false, value = "error", defaultValue = "0") int error_value) {

		if (latency_value > 0) {
			latency = latency_value;
		}

		if ( (error_value >= 0) && (error_value<=100) ) {
			errorRate = error_value;
		}

		return ("Current configuration is:\n\n" +
				"latency:\t" + latency + "ms\n" +
				"errors: \t" + errorRate + "%\n\n");
	}

	@RequestMapping(value = "/external")
	public String external() throws Exception {
		Thread.sleep(latency/2);

		if ( (Math.random()*100) < errorRate) {
			throw new Exception("General service error");
		}

		Thread.sleep(latency/2);

		return "Message from external app";
	}

	public static void main(String[] args) {
		SpringApplication.run(MyExternalDependencyApplication.class, args);
	}
}
