package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

import java.util.Optional;

public interface FilmRepository {

	Film save(Film film);

	/**
	 * @param id ID of the film, must not be null
	 * @throws NoRecordWithSuchIdException
	 */
	Film findById(int id) throws NoRecordWithSuchIdException;

	Page<Film> find(ContentQuery query);

	Optional<Film> findByTitleAndYear(String title, int year);

	Optional<Film> findByFilmwebId(String filmwebId);
}
