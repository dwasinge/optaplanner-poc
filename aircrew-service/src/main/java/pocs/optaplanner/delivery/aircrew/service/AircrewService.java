package pocs.optaplanner.delivery.aircrew.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.aircrew.domain.Aircrew;
import pocs.optaplanner.delivery.aircrew.exception.RepositoryException;
import pocs.optaplanner.delivery.aircrew.exception.ResourceAlreadyExistsException;
import pocs.optaplanner.delivery.aircrew.exception.ResourceNotFoundException;
import pocs.optaplanner.delivery.aircrew.repository.MapRepository;

@Component
public class AircrewService {

	private MapRepository repository;

	@Autowired
	public AircrewService(MapRepository repository) {
		this.repository = repository;
	}

	public Aircrew create(Aircrew aircrew) {

		Optional<Aircrew> optional = repository.get(aircrew.getId());

		if (optional.isPresent()) {
			throw new ResourceAlreadyExistsException("resource with id '" + aircrew.getId() + "' already exists.");
		}

		optional = repository.create(aircrew);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to create resource for " + aircrew);
		}

		return optional.get();

	}

	public Aircrew update(Aircrew aircrew) {

		Optional<Aircrew> optional = repository.get(aircrew.getId());

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource found to update with id '" + aircrew.getId() + "'");
		}

		optional = repository.update(aircrew);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to update resource " + aircrew);
		}

		return optional.get();

	}

	public Aircrew get(Integer id) {

		Optional<Aircrew> optional = repository.get(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<Aircrew> getAll() {

		return repository.getAll();

	}

	public Aircrew delete(Integer id) {

		Optional<Aircrew> optional = repository.delete(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<Aircrew> deleteAll() {

		return repository.deleteAll();

	}

}
