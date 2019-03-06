package pocs.optaplanner.delivery.skills.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.skills.domain.Skill;

@Component
public class MapRepository {

	private AtomicInteger id = new AtomicInteger(0);
	private Map<Integer, Skill> map = new HashMap<>();

	public Optional<Skill> create(Skill Skill) {

		// return if no Skill supplied
		if (null == Skill) {
			return Optional.empty();
		}

		// if Skill has an id
		if (null != Skill.getId()) {

			// return value if key already exists, otherwise empty
			if (map.containsKey(Skill.getId())) {
				return Optional.of(map.get(Skill.getId()));
			} else {
				return Optional.empty();
			}

		}

		// generate id
		int aId = id.getAndIncrement();

		Skill.setId(aId);
		map.put(aId, Skill);

		return Optional.of(Skill);

	}

	public Optional<Skill> update(Skill Skill) {

		if (null == Skill) {
			return Optional.empty();
		}

		if (null == Skill.getId() || !map.containsKey(Skill.getId())) {
			return Optional.empty();
		}

		map.replace(Skill.getId(), Skill);

		return Optional.of(Skill);

	}

	public Optional<Skill> get(Integer id) {

		if (!map.containsKey(id)) {
			return Optional.empty();
		}

		return Optional.ofNullable(map.get(id));

	}

	public Collection<Skill> getAll() {

		return map.values();

	}

	public Optional<Skill> delete(Integer id) {

		if (!map.containsKey(id)) {
			return Optional.empty();
		}

		Optional<Skill> optional = Optional.of(map.get(id));
		map.remove(id);

		return optional;

	}

	public Collection<Skill> deleteAll() {

		Collection<Skill> SkillCollection = map.values();
		map.clear();

		return SkillCollection;

	}

}
