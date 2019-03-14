package pocs.optaplanner.delivery.aircrew.domain.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class AbstractPersistable {

	@ApiModelProperty(hidden = true)
	protected Integer id;

	protected AbstractPersistable() {
	}

	protected AbstractPersistable(Integer id) {
		this.id = id;
	}

}
