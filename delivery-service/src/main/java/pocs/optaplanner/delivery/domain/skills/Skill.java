package pocs.optaplanner.delivery.domain.skills;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Skill extends AbstractPersistable {

	@NotBlank
	private String code;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	public Skill() {
	}

	public Skill(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

}
