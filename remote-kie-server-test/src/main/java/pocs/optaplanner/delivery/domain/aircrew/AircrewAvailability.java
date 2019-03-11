package pocs.optaplanner.delivery.domain.aircrew;

import java.time.OffsetDateTime;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.Data;

@Data
public class AircrewAvailability {

	private Aircrew aircrew;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime startTime;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
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
