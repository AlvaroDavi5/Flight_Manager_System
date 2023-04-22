package app.services;

import java.util.Date;
import java.util.HashMap;
import infra.integration.queue.producers.FlightLogisticProducer;
import infra.integration.queue.producers.FlightNotificationsProducer;

public class FlightManagerService {
	private FlightLogisticProducer flightLogisticProducer;
	private FlightNotificationsProducer flightNotificationsProducer;

	public FlightManagerService(FlightLogisticProducer flightLogisticProducer,
			FlightNotificationsProducer flightNotificationsProducer) {
		this.flightLogisticProducer = flightLogisticProducer;
		this.flightNotificationsProducer = flightNotificationsProducer;
	}

	public void dispatchMessage() {
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("v1", 555);
		message.put("v2", "test");
		String msgKey = "msg#" + new Date().getTime();

		this.flightLogisticProducer.sendMessage(2,
				msgKey, message);
		this.flightNotificationsProducer.sendMessage(2,
				msgKey, message);
	}
}
