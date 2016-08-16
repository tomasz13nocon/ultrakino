package pl.ultrakino;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.assemblers.FilmResourceAsm;

@ContextConfiguration("classpath:spring/spring-config.xml")
public class FilmAssemblerTest {

	@Autowired
	private FilmResourceAsm filmResourceAsm;

	@Test
	public void shouldAssembleFilmWithCollections() {
		Film film = new Film();
		film.setTitle("Sharknado");

		/*filmResourceAsm.resource().cast().players().categories().build();
		filmResourceAsm.getCastAsm().includeSth().build();*/
	}

}
