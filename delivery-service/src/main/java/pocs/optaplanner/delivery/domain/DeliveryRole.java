package pocs.optaplanner.delivery.domain;

import java.util.Set;

import lombok.Data;
import pocs.optaplanner.delivery.domain.skills.Skill;

@Data
public class DeliveryRole {

	private String name;
	private Set<Skill> requiredSkillSet;

	public DeliveryRole() {
	}

	public DeliveryRole(String name, Set<Skill> requiredSkillSet) {
		this.name = name;
		this.requiredSkillSet = requiredSkillSet;
	}

}
