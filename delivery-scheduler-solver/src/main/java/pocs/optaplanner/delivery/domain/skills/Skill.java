package pocs.optaplanner.delivery.domain.skills;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Skill implements Serializable {

	private static final long serialVersionUID = 1L;

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
