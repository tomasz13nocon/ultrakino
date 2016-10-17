package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Series;

public interface SeriesService {

	Series findById(int seriesId) throws NoRecordWithSuchIdException;
}
