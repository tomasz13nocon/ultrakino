package pl.ultrakino.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.repository.UserRepository;
import pl.ultrakino.service.MainService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
@ActiveProfiles("dev")
public class RelationsTest {

	@Autowired
	private MainService service;

	@Test
	public void test() {
//		Film sharknado = new Film();
//		sharknado.setTitle("Sharknado");
//
//		Player p1 = new Player();
//		p1.setSrc("source");
//		p1.setContent(sharknado);
//
//		sharknado.setPlayers(Arrays.asList(p1));

//		service.saveFilm(sharknado);
	}

}
