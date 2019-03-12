package pocs.optaplanner.delivery.skills.domain;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Skill {

	@ApiModelProperty(hidden = true)
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
