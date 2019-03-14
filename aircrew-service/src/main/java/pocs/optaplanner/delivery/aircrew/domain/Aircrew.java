package pocs.optaplanner.delivery.aircrew.domain;

import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.aircrew.domain.common.AbstractPersistable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Aircrew extends AbstractPersistable {

	@NotBlank
	private String name;
	@NotNull
	private Set<Integer> skillProficiencyIdSet;

	public Aircrew() {
	}

	public Aircrew(String name, Set<Integer> skillProficiencyIdSet) {
		this.name = name;
		this.skillProficiencyIdSet = skillProficiencyIdSet;
	}

	public boolean hasSkill(Integer skillId) {
		return skillProficiencyIdSet.contains(skillId);
	}

	public boolean hasSkills(Collection<Integer> skillIds) {
		return skillProficiencyIdSet.containsAll(skillIds);
	}

}
