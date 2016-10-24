package pl.ultrakino.service;

import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Series;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resources.SeriesResource;

import java.util.List;

public interface SeriesService {

	Series findById(int seriesId) throws NoRecordWithSuchIdException;

	Page<Series> find(MultiValueMap<String, String> params);

	SeriesResource toResource(Series series);

	List<SeriesResource> toResources(List<Series> series);
}
