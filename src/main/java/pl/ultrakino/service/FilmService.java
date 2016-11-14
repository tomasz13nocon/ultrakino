package pl.ultrakino.service;

import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resource.FilmDetailsResource;
import pl.ultrakino.resource.FilmResource;

import java.util.Collection;
import java.util.List;

public interface FilmService {

	Film findById(Integer id) throws NoRecordWithSuchIdException;

	Page<Film> find(MultiValueMap<String, String> params);

	void recommend(int filmId) throws NoRecordWithSuchIdException;

	void deleteRecommendation(int filmId) throws NoRecordWithSuchIdException;

	FilmResource toResource(Film film);

	List<FilmResource> toResources(Collection<Film> films);

	FilmDetailsResource toDetailsResource(Film film);
}
