package pocs.optaplanner.delivery.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import pocs.optaplanner.delivery.domain.common.AbstractPersistable;

public abstract class AbstractMapRepository<T extends AbstractPersistable> {

	private AtomicInteger id = new AtomicInteger(0);
	private Map<Integer, T> map = new HashMap<>();

	public Optional<T> create(T obj) {

		// return if no Object supplied
		if (null == obj) {
			return Optional.empty();
		}

		// if Object has an id
		if (null != obj.getId()) {

			// return value if key already exists, otherwise empty
			if (map.containsKey(obj.getId())) {
				return Optional.of(map.get(obj.getId()));
			} else {
				return Optional.empty();
			}

		}

		// generate id
		int aId = id.getAndIncrement();

		obj.setId(aId);
		map.put(aId, obj);

		return Optional.of(obj);

	}

	public Optional<T> update(T obj) {

		if (null == obj) {
			return Optional.empty();
		}

		if (null == obj.getId() || !map.containsKey(obj.getId())) {
			return Optional.empty();
		}

		map.replace(obj.getId(), obj);

		return Optional.of(obj);

	}

	public Optional<T> get(Integer id) {

		if (!map.containsKey(id)) {
			return Optional.empty();
		}

		return Optional.ofNullable(map.get(id));

	}

	public Collection<T> getAll() {

		return map.values();

	}

	public Optional<T> delete(Integer id) {

		if (!map.containsKey(id)) {
			return Optional.empty();
		}

		Optional<T> optional = Optional.of(map.get(id));
		map.remove(id);

		return optional;

	}

	public Collection<T> deleteAll() {

		Collection<T> objectCollection = map.values();
		map.clear();

		return objectCollection;

	}

}
