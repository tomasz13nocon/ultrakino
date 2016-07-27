package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

public interface FilmRepository {

	void create(Film film);

	/**
	 * @param id must not be null
	 * @throws IllegalArgumentException when {@code id} is null
	 * @throws NoRecordWithSuchIdException
	 */
	Film find(Integer id) throws NoRecordWithSuchIdException;



}
