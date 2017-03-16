package pl.ultrakino.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resource.FilmResource;
import pl.ultrakino.service.FilmCategoryService;
import pl.ultrakino.service.FilmService;
import pl.ultrakino.service.RatingService;
import pl.ultrakino.service.UserService;

import java.security.Principal;

import static pl.ultrakino.Constants.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/films", produces = "application/json;charset=utf-8")
public class FilmController {

	private FilmService filmService;
	private UserService userService;
	private RatingService ratingService;
	private FilmCategoryService filmCategoryService;

	@Autowired
	public FilmController(FilmService filmService, UserService userService, RatingService ratingService, FilmCategoryService filmCategoryService) {
		this.filmService = filmService;
		this.userService = userService;
		this.ratingService = ratingService;
		this.filmCategoryService = filmCategoryService;
	}


	@PostMapping
	public ResponseEntity addFilm(@RequestBody ObjectNode film, Principal principal) {
		if (principal == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}

	@GetMapping("/{filmId}")
	public ResponseEntity getFilm(@PathVariable int filmId) {
		try {
			return ResponseEntity.ok(filmService.toDetailsResource(filmService.findById(filmId)));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{filmId}/recommendationDate")
	public ResponseEntity recommendFilm(@PathVariable int filmId) {
		try {
			filmService.recommend(filmId);
			return ResponseEntity.ok().build();
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{filmId}/recommendationDate")
	public ResponseEntity deleteRecommendation(@PathVariable int filmId) {
		try {
			filmService.deleteRecommendation(filmId);
			return ResponseEntity.ok().build();
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity getFilms(@RequestParam MultiValueMap<String, String> params) {
		try {
			Page<Film> films = filmService.find(params);
			Page<FilmResource> result = new Page<>(
					filmService.toResources(films.getContent()),
					films.getPageNumber(),
					films.getPageCount());
			return ResponseEntity.ok(result);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", e.getMessage()));
		}
	}

	@GetMapping("/categories")
	public ResponseEntity getCategories() {
		return ResponseEntity.ok(filmCategoryService.findAll());
	}

}
