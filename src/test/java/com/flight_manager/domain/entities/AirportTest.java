package com.flight_manager.domain.entities;

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

		assertEquals(145, this.airport.getGate(145).getGateNumber());
		assertEquals(false, this.airport.getGate(145).isOpenToBoarding());
		assertEquals(true, this.airport.getGate(10).isOpenToBoarding());
		assertEquals(false, this.airport.getGate(5).isFreeToDock());
	}
}
