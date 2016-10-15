package pl.ultrakino.repository;

import pl.ultrakino.model.Series;

import java.util.Optional;

public interface SeriesRepository {

	Optional<Series> findByTitleAndYear(String title, int year);

	Series save(Series series);
}
