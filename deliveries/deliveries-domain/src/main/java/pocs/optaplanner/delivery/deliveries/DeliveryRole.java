package pocs.optaplanner.delivery.deliveries;

import java.util.Set;

import lombok.Data;
import pocs.optaplanner.delivery.skills.Skill;

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
