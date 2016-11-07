package pl.ultrakino.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Rating;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resources.FilmResource;
import pl.ultrakino.resources.assemblers.FilmResourceAsm;
import pl.ultrakino.service.*;

import java.security.Principal;

import static pl.ultrakino.Constants.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/films", produces = "application/json;charset=utf-8")
public class FilmController {

	private FilmService filmService;
	private UserService userService;
	private FilmResourceAsm filmResourceAsm;
	private RatingService ratingService;
	private FilmCategoryService filmCategoryService;

	@Autowired
	public FilmController(FilmService filmService, FilmResourceAsm filmResourceAsm, UserService userService, RatingService ratingService, FilmCategoryService filmCategoryService) {
		this.filmService = filmService;
		this.userService = userService;
		this.filmResourceAsm = filmResourceAsm;
		this.ratingService = ratingService;
		this.filmCategoryService = filmCategoryService;
	}

//	@JsonView(Views.FilmCreation.class)
	/*@PostMapping
	public ResponseEntity<FilmDetailsResource> createFilm(@RequestBody FilmDetailsResource filmDetailsResource) throws URISyntaxException {
		Film film = filmService.save(filmDetailsResource);
		// Get resource representation of actually created Film and its links
		filmDetailsResource = filmDetailsResourceAsm.toResource(film);
		return ResponseEntity.created(new URI(filmDetailsResource.getLink("self").getHref())).body(filmDetailsResource);
	}*/

	@GetMapping("/{filmId}")
	public ResponseEntity getFilm(@PathVariable int filmId) {
		try {
			return ResponseEntity.ok(filmService.findById(filmId));
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
					filmResourceAsm.toResources(films.getContent()),
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
