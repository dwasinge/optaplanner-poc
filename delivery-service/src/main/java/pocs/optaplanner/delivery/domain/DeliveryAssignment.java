package pocs.optaplanner.delivery.domain;

import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryAssignment extends AbstractPersistable {

	private Integer scheduleId;

	private OffsetDateTime startTime;

	private OffsetDateTime endTime;

	@ApiModelProperty(hidden = true)
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
