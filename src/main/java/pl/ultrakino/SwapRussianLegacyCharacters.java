package pl.ultrakino;

import pl.ultrakino.model.Film;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.regex.Pattern;

public class SwapRussianLegacyCharacters {

	public static void main(String[] args) {
		new SwapRussianLegacyCharacters().run();
	}

	private void run() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Query q = em.createQuery("SELECT f FROM Film f WHERE " +
				"f.contentComponent.title LIKE '%&quot;%' OR " +
				"f.seriesFilmComponent.description LIKE '%&quot;%'");
		List<Film> list = q.getResultList();
		for (Film film: list) {
			String newTitle = film.getTitle().replaceAll(Pattern.quote("&quot;"), "\"");
			String newDescription = film.getDescription().replaceAll(Pattern.quote("&quot;"), "\"");
			film.setTitle(newTitle);
			film.setDescription(newDescription);
		}

		em.getTransaction().commit();

		em.close();
		emf.close();
	}

}
