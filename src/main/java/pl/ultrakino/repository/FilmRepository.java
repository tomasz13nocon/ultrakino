package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

import java.util.List;

public interface FilmRepository {

	void create(Film film);

	/**
	 * @param id must not be null
	 * @throws IllegalArgumentException when {@code id} is null
	 * @throws NoRecordWithSuchIdException
	 */
	Film findById(Integer id) throws NoRecordWithSuchIdException;

	Film findByIdAndFetchAll(Integer id) throws NoRecordWithSuchIdException;

	List<Film> findRecommended();
}
