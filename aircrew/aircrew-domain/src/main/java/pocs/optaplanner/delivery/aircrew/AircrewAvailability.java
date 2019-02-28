package pocs.optaplanner.delivery.aircrew;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class AircrewAvailability {

	private Aircrew aircrew;

	private OffsetDateTime startTime;
	private OffsetDateTime endTime;

	private AircrewAvailabilityState state;

	public AircrewAvailability() {
	}

	public AircrewAvailability(Aircrew aircrew, OffsetDateTime startTime, OffsetDateTime endTime,
			AircrewAvailabilityState state) {
		this.aircrew = aircrew;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
	}

}
