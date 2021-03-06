package pocs.optaplanner.delivery;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pocs.optaplanner.delivery.domain.DeliveryAssignment;
import pocs.optaplanner.delivery.domain.DeliveryRole;
import pocs.optaplanner.delivery.domain.DeliverySchedule;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailabilityState;
import pocs.optaplanner.delivery.domain.skills.Skill;
import pocs.optaplanner.delivery.utils.MockUtils;
import pocs.optaplanner.delivery.utils.SolverUtils;

public class SolverTest {

	@Before
	public void setup() {
		System.out.println("Starting test...");
	}

	@Test
	public void testThis() {

		DeliverySchedule unsolved = new DeliverySchedule();

		unsolved.getAirCrewList().add(new Aircrew("derek", new HashSet<>(Arrays.asList(0))));

		DeliveryRole deliveryRole = new DeliveryRole("pilot", new HashSet<>(Arrays.asList(0)));
		unsolved.getDeliveryRoleList().add(deliveryRole);

		OffsetDateTime startTime = OffsetDateTime.of(2019, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime endTime = OffsetDateTime.of(2019, 3, 1, 12, 0, 0, 0, ZoneOffset.UTC);
		DeliveryAssignment assignment = new DeliveryAssignment(0, startTime, endTime, deliveryRole);
		unsolved.getDeliveryAssignmentList().add(assignment);

		Skill skill = new Skill(0, "001", "pilot", "can fly");
		unsolved.getSkillList().add(skill);

		DeliverySchedule solved = SolverUtils.assignAircrewToDelivery(unsolved,
				"pocs/optaplanner/delivery/deliveryScheduleSolver.xml");
		Assert.assertNotNull(solved);


	}

	@Test
	public void testSolveOneMissionAllCrewAvailable() {

		DeliverySchedule unsolvedSchedule = new DeliverySchedule();

		// create all Aircrew in the squadron
		unsolvedSchedule.getAirCrewList().addAll(MockUtils.createSquadronList());

		// create mission assignment list with no aircrew assigned
		OffsetDateTime startTime = OffsetDateTime.of(2019, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime endTime = OffsetDateTime.of(2019, 3, 1, 12, 0, 0, 0, ZoneOffset.UTC);
		unsolvedSchedule.getDeliveryAssignmentList().addAll(
				MockUtils.createDelivery(0, startTime, endTime, MockUtils.PILOT, MockUtils.NAVIGATOR, MockUtils.ROBOT));

		// create mission role list
		unsolvedSchedule.getDeliveryRoleList().addAll(MockUtils.createDeliveryRoleList());

		// create all skills
		unsolvedSchedule.getSkillList().addAll(MockUtils.createSkillList());

		DeliverySchedule solvedSchedule = SolverUtils.assignAircrewToDelivery(unsolvedSchedule,
				"pocs/optaplanner/delivery/deliveryScheduleSolver.xml");
		Assert.assertNotNull(solvedSchedule);

		List<DeliveryAssignment> DeliveryAssignmentList = solvedSchedule.getDeliveryAssignmentList();
		Assert.assertNotNull(DeliveryAssignmentList);
		Assert.assertEquals(3, DeliveryAssignmentList.size());

		for (DeliveryAssignment assignment : DeliveryAssignmentList) {

			DeliveryRole dRole = assignment.getDeliveryRole();
			Aircrew ac = assignment.getAircrew();

			System.out.println(ac.getName() + " has been assigned role '" + dRole.getName() + "' for mission "
					+ assignment.getScheduleId());

		}

	}

	@Test
	public void testSolveOneMissionFryUnavailable() {

		DeliverySchedule unsolvedSchedule = new DeliverySchedule();

		// create all Aircrew in the squadron
		unsolvedSchedule.getAirCrewList().addAll(MockUtils.createSquadronList());

		// create sniv for fry
		Aircrew fry = MockUtils.getAircrewByName(unsolvedSchedule.getAirCrewList(), "Philip J Fry");
		OffsetDateTime fryUnavailableStartTime = OffsetDateTime.of(2019, 3, 1, 9, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime fryUnavailableEndTime = OffsetDateTime.of(2019, 3, 1, 10, 30, 0, 0, ZoneOffset.UTC);
		AircrewAvailability fryAvailability = MockUtils.createAircrewAvailability(fry, fryUnavailableStartTime,
				fryUnavailableEndTime, AircrewAvailabilityState.UNAVAILABLE);
		unsolvedSchedule.getAircrewAvailabilityList().add(fryAvailability);

		// create mission assignment list with no aircrew assigned
		OffsetDateTime startTime = OffsetDateTime.of(2019, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime endTime = OffsetDateTime.of(2019, 3, 1, 12, 0, 0, 0, ZoneOffset.UTC);
		unsolvedSchedule.getDeliveryAssignmentList().addAll(
				MockUtils.createDelivery(0, startTime, endTime, MockUtils.PILOT, MockUtils.NAVIGATOR, MockUtils.ROBOT));

		// create mission role list
		unsolvedSchedule.getDeliveryRoleList().addAll(MockUtils.createDeliveryRoleList());

		// create all skills
		unsolvedSchedule.getSkillList().addAll(MockUtils.createSkillList());

		DeliverySchedule solvedSchedule = SolverUtils.assignAircrewToDelivery(unsolvedSchedule,
				"pocs/optaplanner/delivery/deliveryScheduleSolver.xml");
		Assert.assertNotNull(solvedSchedule);

		List<DeliveryAssignment> DeliveryAssignmentList = solvedSchedule.getDeliveryAssignmentList();
		Assert.assertNotNull(DeliveryAssignmentList);
		Assert.assertEquals(3, DeliveryAssignmentList.size());

		for (DeliveryAssignment assignment : DeliveryAssignmentList) {

			DeliveryRole dRole = assignment.getDeliveryRole();
			Aircrew ac = assignment.getAircrew();

			System.out.println(ac.getName() + " has been assigned role '" + dRole.getName() + "' for mission "
					+ assignment.getScheduleId());

		}

	}

}
