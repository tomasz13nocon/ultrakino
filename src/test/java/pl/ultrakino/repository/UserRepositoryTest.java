package pl.ultrakino.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.ultrakino.model.Film;
import pl.ultrakino.service.MainService;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public class UserRepositoryTest {

	@Autowired
	MainService mainService;

	@Test
	public void testqwe() {

		Film film = new Film();


		mainService.saveFilm(film);
	}

}
