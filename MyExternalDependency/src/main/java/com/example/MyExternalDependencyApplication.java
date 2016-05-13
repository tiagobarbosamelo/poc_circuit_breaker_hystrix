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

	static int timeout = 400;
	static int errorRate = 10;

	@RequestMapping(value = "/configure")
	public String configure(
			@RequestParam(required = false, value = "timeout", defaultValue = "0") int timeout_value,
			@RequestParam(required = false, value = "error", defaultValue = "0") int error_value) {

		if (timeout_value > 0) {
			timeout = timeout_value;
		}

		if ( (error_value >= 0) && (error_value<=100) ) {
			errorRate = error_value;
		}

		return ("Current configuration is:\n\n" +
				"timeout:\t" + timeout + "ms\n" +
				"error:  \t" + errorRate + "%\n\n");
	}

	@RequestMapping(value = "/external")
	public String external() throws Exception {
		Thread.sleep(timeout/2);

		if ( (Math.random()*100) < errorRate) {
			throw new Exception("General service error");
		}

		Thread.sleep(timeout/2);

		return "Message from external app";
	}

	public static void main(String[] args) {
		SpringApplication.run(MyExternalDependencyApplication.class, args);
	}
}
