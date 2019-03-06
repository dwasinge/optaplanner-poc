package pocs.optaplanner.delivery.skills.rest;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pocs.optaplanner.delivery.skills.domain.Skill;
import pocs.optaplanner.delivery.skills.service.SkillsService;

@RestController
@RequestMapping("/api/v1")
public class SkillsResource {

	private SkillsService service;

	public SkillsResource(SkillsService service) {
		this.service = service;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/skills", method = RequestMethod.POST)
	public Skill post(@RequestBody @NotNull @Valid Skill aircrew) {
		return service.create(aircrew);
	}

	@RequestMapping(path = "/skills", method = RequestMethod.PUT)
	public Skill put(@RequestBody @NotNull @Valid Skill aircrew) {
		return service.update(aircrew);
	}

	@RequestMapping(path = "/skills/{id}", method = RequestMethod.GET)
	public Skill get(@PathVariable Integer id) {
		return service.get(id);
	}

	@RequestMapping(path = "/skills", method = RequestMethod.GET)
	public Collection<Skill> getAll() {
		return service.getAll();
	}

	@RequestMapping(path = "/skills/{id}", method = RequestMethod.DELETE)
	public Skill delete(@PathVariable Integer id) {
		return service.delete(id);
	}

	@RequestMapping(path = "/skills", method = RequestMethod.DELETE)
	public Collection<Skill> deleteAll() {
		return service.deleteAll();
	}

}
