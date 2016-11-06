package pl.ultrakino.repository;

import pl.ultrakino.model.FilmCategory;

import java.util.List;
import java.util.Optional;

public interface FilmCategoryRepository {

	Optional<FilmCategory> findByName(String name);

	FilmCategory save(FilmCategory category);

	List<FilmCategory> findAll();
}
