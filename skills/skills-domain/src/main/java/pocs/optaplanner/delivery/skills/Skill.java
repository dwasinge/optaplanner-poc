package pocs.optaplanner.delivery.skills;

import lombok.Data;

@Data
public class Skill {

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
