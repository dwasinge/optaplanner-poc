package pocs.optaplanner.delivery.test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.server.api.exception.KieServicesHttpException;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.instance.SolverInstance;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.SolverServicesClient;

import pocs.optaplanner.delivery.domain.DeliveryAssignment;
import pocs.optaplanner.delivery.domain.DeliveryRole;
import pocs.optaplanner.delivery.domain.DeliverySchedule;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailabilityState;
import pocs.optaplanner.delivery.domain.skills.Skill;
import pocs.optaplanner.delivery.test.utils.MockUtils;

public class RemoteKieServerTest {

	// OpenshiftConfiguration
	private static String KIE_HTTP_URL = "http://delivery-scheduler-app-kieserver-puckboard-dw-dev.apps.s11.core.rht-labs.com/services/rest/server";
	private static String USER = "executionUser";
	private static String PASSWORD = "eLEkMx6!";

	// Local Configuration
//	private static String KIE_HTTP_URL = "http://localhost:8080/kie-server/services/rest/server";
//	private static String USER = "executionUser";
//	private static String PASSWORD = "RedHat";

	// container and solver configuration
	private static String CONTAINER_ID = "defaultContainer";
	private static String SOLVER_ID = "scheduleSolver";
	private static String SOLVER_CONFIG_XML = "pocs/optaplanner/delivery/deliveryScheduleSolver.xml";

	private SolverServicesClient solverClient;
	private SolverInstance solverInstance;

	@Before
	public void setup() {

		// setup solver client
		if (null == solverClient) {
			solverClient = initSolverClient();
		}

		// create the solver instance if it doesn't exist
		if (!solverExists()) {
			solverInstance = solverClient.createSolver(CONTAINER_ID, SOLVER_ID, SOLVER_CONFIG_XML);
		}

	}

	@After
	public void tearDown() {

		if (null != solverInstance) {
			// dispose of solver
			solverClient.disposeSolver(CONTAINER_ID, SOLVER_ID);
			solverInstance = null;
		}

	}

	@Test
	public void testSolverStartSolvingAndGetBestSolution() throws InterruptedException {

		// given
		DeliverySchedule unsolvedSchedule = createUnsolvedSolution();

		// when
		solverClient.solvePlanningProblem(CONTAINER_ID, SOLVER_ID, unsolvedSchedule);
		TimeUnit.SECONDS.sleep(5);

		SolverInstance instance = solverClient.getSolverWithBestSolution(CONTAINER_ID, SOLVER_ID);

		// then
		Assert.assertNotNull(instance);
		Assert.assertEquals(SOLVER_ID, instance.getSolverId());

		DeliverySchedule solved = (DeliverySchedule) instance.getBestSolution();
		Assert.assertNotNull(solved);
		Assert.assertNotNull(solved.getDeliveryAssignmentList());
		Assert.assertEquals(3, solved.getDeliveryAssignmentList().size());

		boolean pilotFound = false;
		boolean navigatorFound = false;
		boolean robotFound = false;

		for (DeliveryAssignment assignment : solved.getDeliveryAssignmentList()) {

			if ("pilot".equalsIgnoreCase(assignment.getDeliveryRole().getName())
					&& "Taranga Leela".equalsIgnoreCase(assignment.getAircrew().getName())) {
				pilotFound = true;
				continue;
			}

			if ("navigator".equalsIgnoreCase(assignment.getDeliveryRole().getName())
					&& "Philip J Fry".equalsIgnoreCase(assignment.getAircrew().getName())) {
				navigatorFound = true;
				continue;
			}

			if ("robot".equalsIgnoreCase(assignment.getDeliveryRole().getName())
					&& "Bender B Rodriguez".equalsIgnoreCase(assignment.getAircrew().getName())) {
				robotFound = true;
				continue;
			}

		}

		Assert.assertTrue("Pilot was not found in delivery schedule.", pilotFound);
		Assert.assertTrue("Navigator was not found in delivery schedule.", navigatorFound);
		Assert.assertTrue("Robot was not found in delivery schedule.", robotFound);

	}

	/**
	 * intializes a {@link SolverServicesClient} with the configured attributes.
	 * 
	 * @return
	 */
	private SolverServicesClient initSolverClient() {

		// create kie services config
		KieServicesConfiguration conf = KieServicesFactory.newRestConfiguration(KIE_HTTP_URL, USER, PASSWORD, 30000);

		// NOTE: current could not get the JSON marshalling to work, using xstream
		// instead
		// conf.setMarshallingFormat(MarshallingFormat.JSON);
		conf.setMarshallingFormat(MarshallingFormat.XSTREAM);

		// Set POJO classes for the Marshaller
		conf.setExtraClasses(new HashSet<>(Arrays.asList(DeliverySchedule.class, Skill.class, DeliveryRole.class,
				DeliveryAssignment.class, Aircrew.class, AircrewAvailability.class, AircrewAvailabilityState.class)));

		// Settig KIE Content Type for the server side Content-Type header
		Map<String, String> headers = new HashMap<>();
		headers.put("X-KIE-ContentType", "xstream");
		conf.setHeaders(headers);

		// create kie services client using configuration
		KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(conf);

		// create solver services client
		return kieServicesClient.getServicesClient(SolverServicesClient.class);

	}

	/**
	 * Returns true if a {@link SolverInstance} exists with the configured container
	 * id and solver id.
	 * 
	 * @return
	 */
	private boolean solverExists() {

		try {
			solverClient.getSolver(CONTAINER_ID, SOLVER_ID);
		} catch (KieServicesHttpException e) {
			return false;
		}

		return true;

	}

	/**
	 * Create a {@link DeliverySchedule} without a score or assigned {@link Aircrew}
	 * 
	 * @return
	 */
	private DeliverySchedule createUnsolvedSolution() {

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

		return unsolvedSchedule;

	}

}
