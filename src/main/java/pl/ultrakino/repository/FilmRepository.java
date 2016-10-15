package pl.ultrakino.repository;

import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {

	void create(Film film);

	/**
	 * @param id ID of the film, must not be null
	 * @throws NoRecordWithSuchIdException
	 */
	Film findById(int id) throws NoRecordWithSuchIdException;

	Page<Film> find(FilmQuery query);

	Film save(Film film);

	Optional<Film> findByTitleAndYear(String title, int year);

	Optional<Film> findByFilmwebId(String filmwebId);
}
