package pl.ultrakino.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.ContentQuery;
import pl.ultrakino.repository.Page;
import pl.ultrakino.repository.SeriesRepository;
import pl.ultrakino.resources.SeriesResource;
import pl.ultrakino.service.SeriesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeriesServiceImpl implements SeriesService {

	private SeriesRepository seriesRepository;

	@Override
	public Series findById(int seriesId) throws NoRecordWithSuchIdException {
		return seriesRepository.findById(seriesId);
	}

	@Override
	public Page<Series> find(MultiValueMap<String, String> params) {
		return seriesRepository.find(new ContentQuery(params));
	}

	@Override
	public SeriesResource toResource(Series series) {
		SeriesResource res = new SeriesResource();
		res.setUid(series.getId());
		res.setTitle(series.getTitle());
		res.setYear(series.getYear());
		res.setRating(series.getRating());
		res.setTimesRated(series.getTimesRated());
		res.setOriginalTitle(series.getOriginalTitle());
		res.setDescription(series.getDescription());
		res.setCoverFilename(series.getCoverFilename());
		res.setWorldPremiere(series.getWorldPremiere());
		res.setCategories(series.getCategories());
		res.setSeasonCount(series.getSeasonCount());
		res.setEpisodeCount(series.getEpisodeCount());
		return res;
	}

	@Override
	public List<SeriesResource> toResources(List<Series> series) {
		return series.stream()
				.map(this::toResource)
				.collect(Collectors.toList());
	}
}
