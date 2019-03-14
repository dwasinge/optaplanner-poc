package pocs.optaplanner.delivery.domain;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryRole extends AbstractPersistable {

	private String name;
	private Set<Integer> requiredSkillCodeSet;

	public DeliveryRole() {
	}

	public DeliveryRole(String name, Set<Integer> requiredSkillCodeSet) {
		this.name = name;
		this.requiredSkillCodeSet = requiredSkillCodeSet;
	}

}
