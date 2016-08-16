package pl.ultrakino.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Person;
import pl.ultrakino.model.PersonRole;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.repository.PersonRepository;
import pl.ultrakino.repository.UserRepository;
import pl.ultrakino.resources.FilmResource;
import pl.ultrakino.resources.PersonResource;
import pl.ultrakino.resources.PlayerResource;
import pl.ultrakino.resources.UserResource;
import pl.ultrakino.service.FilmService;
import pl.ultrakino.service.impl.FilmServiceImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilmServiceTest {


	@InjectMocks
	private FilmService filmService = new FilmServiceImpl();
	@Mock
	private FilmRepository filmRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private PersonRepository personRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		Person person = new Person();
		person.setId(1);
		person.setName("Nicolas Cage");
		person.setRole(PersonRole.ACTOR);
		when(personRepository.findByName(person.getName())).thenReturn(Optional.of(person));

		// TODO: Why it's not working?
		/*doAnswer(invocation -> {
			Film film = (Film) invocation.getArguments()[0];
			film.setId(1);
			System.out.println("Test");
			return film;
		}).when(filmRepository).create(new Film());*/
	}

	@Test
	public void shouldCreateFilm() {
		FilmResource film = new FilmResource();
		film.setTitle("Sharknado");

		PersonResource person1 = new PersonResource();
		person1.setName("Nicolas Cage");
//		person1.setRole(PersonRole.ACTOR);
		film.setCast(Arrays.asList(person1));

		PlayerResource player = new PlayerResource();
		player.setSrc("sharknadosrc");
		UserResource user = new UserResource();
		user.setUserId(1);
		player.setAddedBy(user);
		film.setPlayers(Arrays.asList(player));

		filmService.create(film);

//		verify(filmRepository).create(new Film());
	}

}
