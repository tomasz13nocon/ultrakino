package pl.ultrakino.service;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

import java.util.List;

public interface FilmService {

	void create(Film film);

	Film findById(Integer id) throws NoRecordWithSuchIdException;

	List<Film> findRecommended();

}
