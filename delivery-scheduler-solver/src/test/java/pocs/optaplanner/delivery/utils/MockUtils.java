package pocs.optaplanner.delivery.utils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pocs.optaplanner.delivery.domain.DeliveryAssignment;
import pocs.optaplanner.delivery.domain.DeliveryRole;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailabilityState;
import pocs.optaplanner.delivery.domain.skills.Skill;

public class MockUtils {

	public static final String PILOT = "pilot";
	public static final String NAVIGATOR = "navigator";
	public static final String DELIVERY_BOY = "human-delivery-boy";
	public static final String ROBOT = "robot";

	public static List<DeliveryAssignment> createDelivery(Integer scheduleId, OffsetDateTime startTime,
			OffsetDateTime endTime, String... requiredRoles) {

		List<DeliveryAssignment> missionAssignmentList = new ArrayList<>();

		for (String role : requiredRoles) {

			if (PILOT.equals(role)) {
				missionAssignmentList.add(createMissionAssignment(scheduleId, startTime, endTime,
						createDeliveryRole(PILOT, new HashSet<>(Arrays.asList(createSkill(PILOT).getId())))));
			} else if (DELIVERY_BOY.equals(role)) {
				missionAssignmentList
						.add(createMissionAssignment(scheduleId, startTime, endTime, createDeliveryRole(DELIVERY_BOY,
								new HashSet<>(Arrays.asList(createSkill(DELIVERY_BOY).getId())))));
			} else if (NAVIGATOR.equals(role)) {
				missionAssignmentList.add(createMissionAssignment(scheduleId, startTime, endTime,
						createDeliveryRole(NAVIGATOR, new HashSet<>(Arrays.asList(createSkill(NAVIGATOR).getId())))));
			} else if (ROBOT.equals(role)) {
				missionAssignmentList.add(createMissionAssignment(scheduleId, startTime, endTime,
						createDeliveryRole(ROBOT, new HashSet<>(Arrays.asList(createSkill(ROBOT).getId())))));
			} else {
				throw new IllegalArgumentException("unknown role '" + role + "'");
			}

		}

		return missionAssignmentList;

	}

	public static Aircrew getAircrewByName(List<Aircrew> aircrewList, String name) {

		for (Aircrew ac : aircrewList) {
			if (ac.getName().equals(name)) {
				return ac;
			}
		}

		return null;

	}

	public static List<Aircrew> createSquadronList() {

		// create air crew list
		Aircrew ac1 = createAircrew("Taranga Leela", new HashSet<>(Arrays.asList(createSkill(PILOT).getId())));
		Aircrew ac2 = createAircrew("Philip J Fry",
				new HashSet<>(Arrays.asList(createSkill(DELIVERY_BOY).getId(), createSkill(NAVIGATOR).getId())));
		Aircrew ac3 = createAircrew("Bender B Rodriguez",
				new HashSet<>(Arrays.asList(createSkill(ROBOT).getId(), createSkill(NAVIGATOR).getId())));
		Aircrew ac4 = createAircrew("Amy Wong", new HashSet<>(Arrays.asList(createSkill(NAVIGATOR).getId())));

		return Arrays.asList(ac1, ac2, ac3, ac4);

	}

	public static List<Skill> createSkillList() {

		Skill pilotSkill = createSkill(PILOT);
		Skill navigatorSkill = createSkill(NAVIGATOR);
		Skill deliveryBoySkill = createSkill(DELIVERY_BOY);
		Skill robotSkill = createSkill(ROBOT);

		return Arrays.asList(pilotSkill, navigatorSkill, deliveryBoySkill, robotSkill);

	}

	public static Skill createSkill(String type) {

		if (PILOT.equals(type)) {
			return createSkill(0, "p1000", "pilot", "all pilot training complete");
		} else if (NAVIGATOR.equals(type)) {
			return createSkill(1, "n2000", "navigator", "can read a map");
		} else if (DELIVERY_BOY.equals(type)) {
			return createSkill(2, "hdb100", "human delivery boy", "human with no skills");
		} else if (ROBOT.equals(type)) {
			return createSkill(3, "rdb100", "robot", "robot taking human job");
		} else {
			throw new IllegalArgumentException("unknown type supplied.");
		}

	}

	public static Skill createSkill(Integer id, String code, String name, String description) {
		return new Skill(id, code, name, description);
	}

	public static List<DeliveryRole> createDeliveryRoleList() {

		DeliveryRole pilotRole = createDeliveryRole(PILOT, new HashSet<>(Arrays.asList(createSkill(PILOT).getId())));
		DeliveryRole robotRole = createDeliveryRole(ROBOT, new HashSet<>(Arrays.asList(createSkill(ROBOT).getId())));
		DeliveryRole navigatorRole = createDeliveryRole(NAVIGATOR,
				new HashSet<>(Arrays.asList(createSkill(NAVIGATOR).getId())));
		DeliveryRole deliveryBoyRole = createDeliveryRole(DELIVERY_BOY,
				new HashSet<>(Arrays.asList(createSkill(DELIVERY_BOY).getId())));

		return Arrays.asList(pilotRole, robotRole, navigatorRole, deliveryBoyRole);

	}

	public static DeliveryRole createDeliveryRole(String name, Set<Integer> requiredSkillSet) {
		return new DeliveryRole(name, requiredSkillSet);
	}

	public static Aircrew createAircrew(String name, Set<Integer> skillProficiencySet) {
		return new Aircrew(name, skillProficiencySet);
	}

	public static DeliveryAssignment createMissionAssignment(Integer scheduleId, OffsetDateTime startTime,
			OffsetDateTime endTime, DeliveryRole deliveryRole) {
		return new DeliveryAssignment(scheduleId, startTime, endTime, deliveryRole);
	}

	public static AircrewAvailability createAircrewAvailability(Aircrew aircrew, OffsetDateTime startTime,
			OffsetDateTime endTime, AircrewAvailabilityState state) {
		return new AircrewAvailability(aircrew, startTime, endTime, state);
	}

}
