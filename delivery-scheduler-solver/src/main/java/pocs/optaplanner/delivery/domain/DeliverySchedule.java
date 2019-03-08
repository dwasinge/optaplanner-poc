package pocs.optaplanner.delivery.domain;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.autodiscover.AutoDiscoverMemberType;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoft.HardMediumSoftScoreJacksonJsonDeserializer;
import org.optaplanner.persistence.jackson.api.score.buildin.hardmediumsoft.HardMediumSoftScoreJacksonJsonSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.domain.skills.Skill;

@Data
@PlanningSolution()
public class DeliverySchedule {

	@ProblemFactCollectionProperty
	private List<Skill> skillList = new ArrayList<>();

	@ProblemFactCollectionProperty
	private List<DeliveryRole> deliveryRoleList = new ArrayList<>();

	@ValueRangeProvider(id = "availableAircrewRange")
	@ProblemFactCollectionProperty
	private List<Aircrew> airCrewList = new ArrayList<>();

	@ProblemFactCollectionProperty
	private List<AircrewAvailability> aircrewAvailabilityList = new ArrayList<>();

	@PlanningEntityCollectionProperty
	private List<DeliveryAssignment> deliveryAssignmentList = new ArrayList<>();

	@PlanningScore
	@JsonSerialize(using = HardMediumSoftScoreJacksonJsonSerializer.class)
	@JsonDeserialize(using = HardMediumSoftScoreJacksonJsonDeserializer.class)
	private HardMediumSoftScore score = null;

}
