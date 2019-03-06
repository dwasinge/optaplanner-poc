package pocs.optaplanner.delivery.skills.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.skills.domain.Skill;
import pocs.optaplanner.delivery.skills.exception.RepositoryException;
import pocs.optaplanner.delivery.skills.exception.ResourceAlreadyExistsException;
import pocs.optaplanner.delivery.skills.exception.ResourceNotFoundException;
import pocs.optaplanner.delivery.skills.repository.MapRepository;

@Component
public class SkillsService {

	private MapRepository repository;

	@Autowired
	public SkillsService(MapRepository repository) {
		this.repository = repository;
	}

	public Skill create(Skill skill) {

		Optional<Skill> optional = repository.get(skill.getId());

		if (optional.isPresent()) {
			throw new ResourceAlreadyExistsException("resource with id '" + skill.getId() + "' already exists.");
		}

		optional = repository.create(skill);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to create resource for " + skill);
		}

		return optional.get();

	}

	public Skill update(Skill skill) {

		Optional<Skill> optional = repository.get(skill.getId());

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource found to update with id '" + skill.getId() + "'");
		}

		optional = repository.update(skill);

		if (!optional.isPresent()) {
			throw new RepositoryException("failed to update resource " + skill);
		}

		return optional.get();

	}

	public Skill get(Integer id) {

		Optional<Skill> optional = repository.get(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<Skill> getAll() {

		return repository.getAll();

	}

	public Skill delete(Integer id) {

		Optional<Skill> optional = repository.delete(id);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		return optional.get();

	}

	public Collection<Skill> deleteAll() {

		return repository.deleteAll();

	}

}
