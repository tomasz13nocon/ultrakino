package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
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
	public Episode findById(int id) throws NoRecordWithSuchIdException {
		Episode episode = em.find(Episode.class, id);
		if (episode == null) throw new NoRecordWithSuchIdException("No Episode with id " + id);
		return episode;
	}

	@Override
	public Optional<Episode> findBySeasonAndEpisodeNumber(int season, int episodeNumber) {
		List<Episode> episodes = em.createQuery("SELECT e FROM Episode e WHERE e.season=:season AND e.episodeNumber=:episodeNumber", Episode.class)
				.setParameter("season", season)
				.setParameter("episodeNumber", episodeNumber)
				.getResultList();
		if (episodes.isEmpty())
			return Optional.empty();
		return Optional.of(episodes.get(0));
	}

	@Override
	public List<Episode> findBySeriesIdAndSeason(int seriesId, int season) {
		return em.createQuery("SELECT e FROM Episode e WHERE e.series.id=:seriesId AND e.season=:season", Episode.class)
				.setParameter("seriesId", seriesId)
				.setParameter("season", season)
				.getResultList();
	}

	@Override
	public Episode findByIdAndSeriesId(int id, int seriesId) throws NoRecordWithSuchIdException {
		List<Episode> episodes = em.createQuery("SELECT e FROM Episode e " +
				"LEFT JOIN FETCH e.ratings " +
				"LEFT JOIN FETCH e.players " +
				"WHERE e.id=:id AND e.series.id=:seriesId", Episode.class)
				.setParameter("id", id)
				.setParameter("seriesId", seriesId)
				.getResultList();
		if (episodes.isEmpty())
			throw new NoRecordWithSuchIdException();
		return episodes.get(0);
	}

	@Override
	public Optional<Episode> findNext(Episode episode) {
		List<Episode> episodes = em.createQuery("SELECT e FROM Episode e WHERE " +
				"e.series=:series AND " +
				"e.season=:season AND " +
				"e.episodeNumber>:episodeNumber " +
				"ORDER BY e.episodeNumber ASC", Episode.class)
				.setParameter("series", episode.getSeries())
				.setParameter("season", episode.getSeason())
				.setParameter("episodeNumber", episode.getEpisodeNumber())
				.getResultList();
		if (!episodes.isEmpty())
			return Optional.of(episodes.get(0));
		if (episode.getSeason() == 1)
			return Optional.empty();
		episodes = em.createQuery("SELECT e FROM Episode e WHERE " +
				"e.series=:series AND " +
				"e.season=(SELECT MIN(e.season) FROM Episode e WHERE e.season>:season) AND " +
				"e.episodeNumber>:episodeNumber " +
				"ORDER BY e.episodeNumber ASC", Episode.class)
				.setParameter("series", episode.getSeries())
				.setParameter("season", episode.getSeason())
				.setParameter("episodeNumber", episode.getEpisodeNumber())
				.getResultList();
		if (episodes.isEmpty())
			return Optional.empty();
		return Optional.of(episodes.get(0));
	}

	@SuppressWarnings("Duplicates")
	@Override
	public Optional<Episode> findPrevious(Episode episode) {
		List<Episode> episodes = em.createQuery("SELECT e FROM Episode e WHERE " +
				"e.series=:series AND " +
				"e.season=:season AND " +
				"e.episodeNumber<:episodeNumber " +
				"ORDER BY e.episodeNumber DESC", Episode.class)
				.setParameter("series", episode.getSeries())
				.setParameter("season", episode.getSeason())
				.setParameter("episodeNumber", episode.getEpisodeNumber())
				.getResultList();
		if (!episodes.isEmpty())
			return Optional.of(episodes.get(0));
		if (episode.getSeason() == 1)
			return Optional.empty();
		episodes = em.createQuery("SELECT e FROM Episode e WHERE " +
				"e.series=:series AND " +
				"e.season=(SELECT MAX(e.season) FROM Episode e WHERE e.season<:season) AND " +
				"e.episodeNumber<:episodeNumber " +
				"ORDER BY e.episodeNumber DESC", Episode.class)
				.setParameter("series", episode.getSeries())
				.setParameter("season", episode.getSeason())
				.setParameter("episodeNumber", episode.getEpisodeNumber())
				.getResultList();
		if (episodes.isEmpty())
			return Optional.empty();
		return Optional.of(episodes.get(0));
	}

}
