package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Film;
import pl.ultrakino.repository.FilmRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaFilmRepositoryImpl implements FilmRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Film film) {
		em.persist(film);
	}

}
