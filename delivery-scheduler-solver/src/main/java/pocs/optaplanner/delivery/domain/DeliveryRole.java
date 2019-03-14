package pocs.optaplanner.delivery.domain;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class DeliveryRole implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;
	private Set<Integer> requiredSkillCodeSet;

	public DeliveryRole() {
	}

	public DeliveryRole(String name, Set<Integer> requiredSkillCodeSet) {
		this.name = name;
		this.requiredSkillCodeSet = requiredSkillCodeSet;
	}

}
