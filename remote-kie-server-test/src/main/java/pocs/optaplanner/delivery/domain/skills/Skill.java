package pocs.optaplanner.delivery.domain.skills;

import lombok.Data;

@Data
public class Skill {

	private Integer id;
	private String code;
	private String name;

	private String description;

	public Skill() {
	}

	public Skill(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

}
