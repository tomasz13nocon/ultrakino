package pl.ultrakino.repository;

import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

import java.util.List;

public interface FilmRepository {

	void create(Film film);

	/**
	 * @param id ID of the film, must not be null
	 * @throws NoRecordWithSuchIdException
	 */
	Film findById(int id) throws NoRecordWithSuchIdException;

	Page<Film> find(FilmQuery query);

	Film save(Film film);
}
