package pl.ultrakino.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resource.FilmResource;
import pl.ultrakino.service.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import static pl.ultrakino.Constants.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/films", produces = "application/json;charset=utf-8")
public class FilmController {

	private FilmService filmService;
	private UserService userService;
	private RatingService ratingService;
	private FilmwebService filmwebService;
	private FilmCategoryService filmCategoryService;

	@Autowired
	public FilmController(FilmService filmService, UserService userService, RatingService ratingService, FilmwebService filmwebService, FilmCategoryService filmCategoryService) {
		this.filmService = filmService;
		this.userService = userService;
		this.ratingService = ratingService;
		this.filmwebService = filmwebService;
		this.filmCategoryService = filmCategoryService;
	}


	@PostMapping
	public ResponseEntity addFilm(@RequestBody ObjectNode filmJson, Principal principal) {
		if (principal == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		String filmwebId = filmJson.get("filmwebId").asText();
		Film film;
		try {
			film = filmwebService.getFullFilmInfo(filmwebId);
		} catch (FilmwebException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		String src = filmJson.get("src").asText();
		String hosting = filmJson.get("hosting").asText();
		Optional<User> user = userService.findByUsername(principal.getName());
		if (!user.isPresent())
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		film.getPlayers().add(new Player(src, hosting, user.get()));
		filmService.save(film);
		return ResponseEntity.ok().body(JsonNodeFactory.instance.objectNode().put("id", film.getId()));
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
