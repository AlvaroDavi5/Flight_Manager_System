package com.flightmanager.infra.integration.rest;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

public class FlightManagerRestClient {
	private String baseUrl;
	private RestTemplate restTemplate;

	public FlightManagerRestClient() {
		this.baseUrl = "http://localhost:3000/api";
		this.restTemplate = new RestTemplate();
	}

	public ResponseEntity<String> listFlights(long startDate, long endDate) {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
				(this.baseUrl + "/flights/list" + "?startDate=" + startDate + "&endDate=" + endDate),
				String.class);

		return response;
	}

}
