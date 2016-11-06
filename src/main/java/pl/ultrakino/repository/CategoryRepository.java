package pl.ultrakino.repository;

import pl.ultrakino.model.FilmCategory;

import java.util.Optional;

public interface CategoryRepository {

	Optional<FilmCategory> findByName(String name);

	FilmCategory save(FilmCategory category);
}
