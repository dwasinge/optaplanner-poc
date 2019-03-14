package pocs.optaplanner.delivery.service;

import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.domain.DeliveryAssignment;
import pocs.optaplanner.delivery.repository.AbstractMapRepository;

@Component
public class DeliveryAssignmentService extends AbstractGenericCrudService<DeliveryAssignment> {

	public DeliveryAssignmentService(AbstractMapRepository<DeliveryAssignment> repository) {
		super(repository);
	}

}
