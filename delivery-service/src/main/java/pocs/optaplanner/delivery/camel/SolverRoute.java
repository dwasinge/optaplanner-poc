package pocs.optaplanner.delivery.camel;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.domain.DeliverySchedule;

@Component
public class SolverRoute extends RouteBuilder {

	public static final String SOLVER_ROUTE_ENDPOINT = "direct:solve-route";
	public static final String GET_BEST_SOLUTION_ENDPOINT = "direct:get-best-solution-route";

	@Override
	public void configure() throws Exception {

		from(SOLVER_ROUTE_ENDPOINT).routeId("schedule-solver-route").log(LoggingLevel.INFO,
				"The schedule solver route has been started.");

		from(GET_BEST_SOLUTION_ENDPOINT).routeId("best-solution-route")
				.log(LoggingLevel.INFO, "The get best solution route has been started.").process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						Integer id = exchange.getIn().getBody(Integer.class);
						DeliverySchedule schedule = new DeliverySchedule();
						schedule.setId(id);
						exchange.getOut().setBody(schedule);
					}
				});

	}

}
