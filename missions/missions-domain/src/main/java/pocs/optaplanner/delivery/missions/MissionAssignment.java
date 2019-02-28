package pocs.optaplanner.delivery.missions;

import java.time.OffsetDateTime;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import lombok.Data;
import pocs.optaplanner.delivery.aircrew.Aircrew;

@Data
@PlanningEntity
public class MissionAssignment {

	private String name;

	private String description;

	private OffsetDateTime startTime;

	private OffsetDateTime endTime;

	@PlanningVariable(valueRangeProviderRefs = { "availableAircrewRange" }, nullable = true)
	private Aircrew aircrew;

	private MissionRole missionRole;

	public MissionAssignment() {
	}

	public MissionAssignment(String name, String description, OffsetDateTime startTime, OffsetDateTime endTime,
			MissionRole missionRole) {
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.missionRole = missionRole;
	}

}
