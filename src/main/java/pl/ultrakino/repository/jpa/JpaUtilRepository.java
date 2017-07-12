package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.repository.UtilRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaUtilRepository implements UtilRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public <T> T merge(T t) {
		return em.merge(t);
	}
}
