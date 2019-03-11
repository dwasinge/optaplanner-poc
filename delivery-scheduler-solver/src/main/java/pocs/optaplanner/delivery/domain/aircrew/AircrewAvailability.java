package pocs.optaplanner.delivery.domain.aircrew;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.Data;

@Data
public class AircrewAvailability implements Serializable {

	private static final long serialVersionUID = 1L;

	private Aircrew aircrew;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime availabilityStartTime;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime availabilityEndTime;

	private AircrewAvailabilityState state;

	public AircrewAvailability() {
	}

	public AircrewAvailability(Aircrew aircrew, OffsetDateTime availabilityStartTime, OffsetDateTime availabilityEndTime,
			AircrewAvailabilityState state) {
		this.aircrew = aircrew;
		this.availabilityStartTime = availabilityStartTime;
		this.availabilityEndTime = availabilityEndTime;
		this.state = state;
	}

}
