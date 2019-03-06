package pocs.optaplanner.delivery.aircrew.rest;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pocs.optaplanner.delivery.aircrew.domain.Aircrew;
import pocs.optaplanner.delivery.aircrew.service.AircrewService;

@RestController
@RequestMapping("/api/v1")
public class AircrewResource {

	private AircrewService service;

	@Autowired
	public AircrewResource(AircrewService service) {
		this.service = service;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/aircrew", method = RequestMethod.POST)
	public Aircrew post(@RequestBody @NotNull @Valid Aircrew aircrew) {
		return service.create(aircrew);
	}

	@RequestMapping(path = "/aircrew", method = RequestMethod.PUT)
	public Aircrew put(@RequestBody @NotNull @Valid Aircrew aircrew) {
		return service.update(aircrew);
	}

	@RequestMapping(path = "/aircrew/{id}", method = RequestMethod.GET)
	public Aircrew get(@PathVariable Integer id) {
		return service.get(id);
	}

	@RequestMapping(path = "/aircrew", method = RequestMethod.GET)
	public Collection<Aircrew> getAll() {
		return service.getAll();
	}

	@RequestMapping(path = "/aircrew/{id}", method = RequestMethod.DELETE)
	public Aircrew delete(@PathVariable Integer id) {
		return service.delete(id);
	}

	@RequestMapping(path = "/aircrew", method = RequestMethod.DELETE)
	public Collection<Aircrew> deleteAll() {
		return service.deleteAll();
	}

}
