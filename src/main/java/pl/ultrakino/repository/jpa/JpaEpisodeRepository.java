package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Episode;
import pl.ultrakino.repository.EpisodeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaEpisodeRepository implements EpisodeRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public void save(Episode episode) {
		em.persist(episode);
	}

	@Override
	public Optional<Episode> findBySeasonAndEpisodeNumber(int season, int episodeNumber) {
		List<Episode> episodes = em.createQuery("FROM Episode WHERE season=:season AND episodeNumber=:episodeNumber", Episode.class)
				.setParameter("season", season)
				.setParameter("episodeNumber", episodeNumber)
				.getResultList();
		if (episodes.isEmpty())
			return Optional.empty();
		return Optional.of(episodes.get(0));
	}

}
