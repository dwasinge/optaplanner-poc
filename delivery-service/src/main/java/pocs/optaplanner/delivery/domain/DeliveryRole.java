package pocs.optaplanner.delivery.domain;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;
import pocs.optaplanner.delivery.domain.skills.Skill;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryRole extends AbstractPersistable {

	private String name;
	private Set<Skill> requiredSkillSet;

	public DeliveryRole() {
	}

	public DeliveryRole(String name, Set<Skill> requiredSkillSet) {
		this.name = name;
		this.requiredSkillSet = requiredSkillSet;
	}

}
