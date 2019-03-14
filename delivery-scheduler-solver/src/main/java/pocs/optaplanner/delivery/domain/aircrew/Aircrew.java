package pocs.optaplanner.delivery.domain.aircrew;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import lombok.Data;

@Data
public class Aircrew implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
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
