package pl.ultrakino.repository;

import pl.ultrakino.model.Episode;

import java.util.Optional;

public interface EpisodeRepository {

	void save(Episode episode);

	Optional<Episode> findBySeasonAndEpisodeNumber(int season, int episodeNumber);

}
