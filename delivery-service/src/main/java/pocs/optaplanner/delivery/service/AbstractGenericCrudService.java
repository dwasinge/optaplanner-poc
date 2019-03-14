package pocs.optaplanner.delivery.service;

import java.util.Collection;
import java.util.Optional;

import pocs.optaplanner.delivery.domain.common.AbstractPersistable;
import pocs.optaplanner.delivery.exception.RepositoryException;
import pocs.optaplanner.delivery.exception.ResourceAlreadyExistsException;
import pocs.optaplanner.delivery.exception.ResourceNotFoundException;
import pocs.optaplanner.delivery.repository.AbstractMapRepository;

public abstract class AbstractGenericCrudService<T extends AbstractPersistable> {

	protected AbstractMapRepository<T> repository;

	public AbstractGenericCrudService(AbstractMapRepository<T> repository) {
		this.repository = repository;
	}

	public T create(T persistable) {

		Optional<T> optional = repository.get(persistable.getId());

		if (optional.isPresent()) {
			throw new ResourceAlreadyExistsException("resource already exists with id '" + persistable.getId() + "'");
		}

		optional = repository.create(persistable);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to create resource for " + persistable);
		}

		return optional.get();

	}

	public T update(T persistable) {

		Optional<T> optional = repository.get(persistable.getId());

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource found to update with id '" + persistable.getId() + "'");
		}

		optional = repository.update(persistable);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to update resource " + persistable);
		}

		return optional.get();

	}

	public T get(Integer id) {

		Optional<T> optional = repository.get(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<T> getAll() {
		return repository.getAll();
	}

	public T delete(Integer id) {

		Optional<T> optional = repository.delete(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<T> deleteAll() {
		return repository.deleteAll();
	}

}
