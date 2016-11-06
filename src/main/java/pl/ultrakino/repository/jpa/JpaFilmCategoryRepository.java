package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.repository.FilmCategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaFilmCategoryRepository implements FilmCategoryRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<FilmCategory> findByName(String name) {
		List<FilmCategory> categories = em.createQuery("SELECT f FROM FilmCategory f WHERE f.name=:name", FilmCategory.class)
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

	@Override
	public List<FilmCategory> findAll() {
		return em.createQuery("SELECT f FROM FilmCategory f", FilmCategory.class).getResultList();
	}
}
