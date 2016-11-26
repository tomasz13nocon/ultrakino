package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Series;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository {

	void save(Episode episode);

	Episode findById(int id) throws NoRecordWithSuchIdException;

	Optional<Episode> findBySeriesIdAndSeasonAndEpisodeNumber(int seriesId, int season, int episodeNumber);

	List<Episode> findBySeriesIdAndSeason(int seriesId, int season);

	Episode findByIdAndSeriesId(int id, int seriesId) throws NoRecordWithSuchIdException;

	Optional<Episode> findNext(Episode episode);

	Optional<Episode> findPrevious(Episode episode);
}
