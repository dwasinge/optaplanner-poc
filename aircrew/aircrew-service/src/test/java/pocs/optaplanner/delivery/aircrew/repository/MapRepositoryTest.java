package pocs.optaplanner.delivery.aircrew.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pocs.optaplanner.delivery.aircrew.Aircrew;
import pocs.optaplanner.delivery.skills.Skill;

public class MapRepositoryTest {

	private MapRepository repository;

	@Before
	public void setup() {
		repository = new MapRepository();
	}

	@Test
	public void testCreateSuccess() {

		// given
		Aircrew ac = new Aircrew();
		ac.setName("joe");
		ac.setSkillProficiencySet(new HashSet<>(Arrays.asList(new Skill("001", "pilot", "pilot"))));

		// when
		Optional<Aircrew> optional = repository.create(ac);

		// then
		Assert.assertNotNull(optional);
		Assert.assertTrue(optional.isPresent());
		Assert.assertNotNull(optional.get().getId());
		Assert.assertEquals("joe", optional.get().getName());

	}

	@Test
	public void testCreateIdNotNullAndIdNotAlreadyPersisted() {

		// given
		Aircrew ac = new Aircrew();
		ac.setId(11);
		ac.setName("joe");
		ac.setSkillProficiencySet(new HashSet<>(Arrays.asList(new Skill("001", "pilot", "pilot"))));

		// when
		Optional<Aircrew> optional = repository.create(ac);

		// then
		Assert.assertNotNull(optional);
		Assert.assertFalse(optional.isPresent());

	}

	@Test
	public void testCreateIdNotNullAndIdAlreadyPersisted() {

		// given
		Aircrew ac = new Aircrew();
		ac.setName("joe");
		ac.setSkillProficiencySet(new HashSet<>(Arrays.asList(new Skill("001", "pilot", "pilot"))));
		Optional<Aircrew> saved = repository.create(ac);

		// when
		Optional<Aircrew> optional = repository.create(saved.get());

		// then
		Assert.assertNotNull(optional);
		Assert.assertTrue(optional.isPresent());
		Assert.assertEquals(Integer.valueOf(0), optional.get().getId());

	}

	@Test
	public void testCreateAircrewNull() {

		// given

		// when
		Optional<Aircrew> optional = repository.create(null);

		Assert.assertNotNull(optional);
		Assert.assertFalse(optional.isPresent());

	}

}
