package pocs.optaplanner.delivery.missions;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import lombok.Data;
import pocs.optaplanner.delivery.aircrew.Aircrew;
import pocs.optaplanner.delivery.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.skills.Skill;

@Data
@PlanningSolution
public class MissionSchedule {

	@ProblemFactCollectionProperty
	private List<Skill> skillList = new ArrayList<>();

	@ProblemFactCollectionProperty
	private List<MissionRole> missionRoleList = new ArrayList<>();

	@ValueRangeProvider(id = "availableAircrewRange")
	@ProblemFactCollectionProperty
	private List<Aircrew> airCrewList = new ArrayList<>();

	@ProblemFactCollectionProperty
	private List<AircrewAvailability> aircrewAvailabilityList = new ArrayList<>();

	@PlanningEntityCollectionProperty
	private List<MissionAssignment> missionAssignmentList = new ArrayList<>();

	@PlanningScore
	private HardMediumSoftScore score = null;

}
