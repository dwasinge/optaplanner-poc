package pocs.optaplanner.delivery.utils;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import pocs.optaplanner.delivery.domain.DeliverySchedule;

public class SolverUtils {

	public static DeliverySchedule assignAircrewToDelivery(DeliverySchedule unsolvedSchedule, String solverConfigFile) {

		SolverFactory<DeliverySchedule> solverFactory = SolverFactory.createFromXmlResource(solverConfigFile);
		Solver<DeliverySchedule> solver = solverFactory.buildSolver();
		return solver.solve(unsolvedSchedule);

	}

}
