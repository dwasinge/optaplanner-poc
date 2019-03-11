package pocs.optaplanner.delivery.domain.skills;

import java.io.Serializable;

import lombok.Data;

@Data
public class Skill implements Serializable {

	private static final long serialVersionUID = 1L;

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
