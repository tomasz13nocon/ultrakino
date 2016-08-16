package pl.ultrakino;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:spring/spring-config.xml")
@ActiveProfiles("test")
public class HibernateCascadeTest {

	@PersistenceContext
	private EntityManager em;
//	@Autowired
//	private FilmService filmService;

	@Test
	@Transactional
	@Commit
	public void test() {
		User user = new User();
		user.setUsername("januszex69");

		Film film = new Film();
		film.setTitle("Sharknado 2");
		Player player = new Player();
		player.setSrc("elo");
		player.setAddedBy(em.getReference(User.class, 1));
		film.setPlayers(Arrays.asList(player));

		em.persist(film);
	}

}
