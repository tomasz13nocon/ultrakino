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
import pl.ultrakino.resources.assemblers.FilmDetailsResourceAsm;
import pl.ultrakino.resources.assemblers.FilmResourceAsm;
import pl.ultrakino.service.CommentService;
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
	private FilmResourceAsm filmResourceAsm;
	private RatingService ratingService;

	@Autowired
	public FilmController(FilmService filmService, FilmResourceAsm filmResourceAsm, UserService userService, RatingService ratingService) {
		this.filmService = filmService;
		this.userService = userService;
		this.filmResourceAsm = filmResourceAsm;
		this.ratingService = ratingService;
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

	@PostMapping("/{filmId}/ratings")
	public ResponseEntity rate(@PathVariable int filmId, @RequestBody Rating rating, Principal principal) {
		if (principal == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		try {
			return ResponseEntity.ok(ratingService.toResource(filmService.rate(filmId, principal.getName(), rating.getRating())));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		} catch (NoUserWithSuchUsernameException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", "This content has been already rated by this user."));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", "Rating has to be between 0 and 10."));
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
		return ResponseEntity.ok().build();
	}

}
