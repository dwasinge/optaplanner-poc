package pocs.optaplanner.delivery.skills.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pocs.optaplanner.delivery.skills.Skill;
import pocs.optaplanner.delivery.skills.repository.MapRepository;
import pocs.optaplanner.delivery.skills.service.SkillsService;
import pocs.optaplanner.delivery.skills.utils.TestUtils;

public class SkillsResourceTest {

	private static final String BASE_API_PATH = "/api/v1";
	private static final String SKILLS_POST_API = BASE_API_PATH + "/skills";

	private MockMvc mvc;

	private ObjectMapper mapper = new ObjectMapper();

	private MapRepository repository;
	private SkillsService service;
	private SkillsResource resource;

	@Before
	public void setup() {

		repository = new MapRepository();
		service = new SkillsService(repository);
		resource = new SkillsResource(service);

		// We would need this line if we would not use MockitoJUnitRunner
		// MockitoAnnotations.initMocks(this);
		// Initializes the JacksonTester
		JacksonTester.initFields(this, mapper);
		// MockMvc standalone approach
		mvc = MockMvcBuilders.standaloneSetup(resource).build();

	}

	@Test
	public void testPostSuccess() throws Exception {

		// given
		Skill skill = new Skill("100", "night", "night flying");

		// when
		String json = mapper.writeValueAsString(skill);
		MockHttpServletResponse response = TestUtils.callPost(mvc, SKILLS_POST_API, json);

		// then
		Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		String responseJson = response.getContentAsString();
		Assert.assertNotNull(responseJson);
		
		Skill actual = mapper.readValue(responseJson, Skill.class);
		Assert.assertNotNull(actual);
		Assert.assertNotNull(actual.getId());
		Assert.assertEquals("100", actual.getCode());
		Assert.assertEquals("night", actual.getName());
		Assert.assertEquals("night flying", actual.getDescription());

	}


}
