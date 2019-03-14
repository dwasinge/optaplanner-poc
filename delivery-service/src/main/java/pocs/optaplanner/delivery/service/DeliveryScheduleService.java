package pocs.optaplanner.delivery.service;

import java.util.Optional;

import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.camel.ScheduleEnrichmentRoute;
import pocs.optaplanner.delivery.domain.DeliverySchedule;
import pocs.optaplanner.delivery.exception.RepositoryException;
import pocs.optaplanner.delivery.exception.ResourceNotFoundException;
import pocs.optaplanner.delivery.repository.AbstractMapRepository;

@Component
public class DeliveryScheduleService extends AbstractGenericCrudService<DeliverySchedule> {

	private ProducerTemplate template;
	private KieServerService kieServerService;

	@Autowired
	public DeliveryScheduleService(AbstractMapRepository<DeliverySchedule> repository, ProducerTemplate template,
			KieServerService kieServerService) {
		super(repository);
		this.template = template;
		this.kieServerService = kieServerService;
	}

	public void solve(Integer id) {

		// Get schedule with id
		Optional<DeliverySchedule> optional = repository.get(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		// try to enrich schedule before solving
		DeliverySchedule enrichedSchedule = (DeliverySchedule) template
				.sendBody(ScheduleEnrichmentRoute.SCHEDULE_ENRICHMENT_ROUTE, ExchangePattern.InOut, optional.get());

		// start the solver route
		kieServerService.solve(enrichedSchedule);

	}

	public DeliverySchedule getBestSolution(Integer id) {

		Optional<DeliverySchedule> optional = kieServerService.getBestSolution(id);
		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("failed to find a best solution for schedule with id '" + id + "'");
		}

		// update schedule with best solution
		optional = repository.update(optional.get());
		if (!optional.isPresent()) {
			throw new RepositoryException("failed to update resource. " + optional.get());
		}

		return optional.get();

	}

}
