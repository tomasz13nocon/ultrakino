package pl.ultrakino.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Comment;
import pl.ultrakino.model.Film;
import pl.ultrakino.repository.Page;
import pl.ultrakino.resources.CommentResource;
import pl.ultrakino.resources.FilmDetailsResource;
import pl.ultrakino.resources.FilmResource;
import pl.ultrakino.resources.assemblers.CommentResourceAsm;
import pl.ultrakino.resources.assemblers.FilmDetailsResourceAsm;
import pl.ultrakino.resources.assemblers.FilmResourceAsm;
import pl.ultrakino.service.CommentService;
import pl.ultrakino.service.FilmService;
import pl.ultrakino.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import static pl.ultrakino.web.RestAPIDefinitions.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/films", produces = "application/json;charset=utf-8")
public class FilmController {

	private FilmService filmService;
	private CommentService commentService;
	private UserService userService;
	private FilmResourceAsm filmResourceAsm;
	private FilmDetailsResourceAsm filmDetailsResourceAsm;
	private CommentResourceAsm commentResourceAsm;

	@Autowired
	public FilmController(FilmService filmService, FilmResourceAsm filmResourceAsm, FilmDetailsResourceAsm filmDetailsResourceAsm, CommentService commentService, UserService userService, CommentResourceAsm commentResourceAsm) {
		this.filmService = filmService;
		this.commentService = commentService;
		this.userService = userService;
		this.filmResourceAsm = filmResourceAsm;
		this.filmDetailsResourceAsm = filmDetailsResourceAsm;
		this.commentResourceAsm = commentResourceAsm;
	}

//	@JsonView(Views.FilmCreation.class)
	@PostMapping
	public ResponseEntity<FilmDetailsResource> createFilm(@RequestBody FilmDetailsResource filmDetailsResource) throws URISyntaxException {
		Film film = filmService.create(filmDetailsResource);
		// Get resource representation of actually created Film and its links
		filmDetailsResource = filmDetailsResourceAsm.toResource(film);
		return ResponseEntity.created(new URI(filmDetailsResource.getLink("self").getHref())).body(filmDetailsResource);
	}

	@GetMapping("/{filmId}")
	public ResponseEntity getFilm(@PathVariable int filmId) {
		try {
			return ResponseEntity.ok(filmDetailsResourceAsm.toResource(filmService.findById(filmId)));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{filmId}/recommendationDate")
	public ResponseEntity recommendFilm(@PathVariable int filmId) throws NoRecordWithSuchIdException {
		filmService.recommendFilm(filmId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{contentId}/comments")
	public ResponseEntity postComment(@PathVariable int contentId, @RequestBody Comment comment, Principal principal) {
		if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		try {
			return ResponseEntity.ok(commentResourceAsm.toResource(commentService.save(comment, contentId, principal.getName())));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		} catch (NoUserWithSuchUsernameException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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

	@GetMapping("/qwe")
	public void qwe() {
		filmService.qwe();
	}


	// TODO: These have to be more specific, i.e. IllegalArgumentException may be thrown in different places and mean different things.
//	@ExceptionHandler({ URISyntaxException.class })
//	public ResponseEntity uriSyntaxException() {
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	}

//	@ExceptionHandler(IllegalArgumentException.class)
//	public ResponseEntity badRequest() {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//	}
}
