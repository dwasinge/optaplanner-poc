package pocs.optaplanner.delivery.deliveries;

import java.time.OffsetDateTime;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import lombok.Data;
import pocs.optaplanner.delivery.aircrew.Aircrew;

@Data
@PlanningEntity
public class DeliveryAssignment {

	private String name;

	private String description;

	private OffsetDateTime startTime;

	private OffsetDateTime endTime;

	@PlanningVariable(valueRangeProviderRefs = { "availableAircrewRange" }, nullable = true)
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
