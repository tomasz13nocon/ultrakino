package pl.ultrakino.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Views;
import pl.ultrakino.resources.FilmResource;
import pl.ultrakino.resources.assemblers.FilmResourceAsm;
import pl.ultrakino.service.FilmService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pl.ultrakino.web.RestAPIDefinitions.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/films", produces = "application/json;charset=utf-8")
public class FilmController {

	private FilmService filmService;
	private FilmResourceAsm filmResourceAsm;

	@Autowired
	public FilmController(FilmService filmService, FilmResourceAsm filmResourceAsm) {
		this.filmService = filmService;
		this.filmResourceAsm = filmResourceAsm;
	}

//	@JsonView(Views.FilmCreation.class)
	@PostMapping
	public ResponseEntity<FilmResource> createFilm(@RequestBody FilmResource filmResource) throws URISyntaxException {
		Film film = filmService.create(filmResource);
		// Get resource representation of actually created Film and its HATEOAS links
		filmResource = filmResourceAsm.toResource(film);
		return ResponseEntity.created(new URI(filmResource.getLink("self").getHref())).body(filmResource);
	}

	@GetMapping("/{filmId}")
	public FilmResource getFilm(@PathVariable int filmId) throws NoRecordWithSuchIdException {
		Film film = filmService.findById(filmId);
		return filmResourceAsm.cast().players().categories().toResource(film);
	}

	@GetMapping("/recommended")
	public List<FilmResource> getRecommendedFilms() {
		List<Film> films = filmService.findRecommended();
		return filmResourceAsm.toResources(films);
	}

	@GetMapping("/newest")
	public List<FilmResource> getNewestFilms() {
		List<Film> films = filmService.findNewest();
		return filmResourceAsm.toResources(films);
	}

	@GetMapping("/most-watched")
	public List<FilmResource> getMostWatchedFilms() {
		List<Film> films = filmService.findMostWatched();
		return filmResourceAsm.toResources(films);
	}

	@GetMapping("/search")
	public List<FilmResource> searchForFilms(@RequestParam("query") String query) {
		List<Film> films = filmService.search(query);
		return filmResourceAsm.toResources(films);
	}

	@GetMapping("/advanced-search")
	public List<FilmResource> advancedSearch(@RequestParam MultiValueMap<String, String> params) {
		List<Film> films = filmService.advancedSearch(params);
		return filmResourceAsm.toResources(films);
	}

	@ExceptionHandler(URISyntaxException.class)
	public ResponseEntity uriSyntaxException() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}














