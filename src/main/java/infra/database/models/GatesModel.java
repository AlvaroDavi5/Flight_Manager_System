package infra.database.models;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "Gates")
public class GatesModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private int number;
	private boolean isFreeToDock;
	private boolean isOpenToBoarding;
	private int boardingDuration;
	private String flightCode;

	public GatesModel() {
	}

	public int getNumber() {
		return this.number;
	};

	public void setNumber(int number) {
		this.number = number;
	};

	public boolean getIsFreeToDock() {
		return this.isFreeToDock;
	};

	public void setIsFreeToDock(boolean isFreeToDock) {
		this.isFreeToDock = isFreeToDock;
	};

	public boolean getIsOpenToBoarding() {
		return this.isOpenToBoarding;
	};

	public void setIsOpenToBoarding(boolean isOpenToBoarding) {
		this.isOpenToBoarding = isOpenToBoarding;
	};

	public int getBoardingDuration() {
		return this.boardingDuration;
	};

	public void setBoardingDuration(int boardingDuration) {
		this.boardingDuration = boardingDuration;
	};

	public String getFlightCode() {
		return this.flightCode;
	};

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	};
}
