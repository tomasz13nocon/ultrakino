package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.ContentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaContentRepository implements ContentRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Content findById(int contentId) throws NoRecordWithSuchIdException {
		Content content = em.find(Content.class, contentId);
		if (content == null)
			throw new NoRecordWithSuchIdException();
		return content;
		/*List<Film> films = em
				.createQuery("SELECT f FROM Film f LEFT JOIN FETCH f.ratings WHERE f.id=:contentId", Film.class)
				.setParameter("contentId", contentId)
				.getResultList();
		if (!films.isEmpty())
			return films.get(0);

		List<Episode> episodes = em
				.createQuery("SELECT e FROM Episode e LEFT JOIN FETCH e.ratings WHERE e.id=:contentId", Episode.class)
				.setParameter("contentId", contentId)
				.getResultList();
		if (!episodes.isEmpty())
			return episodes.get(0);

		List<Series> series = em
				.createQuery("SELECT s FROM Series s LEFT JOIN FETCH s.ratings WHERE s.id=:contentId", Series.class)
				.setParameter("contentId", contentId)
				.getResultList();
		if (!series.isEmpty())
			return series.get(0);

		throw new NoRecordWithSuchIdException();*/
	}

	@Override
	public <T> void merge(T t) {
		em.merge(t);
	}
}
