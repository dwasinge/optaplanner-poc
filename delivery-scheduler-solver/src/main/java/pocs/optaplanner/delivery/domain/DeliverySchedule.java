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

import org.optaplanner.persistence.xstream.api.score.buildin.hardmediumsoft.HardMediumSoftScoreXStreamConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.Data;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.domain.skills.Skill;

@Data
@XStreamAlias("DeliverySchedule")
@PlanningSolution(autoDiscoverMemberType = AutoDiscoverMemberType.FIELD)
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
	@XStreamConverter(HardMediumSoftScoreXStreamConverter.class)
	private HardMediumSoftScore score = null;

}
