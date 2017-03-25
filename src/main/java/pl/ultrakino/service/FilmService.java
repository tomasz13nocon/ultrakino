package pl.ultrakino.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
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

	Film save(Film film);

	void recommend(int filmId) throws NoRecordWithSuchIdException;

	void deleteRecommendation(int filmId) throws NoRecordWithSuchIdException;

	FilmResource toResource(Film film);

	List<FilmResource> toResources(Collection<Film> films);

	FilmDetailsResource toDetailsResource(Film film);

	/**
	 * Create a Film object for a newly added film, from JSON represented in ObjectNode.
 	 * @param filmJson JSON representation of new Film
	 * @return Created Film object
	 */
	Film extractNewFilm(ObjectNode filmJson);
}
