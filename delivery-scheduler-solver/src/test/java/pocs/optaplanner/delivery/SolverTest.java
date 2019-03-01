package pocs.optaplanner.delivery;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pocs.optaplanner.delivery.aircrew.Aircrew;
import pocs.optaplanner.delivery.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.aircrew.AircrewAvailabilityState;
import pocs.optaplanner.delivery.deliveries.DeliveryAssignment;
import pocs.optaplanner.delivery.deliveries.DeliveryRole;
import pocs.optaplanner.delivery.deliveries.DeliverySchedule;
import pocs.optaplanner.delivery.utils.MockUtils;
import pocs.optaplanner.delivery.utils.SolverUtils;

public class SolverTest {

	@Before
	public void setup() {
		System.out.println("Starting test...");
	}

	@Test
	public void testSolveOneMissionAllCrewAvailable() {

		DeliverySchedule unsolvedSchedule = new DeliverySchedule();

		// create all Aircrew in the squadron
		unsolvedSchedule.getAirCrewList().addAll(MockUtils.createSquadronList());

		// create mission assignment list with no aircrew assigned
		OffsetDateTime startTime = OffsetDateTime.of(2019, 3, 1, 10, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime endTime = OffsetDateTime.of(2019, 3, 1, 12, 0, 0, 0, ZoneOffset.UTC);
		unsolvedSchedule.getDeliveryAssignmentList()
				.addAll(MockUtils.createDelivery("lugnuts", "deliver lugnuts to robot planet", startTime, endTime,
						MockUtils.PILOT, MockUtils.NAVIGATOR, MockUtils.ROBOT));

		// create mission role list
		unsolvedSchedule.getDeliveryRoleList().addAll(MockUtils.createDeliveryRoleList());

		// create all skills
		unsolvedSchedule.getSkillList().addAll(MockUtils.createSkillList());

		DeliverySchedule solvedSchedule = SolverUtils.assignAircrewToDelivery(unsolvedSchedule,
				"deliveryScheduleSolver.xml");
		Assert.assertNotNull(solvedSchedule);

		List<DeliveryAssignment> DeliveryAssignmentList = solvedSchedule.getDeliveryAssignmentList();
		Assert.assertNotNull(DeliveryAssignmentList);
		Assert.assertEquals(3, DeliveryAssignmentList.size());

		for (DeliveryAssignment assignment : DeliveryAssignmentList) {

			DeliveryRole dRole = assignment.getDeliveryRole();
			Aircrew ac = assignment.getAircrew();

			System.out.println(ac.getName() + " has been assigned role '" + dRole.getName() + "' for mission "
					+ assignment.getName());

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
		unsolvedSchedule.getDeliveryAssignmentList()
				.addAll(MockUtils.createDelivery("lugnuts", "deliver lugnuts to robot planet", startTime, endTime,
						MockUtils.PILOT, MockUtils.NAVIGATOR, MockUtils.ROBOT));

		// create mission role list
		unsolvedSchedule.getDeliveryRoleList().addAll(MockUtils.createDeliveryRoleList());

		// create all skills
		unsolvedSchedule.getSkillList().addAll(MockUtils.createSkillList());

		DeliverySchedule solvedSchedule = SolverUtils.assignAircrewToDelivery(unsolvedSchedule,
				"deliveryScheduleSolver.xml");
		Assert.assertNotNull(solvedSchedule);

		List<DeliveryAssignment> DeliveryAssignmentList = solvedSchedule.getDeliveryAssignmentList();
		Assert.assertNotNull(DeliveryAssignmentList);
		Assert.assertEquals(3, DeliveryAssignmentList.size());

		for (DeliveryAssignment assignment : DeliveryAssignmentList) {

			DeliveryRole dRole = assignment.getDeliveryRole();
			Aircrew ac = assignment.getAircrew();

			System.out.println(ac.getName() + " has been assigned role '" + dRole.getName() + "' for mission "
					+ assignment.getName());

		}

	}

}
