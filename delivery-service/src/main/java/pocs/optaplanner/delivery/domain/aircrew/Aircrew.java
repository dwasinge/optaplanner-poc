package pocs.optaplanner.delivery.domain.aircrew;

import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import pocs.optaplanner.delivery.domain.skills.Skill;

import lombok.Data;

@Data
public class Aircrew {

	private Integer id;

	@NotBlank
	private String name;
	@NotNull
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
