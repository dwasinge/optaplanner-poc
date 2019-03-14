package pocs.optaplanner.delivery.domain.skills;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Skill extends AbstractPersistable {

	private String code;
	private String name;

	private String description;

	public Skill() {
	}

	public Skill(Integer id, String code, String name, String description) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	public Skill(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

}

