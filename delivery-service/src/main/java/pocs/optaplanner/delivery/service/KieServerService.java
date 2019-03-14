package pocs.optaplanner.delivery.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.kie.server.api.exception.KieServicesHttpException;
import org.kie.server.api.model.instance.SolverInstance;
import org.kie.server.client.SolverServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.domain.DeliverySchedule;

@Component
public class KieServerService {

	private static String CONTAINER_ID = "myContainer";
	private static String SOLVER_ID = "scheduleSolver-{id}";
	private static String SOLVER_CONFIG_XML = "pocs/optaplanner/delivery/deliveryScheduleSolver.xml";

	private SolverServicesClient solverServicesClient;

	private Map<String, SolverInstance> instanceMap = new HashMap<>();

	@Autowired
	public KieServerService(SolverServicesClient solverServicesClient) {
		this.solverServicesClient = solverServicesClient;
	}

	public void createSolver(String solverId) {

		SolverInstance instance = null;

		try {
			solverServicesClient.getSolver(CONTAINER_ID, solverId);
		} catch (KieServicesHttpException e) {
			instance = solverServicesClient.createSolver(CONTAINER_ID, solverId, SOLVER_CONFIG_XML);
		}

		if (!instanceMap.containsKey(solverId)) {
			instanceMap.put(solverId, instance);
		}

	}

	public void deleteSolver(String solverId) {

		if (instanceMap.containsKey(solverId)) {
			solverServicesClient.disposeSolver(CONTAINER_ID, solverId);
			instanceMap.remove(solverId);
		}

	}

	public void solve(DeliverySchedule deliverySchedule) {

		String solverId = createSolverId(deliverySchedule.getId());

		// create solver
		createSolver(solverId);

		// start solving
		solverServicesClient.solvePlanningProblem(CONTAINER_ID, solverId, deliverySchedule);

	}

	public Optional<DeliverySchedule> getBestSolution(Integer id) {

		String solverId = createSolverId(id);

		Optional<DeliverySchedule> optional = Optional.empty();

		SolverInstance instance = solverServicesClient.getSolverWithBestSolution(CONTAINER_ID, solverId);
		if (null != instance && null != instance.getBestSolution()) {
			DeliverySchedule solved = (DeliverySchedule) instance.getBestSolution();
			optional = Optional.of(solved);
			deleteSolver(solverId);
		}

		return optional;

	}

	private String createSolverId(Integer scheduleId) {
		return SOLVER_ID.replace("{id}", scheduleId.toString());
	}

}
