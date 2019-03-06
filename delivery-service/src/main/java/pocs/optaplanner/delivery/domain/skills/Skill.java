package pocs.optaplanner.delivery.domain.skills;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Skill {

	private Integer id;

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
