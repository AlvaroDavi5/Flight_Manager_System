package infra.integration.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class OpenSkyRestClient {
	private String baseUrl;
	private RestTemplate restTemplate;

	public OpenSkyRestClient() {
		this.baseUrl = "http://localhost:8080/api";
		this.restTemplate = new RestTemplate();
	}

	public ResponseEntity<String> getHealthCheck() {
		ResponseEntity<String> response = this.restTemplate.getForEntity(
				(this.baseUrl + "/healthcheck"),
				String.class);

		return response;
	}
}
