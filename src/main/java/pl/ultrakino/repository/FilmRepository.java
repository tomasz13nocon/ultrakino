package pl.ultrakino.repository;

import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmRepository {

	void create(Film film);

	/**
	 * @param id must not be null
	 * @throws IllegalArgumentException when {@code id} is null
	 * @throws NoRecordWithSuchIdException
	 */
	Film findById(Integer id) throws NoRecordWithSuchIdException;

	List<Film> findRecommended();

	List<Film> findNewest();

	List<Film> findMostWatched();

	List<Film> search(String query);

	List<Film> advancedSearch(MultiValueMap<String, String> params);
}
