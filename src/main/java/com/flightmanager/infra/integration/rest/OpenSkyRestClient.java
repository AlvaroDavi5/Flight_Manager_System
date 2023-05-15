package com.flightmanager.infra.integration.rest;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

public class OpenSkyRestClient {
	private String baseUrl;
	private RestTemplate restTemplate;

	public OpenSkyRestClient() {
		this.baseUrl = "opensky-network.org/api";
		this.restTemplate = new RestTemplate();
	}

	public ResponseEntity<String> getArrivalsByAirport(String ICAO, long startDate, long endDate) {
		// FIXME - URI is not absolute
		ResponseEntity<String> response = this.restTemplate.getForEntity(
				(this.baseUrl + "/flights/arrival?airport=" + ICAO + "&begin=" + startDate + "&end=" + endDate),
				String.class);

		return response;
	}

	public ResponseEntity<String> getDeparturesByAirport(String ICAO, long startDate, long endDate) {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
				(this.baseUrl + "/flights/departure?airport=" + ICAO + "&begin=" + startDate + "&end=" + endDate),
				String.class);

		return response;
	}
}
