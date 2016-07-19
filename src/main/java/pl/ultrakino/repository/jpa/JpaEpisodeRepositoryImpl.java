package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Episode;
import pl.ultrakino.repository.EpisodeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaEpisodeRepositoryImpl implements EpisodeRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public void save(Episode episode) {
		em.persist(episode);
	}

}
