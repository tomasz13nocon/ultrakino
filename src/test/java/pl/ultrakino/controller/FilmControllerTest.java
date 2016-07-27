package pl.ultrakino.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
public class FilmControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext ctx;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
	}

	@Test
	public void shouldAllowCrossOrigin() {

	}

}
