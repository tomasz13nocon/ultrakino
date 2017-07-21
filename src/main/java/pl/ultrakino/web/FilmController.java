package pl.ultrakino.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Utils;
import pl.ultrakino.exceptions.FileDeletionException;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resource.FilmDetailsResource;
import pl.ultrakino.resource.FilmResource;
import pl.ultrakino.service.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;

import static pl.ultrakino.Constants.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/films")
public class FilmController {

	private FilmService filmService;
	private UserService userService;
	private RatingService ratingService;
	private FilmwebService filmwebService;
	private FilmCategoryService filmCategoryService;
	private UtilService utilService;
	private PlayerService playerService;

	@Autowired
	public FilmController(FilmService filmService, UserService userService, RatingService ratingService, FilmwebService filmwebService, FilmCategoryService filmCategoryService, UtilService utilService, PlayerService playerService) {
		this.filmService = filmService;
		this.userService = userService;
		this.ratingService = ratingService;
		this.filmwebService = filmwebService;
		this.filmCategoryService = filmCategoryService;
		this.utilService = utilService;
		this.playerService = playerService;
	}


	// TODO Test URI Syntax Ex

	@PostMapping
	public ResponseEntity addFilm(@RequestBody ObjectNode body, @AuthenticationPrincipal User user, HttpServletRequest request) throws URISyntaxException {
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		if (!body.has("filmwebId")) {
			return ResponseEntity.badRequest().body(Utils.jsonError("One of 'filmwebId' or 'id' is required."));
		}

		String filmwebId = body.get("filmwebId").asText();
		Optional<Film> op = filmService.findByFilmwebId(filmwebId);
		if (op.isPresent()) {
			return ResponseEntity.ok(JsonNodeFactory.instance.objectNode().put("id", op.get().getId()));
		}
		else {
			try {
				Film film = filmwebService.getFullFilmInfo(filmwebId);
				filmService.save(film);
				return ResponseEntity.created(new URI(request.getRequestURI() + "/" + film.getId()))
						.body(JsonNodeFactory.instance.objectNode().put("id", film.getId()));
			} catch (FilmwebException e) {
				System.err.println(filmwebId + ": " + e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.jsonError("Coś poszło nie tak. Nie można dodać filmu."));
			}
		}
	}

	@DeleteMapping("/{filmId}")
	public ResponseEntity deleteFilm(@PathVariable int filmId) {
		try {
			filmService.remove(filmId);
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		} catch (FileDeletionException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.jsonError(e.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/{filmId}")
	public ResponseEntity getFilm(@PathVariable int filmId, @AuthenticationPrincipal User user) {
		try {
			/*if (user != null) {
				return ResponseEntity.ok(filmService.findById(filmId, user.getId()));
			}*/
			return ResponseEntity.ok(filmService.toDetailsResource(filmService.findById(filmId)));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{filmId}/players")
	public ResponseEntity addPlayer(@PathVariable int filmId, @RequestBody ObjectNode body, @AuthenticationPrincipal User user, HttpServletRequest request) throws InterruptedException {
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Film film;
		try {
			film = filmService.findById(filmId);
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}

		String field;
		if (!body.has(field = "src") || !body.has(field = "hosting") || !body.has(field = "languageVersion"))
			return ResponseEntity.badRequest().body(Utils.jsonError("field '" + field + "' is incorrect or absent."));

		String src = body.get("src").asText();
		// TODO check that hosting is a hosting that we support ( Here and above in addFilm as well ) ( or mby rather do it in service layer )
		String hosting = body.get("hosting").asText();
		Player.LanguageVersion languageVersion;
		try {
			languageVersion = Player.LanguageVersion.valueOf(body.get("languageVersion").asText());
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Utils.jsonError("field 'languageVersion' is incorrect."));
		}

		Player player1 = new Player(src, hosting, languageVersion, user, film);
		String error = "";
		if (!Utils.isAdmin(user) /* TODO delete */ && playerService.exists(player1)) {
			error = "Link o tym adresie już istnieje.";
		}
		else {
			film.getPlayers().add(player1);
			playerService.save(player1);
		}
		return ResponseEntity.ok(JsonNodeFactory.instance.objectNode()
				.put("id", player1.getId())
				.put("hosting", player1.getHosting())
				.put("src", player1.getSrc())
				.put("error", error));

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{filmId}/recommendationDate")
	public ResponseEntity recommendFilm(@PathVariable int filmId) {
		try {
			filmService.recommend(filmId);
			return ResponseEntity.ok().build();
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
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
			e.printStackTrace(); // TODO: delet maybe
			return ResponseEntity.badRequest().body(Utils.jsonError(e.getMessage()));
		}
	}

	@GetMapping("/categories")
	public ResponseEntity getCategories() {
		return ResponseEntity.ok(filmCategoryService.findAll());
	}


	@ExceptionHandler(URISyntaxException.class)
	public ResponseEntity handleURISyntaxException() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.jsonError("URL parsing error"));
	}
}
