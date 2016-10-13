package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Category;
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
	public Optional<Category> findByName(String name) {
		List<Category> categories = em.createQuery("FROM Category WHERE name=:name", Category.class)
				.setParameter("name", name)
				.getResultList();
		if (categories.isEmpty()) return Optional.empty();
		return Optional.of(categories.get(0));
	}

	@Override
	public Category save(Category category) {
		em.persist(category);
		return category;
	}
}
