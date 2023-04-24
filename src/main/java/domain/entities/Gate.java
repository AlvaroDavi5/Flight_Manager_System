package domain.entities;

public class Gate {
	private int number;
	private boolean isOpen;
	private String flightCode;

	public Gate(int number) {
		this.number = number;
		this.isOpen = false;
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
		if (status == "OPENED")
			this.isOpen = true;
		else if (status == "CLOSED")
			this.isOpen = false;
	}

	public String getFlightCode() {
		return this.flightCode;
	}

	public void setFlightCode(String code) {
		this.flightCode = code;
	}
}
