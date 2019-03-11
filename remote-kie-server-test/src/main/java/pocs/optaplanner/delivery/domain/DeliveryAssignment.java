package pocs.optaplanner.delivery.domain;

import java.time.OffsetDateTime;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.Data;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;

@Data
public class DeliveryAssignment {

	private String name;
	private String description;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime startTime;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime endTime;
	private Aircrew aircrew;

	private DeliveryRole deliveryRole;

	public DeliveryAssignment() {
	}

	public DeliveryAssignment(String name, String description, OffsetDateTime startTime, OffsetDateTime endTime,
			DeliveryRole deliveryRole) {
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.deliveryRole = deliveryRole;
	}

}
