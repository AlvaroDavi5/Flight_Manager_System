package com.flightmanager.infra.integration.rest;

import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenSkyRestClient {
	private String protocol = "https";
	private String baseUrl = "opensky-network.org";
	private RestTemplate restTemplate = new RestTemplate();

	public ResponseEntity<String> getArrivalsByAirport(String ICAO, long startDate, long endDate) {
		try {
			String path = "/api/flights/arrival";
			URI uri = new URI(this.protocol, this.baseUrl, path,
					("airport=" + ICAO + "&begin=" + startDate + "&end=" + endDate), null);
			return this.restTemplate.getForEntity(uri, String.class);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		}
	}

	public ResponseEntity<String> getDeparturesByAirport(String ICAO, long startDate, long endDate) {
		try {
			String path = "/api/flights/departure";
			URI uri = new URI(this.protocol, this.baseUrl, path,
					("airport=" + ICAO + "&begin=" + startDate + "&end=" + endDate), null);
			return this.restTemplate.getForEntity(uri, String.class);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		}
	}
}
