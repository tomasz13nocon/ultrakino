package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.repository.CategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaCategoryRepository implements CategoryRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<FilmCategory> findByName(String name) {
		List<FilmCategory> categories = em.createQuery("FROM Category WHERE name=:name", FilmCategory.class)
				.setParameter("name", name)
				.getResultList();
		if (categories.isEmpty()) return Optional.empty();
		return Optional.of(categories.get(0));
	}

	@Override
	public FilmCategory save(FilmCategory category) {
		em.persist(category);
		return category;
	}
}
