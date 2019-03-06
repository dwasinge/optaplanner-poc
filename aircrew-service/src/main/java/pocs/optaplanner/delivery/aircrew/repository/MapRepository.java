package pocs.optaplanner.delivery.aircrew.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import pocs.optaplanner.delivery.aircrew.domain.Aircrew;

@Component
public class MapRepository {

	private AtomicInteger id = new AtomicInteger(0);
	private Map<Integer, Aircrew> map = new HashMap<>();

	public Optional<Aircrew> create(Aircrew aircrew) {

		// return if no aircrew supplied
		if (null == aircrew) {
			return Optional.empty();
		}

		// if aircrew has an id
		if (null != aircrew.getId()) {

			// return value if key already exists, otherwise empty
			if (map.containsKey(aircrew.getId())) {
				return Optional.of(map.get(aircrew.getId()));
			} else {
				return Optional.empty();
			}

		}

		// generate id
		int aId = id.getAndIncrement();

		aircrew.setId(aId);
		map.put(aId, aircrew);

		return Optional.of(aircrew);

	}

	public Optional<Aircrew> update(Aircrew aircrew) {

		if (null == aircrew) {
			return Optional.empty();
		}

		if (null == aircrew.getId() || !map.containsKey(aircrew.getId())) {
			return Optional.empty();
		}

		map.replace(aircrew.getId(), aircrew);

		return Optional.of(aircrew);

	}

	public Optional<Aircrew> get(Integer id) {

		if (!map.containsKey(id)) {
			return Optional.empty();
		}

		return Optional.ofNullable(map.get(id));

	}

	public Collection<Aircrew> getAll() {

		return map.values();

	}

	public Optional<Aircrew> delete(Integer id) {

		if (!map.containsKey(id)) {
			return Optional.empty();
		}

		Optional<Aircrew> optional = Optional.of(map.get(id));
		map.remove(id);

		return optional;

	}

	public Collection<Aircrew> deleteAll() {

		Collection<Aircrew> aircrewCollection = map.values();
		map.clear();

		return aircrewCollection;

	}

}
