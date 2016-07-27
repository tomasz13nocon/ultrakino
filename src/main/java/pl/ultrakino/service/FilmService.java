package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

public interface FilmService {

	void create(Film film);

	Film find(Integer id) throws NoRecordWithSuchIdException;

}
