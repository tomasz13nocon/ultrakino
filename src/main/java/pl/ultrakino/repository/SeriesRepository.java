package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Series;

import java.util.Optional;

public interface SeriesRepository {



	Optional<Series> findByTitleAndYear(String title, int year);

	Optional<Series> findByTvseriesonlineTitleAndYear(String tvseriesonlineTitle, int year);

	Series save(Series series);

	Series findById(int seriesId) throws NoRecordWithSuchIdException;
}
