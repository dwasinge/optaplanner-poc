package pocs.optaplanner.delivery.camel;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.domain.DeliveryAssignment;
import pocs.optaplanner.delivery.domain.DeliveryRole;
import pocs.optaplanner.delivery.domain.DeliverySchedule;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.skills.Skill;
import pocs.optaplanner.delivery.service.DeliveryAssignmentService;
import pocs.optaplanner.delivery.service.DeliveryRoleService;

@Component
public class ScheduleEnrichmentRoute extends RouteBuilder {

	public static final String SCHEDULE_ENRICHMENT_ROUTE_ID = "schedule-enrichment-route";
	public static final String SCHEDULE_ENRICHMENT_ROUTE = "direct:" + SCHEDULE_ENRICHMENT_ROUTE_ID;

	public static final String SKILLS_ENRICHMENT_ROUTE_ID = "skills-enrichment-route";
	public static final String SKILLS_ENRICHEMNT_ROUTE = "direct:" + SKILLS_ENRICHMENT_ROUTE_ID;

	public static final String AIRCREW_ENRICHMENT_ROUTE_ID = "aircrew-enrichment-route";
	public static final String AIRCREW_ENRICHMENT_ROUTE = "direct:" + AIRCREW_ENRICHMENT_ROUTE_ID;

	public static final String DELIVERY_ROLE_ROUTE_ID = "delivery-role-route";
	public static final String DELIVERY_ROLE_ROUTE = "direct:" + DELIVERY_ROLE_ROUTE_ID;

	public static final String DELIVERY_ASSIGNMENT_ROUTE_ID = "delivery-assignment-route";
	public static final String DELIVERY_ASSIGNMENT_ROUTE = "direct:" + DELIVERY_ASSIGNMENT_ROUTE_ID;

	@Override
	public void configure() throws Exception {

		//getContext().setTracing(true);

		from(SCHEDULE_ENRICHMENT_ROUTE)
			.routeId(SCHEDULE_ENRICHMENT_ROUTE_ID)
			.setHeader("schedule", simple("${body}"))
			.setBody(simple("${null}"))
			.to(SKILLS_ENRICHEMNT_ROUTE)
			.to(AIRCREW_ENRICHMENT_ROUTE)
			.to(DELIVERY_ROLE_ROUTE)
			.to(DELIVERY_ASSIGNMENT_ROUTE)
			.process(new Processor() {
				
				@Override
				public void process(Exchange exchange) throws Exception {

					DeliverySchedule schedule = exchange.getIn().getHeader("schedule", DeliverySchedule.class);

					Skill[] skillArray = exchange.getIn().getHeader("skillsList", Skill[].class);
					schedule.getSkillList().addAll(Arrays.asList(skillArray));

					Aircrew[] aircrewArray = exchange.getIn().getHeader("aircrewList", Aircrew[].class);
					schedule.getAirCrewList().addAll(Arrays.asList(aircrewArray));

					DeliveryRole[] deliveryRoleArray = exchange.getIn().getHeader("deliveryRoleList", DeliveryRole[].class);
					schedule.getDeliveryRoleList().addAll(Arrays.asList(deliveryRoleArray));

					DeliveryAssignment[] deliveryAssignmentArray = exchange.getIn().getHeader("deliveryAssignmentList", DeliveryAssignment[].class);
					schedule.getDeliveryAssignmentList().addAll(Arrays.asList(deliveryAssignmentArray));

					exchange.getIn().setBody(schedule);
					
					
				}
			});

		// get all skills from the configured service
		from(SKILLS_ENRICHEMNT_ROUTE)
			.routeId(SKILLS_ENRICHMENT_ROUTE_ID)
			.enrich("{{skills.service.endpoint}}")
			.unmarshal()
				.json(JsonLibrary.Jackson, Skill[].class)
			.setHeader("skillsList", simple("${body}"))
			.setBody(simple("${null}"));

		// get all aircrew from the configured service
		from(AIRCREW_ENRICHMENT_ROUTE)
			.routeId(AIRCREW_ENRICHMENT_ROUTE_ID)
			.enrich("{{aircrew.service.endpoint}}")
			.unmarshal()
				.json(JsonLibrary.Jackson, Aircrew[].class)
			.setHeader("aircrewList", simple("${body}"))
			.setBody(simple("${null}"));

		// get all roles
		from(DELIVERY_ROLE_ROUTE)
			.routeId(DELIVERY_ROLE_ROUTE_ID)
			.bean(DeliveryRoleService.class, "getAll")
			.setHeader("deliveryRoleList", simple("${body}"))
			.setBody(simple("${null}"));

		// get all assignments
		from(DELIVERY_ASSIGNMENT_ROUTE)
			.routeId(DELIVERY_ASSIGNMENT_ROUTE_ID)
			.bean(DeliveryAssignmentService.class, "getAll")
			.setHeader("deliveryAssignmentList", simple("${body}"))
			.setBody(simple("${null}"));

		// TODO:  get all aircrew assignments

	}

}
