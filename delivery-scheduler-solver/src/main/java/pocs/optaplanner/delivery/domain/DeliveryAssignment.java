package pocs.optaplanner.delivery.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import lombok.Data;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;

@Data
@PlanningEntity
public class DeliveryAssignment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer scheduleId;

	private OffsetDateTime startTime;

	private OffsetDateTime endTime;

	@PlanningVariable(valueRangeProviderRefs = { "availableAircrewRange" }, nullable = true)
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
