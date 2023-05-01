package domain.entities;

public class Gate {
	private int number;
	private boolean isFreeToDock;
	private boolean isOpenToBoarding;
	private int boardingDurationInMinutes;
	private String flightCode;
	private Flight flight;

	public Gate(int number) {
		this.number = number;
		this.isFreeToDock = true;
		this.isOpenToBoarding = false;
		this.boardingDurationInMinutes = 2 * 60;
		this.flightCode = null;
	}

	public int getNumber() {
		return this.number;
	}

	public boolean isFree() {
		return this.isFreeToDock;
	}

	public void openDocking() {
		this.isFreeToDock = true;
	}

	public void closeDocking() {
		this.isFreeToDock = false;
	}

	public boolean isOpen() {
		return this.isOpenToBoarding;
	}

	public void openBoarding() {
		this.isOpenToBoarding = true;
		this.boardingDurationInMinutes = 2 * 60;
	}

	public void closeBoarding() {
		this.isOpenToBoarding = false;
	}

	public int getBoardingDuration() {
		return this.boardingDurationInMinutes;
	}

	public void setBoardingDuration(int durationInMinutes) {
		this.boardingDurationInMinutes = durationInMinutes;
	}

	public String getFlightCode() {
		return this.flightCode;
	}

	public void setFlightCode(String code) {
		this.flightCode = code;
	}

	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
}
