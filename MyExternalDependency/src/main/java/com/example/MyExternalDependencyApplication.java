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

	static int timeout = 1_000;

	@RequestMapping(value = "/configure")
	public String configure(@RequestParam(required = true, value = "timeout") int timeout_value) {
		if (timeout_value > 0) {
			timeout = timeout_value;
		} else {
			timeout = 1_000;
		}
		
		return ("Timeout is now: " + timeout_value);
	}

	@RequestMapping(value = "/external")
	public String external() {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Message from external app";
	}

	public static void main(String[] args) {
		SpringApplication.run(MyExternalDependencyApplication.class, args);
	}
}
