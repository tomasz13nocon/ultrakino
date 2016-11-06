package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.model.SeriesCategory;
import pl.ultrakino.repository.SeriesCategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaSeriesCategoryRepository implements SeriesCategoryRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<SeriesCategory> findByName(String name) {
		List<SeriesCategory> categories = em.createQuery("SELECT s FROM SeriesCategory s WHERE s.name=:name", SeriesCategory.class)
				.setParameter("name", name)
				.getResultList();
		if (categories.isEmpty()) return Optional.empty();
		return Optional.of(categories.get(0));
	}

	@Override
	public SeriesCategory save(SeriesCategory category) {
		em.persist(category);
		return category;
	}

	@Override
	public List<SeriesCategory> findAll() {
		return em.createQuery("SELECT s FROM SeriesCategory s", SeriesCategory.class).getResultList();
	}
}
