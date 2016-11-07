package pl.ultrakino.service;

import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Rating;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resources.FilmDetailsResource;
import pl.ultrakino.resources.FilmResource;

import java.util.List;
import java.util.Map;

public interface FilmService {

	Film create(FilmDetailsResource film);

	FilmDetailsResource findById(Integer id) throws NoRecordWithSuchIdException;

	Page<Film> find(MultiValueMap<String, String> params);

	void recommend(int filmId) throws NoRecordWithSuchIdException;

	void deleteRecommendation(int filmId) throws NoRecordWithSuchIdException;

}
