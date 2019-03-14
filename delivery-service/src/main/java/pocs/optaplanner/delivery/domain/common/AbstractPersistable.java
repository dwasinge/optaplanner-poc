package pocs.optaplanner.delivery.domain.common;

import lombok.Data;

@Data
public abstract class AbstractPersistable {

	protected Integer id;

	protected AbstractPersistable() {
	}

	protected AbstractPersistable(Integer id) {
		this.id = id;
	}

}
