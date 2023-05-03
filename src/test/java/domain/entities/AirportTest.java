package domain.entities;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AirportTest {
	private Airport airport = new Airport("SBGR", "GRU", 200);

	@Test
	public void testInstantiation() {
		assertEquals("SBGR", this.airport.getICAO());
		assertEquals("GRU", this.airport.getIATA());
		assertEquals(200, this.airport.getGatesAmount());
		assertEquals(200, this.airport.getGatesList().size());
	}

	@Test
	public void testPersistedGateList() {
		this.airport.getGate(145).closeBoarding();
		this.airport.getGate(10).openBoarding();
		this.airport.updateGateDocking(this.airport.getGate(5), false);

		assertEquals(145, this.airport.getGate(145).getNumber());
		assertEquals(false, this.airport.getGate(145).isOpen());
		assertEquals(true, this.airport.getGate(10).isOpen());
		assertEquals(false, this.airport.getGate(5).isFree());
	}
}