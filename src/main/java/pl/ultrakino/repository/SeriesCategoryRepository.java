package pl.ultrakino.repository;

import pl.ultrakino.model.SeriesCategory;

import java.util.List;
import java.util.Optional;

public interface SeriesCategoryRepository {

	Optional<SeriesCategory> findByName(String name);

	SeriesCategory save(SeriesCategory category);

	List<SeriesCategory> findAll();
}
