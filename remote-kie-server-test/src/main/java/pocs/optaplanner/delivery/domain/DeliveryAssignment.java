package pocs.optaplanner.delivery.domain;

import java.time.OffsetDateTime;

import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryAssignment extends AbstractPersistable {

	private Integer scheduleId;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime startTime;
	@XStreamConverter(org.kie.soup.commons.xstream.OffsetDateTimeXStreamConverter.class)
	private OffsetDateTime endTime;

	private Aircrew aircrew;

	private DeliveryRole deliveryRole;

	public DeliveryAssignment() {
	}

	public DeliveryAssignment(Integer scheduleId, OffsetDateTime startTime, OffsetDateTime endTime,
			DeliveryRole deliveryRole) {
		this.scheduleId = scheduleId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.deliveryRole = deliveryRole;
	}

}
