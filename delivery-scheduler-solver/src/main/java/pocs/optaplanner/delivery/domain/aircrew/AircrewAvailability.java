package pocs.optaplanner.delivery.domain.aircrew;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AircrewAvailability {

	@NotNull
	private Aircrew aircrew;

	@NotNull
	private OffsetDateTime startTime;
	@NotNull
	private OffsetDateTime endTime;

	@NotNull
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
