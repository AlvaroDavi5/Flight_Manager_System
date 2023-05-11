package com.flightmanager.interfaces;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class Router {
	@GetMapping("/healthcheck")
	public String healthCheck() {
		return "{ \"status\": \"OK\" }";
	}
}
