package pocs.optaplanner.delivery.domain.aircrew;

import java.util.Collection;
import java.util.Set;

import lombok.Data;
import pocs.optaplanner.delivery.domain.skills.Skill;

@Data
public class Aircrew {

	private Integer id;
	private String name;
	private Set<Skill> skillProficiencySet;

	public Aircrew() {
	}

	public Aircrew(String name, Set<Skill> skillProficiencySet) {
		this.name = name;
		this.skillProficiencySet = skillProficiencySet;
	}

	public boolean hasSkill(Skill skill) {
		return skillProficiencySet.contains(skill);
	}

	public boolean hasSkills(Collection<Skill> skills) {
		return skillProficiencySet.containsAll(skills);
	}

}
