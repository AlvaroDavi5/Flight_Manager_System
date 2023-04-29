package domain.entities;

public class Gate {
	private int number;
	private boolean isOpen;
	private int boardingDurationInMinutes;
	private String flightCode;

	public Gate(int number) {
		this.number = number;
		this.isOpen = false;
		this.boardingDurationInMinutes = 2 * 60;
		this.flightCode = null;
	}

	public int getNumber() {
		return this.number;
	}

	public String getStatus() {
		if (this.isOpen)
			return "OPENED";
		else
			return "CLOSED";
	}

	public void setStatus(String status) {
		if (status == "OPENED") {
			this.isOpen = true;
		} else if (status == "CLOSED") {
			this.isOpen = false;
			this.boardingDurationInMinutes = 2 * 60;
		}
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
}
