package domain.entities;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AirportTest {
	private Airport airport = new Airport("SBGR", "GRU", 200);

	@Test
	public void testInstantiation() {
		assertEquals("SBGR", this.airport.getIATA());
		assertEquals("GRU", this.airport.getICAO());
		assertEquals(200, this.airport.getGatesAmount());
		assertEquals(200, this.airport.getGatesList().size());
	}

	@Test
	public void testPersistedGateList() {
		this.airport.getGate(145).setStatus("CLOSED");
		this.airport.getGate(10).setStatus("OPENED");
		this.airport.getGate(5).setStatus("INVALID");

		assertEquals(145, this.airport.getGate(145).getNumber());
		assertEquals("CLOSED", this.airport.getGate(145).getStatus());
		assertEquals("OPENED", this.airport.getGate(10).getStatus());
		assertEquals("OPENED", this.airport.getGate(5).getStatus());
	}
}
