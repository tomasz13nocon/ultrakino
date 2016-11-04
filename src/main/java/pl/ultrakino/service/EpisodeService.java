package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Episode;
import pl.ultrakino.model.Series;
import pl.ultrakino.resources.EpisodeDetailsResource;
import pl.ultrakino.resources.EpisodeResource;

import java.util.List;

public interface EpisodeService {

	EpisodeResource toResource(Episode episode);

	List<EpisodeResource> toResources(List<Episode> episodes);

	EpisodeDetailsResource toDetailsResource(Episode episode);

	List<Episode> findBySeriesIdAndSeason(int seriesId, int season);

	Episode findByIdAndSeriesId(int id, int seriesId) throws NoRecordWithSuchIdException;
}
