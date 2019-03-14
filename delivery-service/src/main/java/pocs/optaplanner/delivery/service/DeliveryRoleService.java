package pocs.optaplanner.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.domain.DeliveryRole;
import pocs.optaplanner.delivery.repository.AbstractMapRepository;

@Component
public class DeliveryRoleService extends AbstractGenericCrudService<DeliveryRole>{

	@Autowired
	public DeliveryRoleService(AbstractMapRepository<DeliveryRole> repository) {
		super(repository);
	}

}
