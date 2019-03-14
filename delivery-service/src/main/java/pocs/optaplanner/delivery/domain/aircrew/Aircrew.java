package pocs.optaplanner.delivery.domain.aircrew;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Aircrew extends AbstractPersistable {

	private String name;
	private Set<Integer> skillProficiencyIdSet;

	public Aircrew() {
	}

	public Aircrew(String name, Set<Integer> skillProficiencyIdSet) {
		this.name = name;
		this.skillProficiencyIdSet = skillProficiencyIdSet;
	}

}
