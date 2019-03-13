package pocs.optaplanner.delivery.service;

import java.util.Collection;
import java.util.Optional;

import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.camel.SolverRoute;
import pocs.optaplanner.delivery.domain.DeliverySchedule;
import pocs.optaplanner.delivery.exception.RepositoryException;
import pocs.optaplanner.delivery.exception.ResourceAlreadyExistsException;
import pocs.optaplanner.delivery.exception.ResourceNotFoundException;
import pocs.optaplanner.delivery.repository.DeliveryScheduleMapRepository;

@Component
public class DeliveryScheduleService {

	private DeliveryScheduleMapRepository repository;
	private ProducerTemplate template;

	@Autowired
	public DeliveryScheduleService(DeliveryScheduleMapRepository repository, ProducerTemplate template) {
		this.repository = repository;
		this.template = template;
	}

	public DeliverySchedule create(DeliverySchedule deliverySchedule) {

		Optional<DeliverySchedule> optional = repository.get(deliverySchedule.getId());

		if (optional.isPresent()) {
			throw new ResourceAlreadyExistsException(
					"resource already exists with id '" + deliverySchedule.getId() + "'");
		}

		optional = repository.create(deliverySchedule);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to create resource for " + deliverySchedule);
		}

		return optional.get();

	}

	public DeliverySchedule update(DeliverySchedule deliverySchedule) {

		Optional<DeliverySchedule> optional = repository.get(deliverySchedule.getId());

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException(
					"no resource found to update with id '" + deliverySchedule.getId() + "'");
		}

		optional = repository.update(deliverySchedule);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to update resource " + deliverySchedule);
		}

		return optional.get();

	}

	public DeliverySchedule get(Integer id) {

		Optional<DeliverySchedule> optional = repository.get(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<DeliverySchedule> getAll() {
		return repository.getAll();
	}

	public DeliverySchedule delete(Integer id) {

		Optional<DeliverySchedule> optional = repository.delete(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<DeliverySchedule> deleteAll() {
		return repository.deleteAll();
	}

	public void solve(Integer id) {

		// Get schedule with id
		Optional<DeliverySchedule> optional = repository.get(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		// start the solver route
		template.sendBody(SolverRoute.SOLVER_ROUTE_ENDPOINT, optional.get());

	}

	public DeliverySchedule getBestSolution(Integer id) {

		// start the get best solution route
		DeliverySchedule solvedSchedule = (DeliverySchedule) template.sendBody(SolverRoute.GET_BEST_SOLUTION_ENDPOINT,
				ExchangePattern.InOut, id);

		// update schedule with best solution
		Optional<DeliverySchedule> optional = repository.update(solvedSchedule);
		if (!optional.isPresent()) {
			throw new RepositoryException("failed to update resource. " + solvedSchedule);
		}

		return optional.get();

	}

}
