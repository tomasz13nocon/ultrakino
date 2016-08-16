package pl.ultrakino.service;

import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmResource;

import java.util.List;
import java.util.Map;

public interface FilmService {

	Film create(FilmResource film);

	Film findById(Integer id) throws NoRecordWithSuchIdException;

	List<Film> findRecommended();

	List<Film> findNewest();

	List<Film> findMostWatched();

	List<Film> search(String query);

	List<Film> advancedSearch(MultiValueMap<String, String> params);
}
