package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.SeriesRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaSeriesRepository implements SeriesRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Series> findByTitleAndYear(String title, int year) {
		List<Series> series = em.createQuery("FROM Series WHERE title=:title AND year=:year", Series.class)
				.setParameter("title", title)
				.setParameter("year", year)
				.getResultList();
		if (series.isEmpty())
			return Optional.empty();
		return Optional.of(series.get(0));
	}
}
