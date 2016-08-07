package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.repository.FilmRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaFilmRepository implements FilmRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Film film) {
		em.persist(film);
	}

	@Override
	public Film findById(Integer id) throws NoRecordWithSuchIdException {
		if (id == null) throw new IllegalArgumentException("id must not be null.");
		Film film = em.find(Film.class, id);
		if (film == null) throw new NoRecordWithSuchIdException();
		return film;
	}

	@Override
	public Film findByIdAndFetchAll(Integer id) throws NoRecordWithSuchIdException {
		return null;
	}

	@Override
	public List<Film> findRecommended() {
		Query q = em.createQuery("SELECT f FROM Film f ORDER BY f.recommendationDate DESC");
		q.setMaxResults(10);
		return q.getResultList();
	}
}
