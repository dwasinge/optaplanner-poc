package pocs.optaplanner.delivery.utils;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import pocs.optaplanner.delivery.SolverTest;
import pocs.optaplanner.delivery.domain.DeliverySchedule;

public class SolverUtils {

	public static DeliverySchedule assignAircrewToDelivery(DeliverySchedule unsolvedSchedule, String solverConfigFile) {

		KieContainer kieContainer = KieServices.Factory.get()
				.getKieClasspathContainer(SolverTest.class.getClassLoader());
		SolverFactory<DeliverySchedule> solverFactory = SolverFactory.createFromKieContainerXmlResource(kieContainer,
				"pocs/optaplanner/delivery/deliveryScheduleSolver.xml");
		Solver<DeliverySchedule> solver = solverFactory.buildSolver();
		return solver.solve(unsolvedSchedule);

	}

}
