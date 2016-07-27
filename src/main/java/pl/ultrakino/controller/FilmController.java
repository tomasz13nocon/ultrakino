package pl.ultrakino.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmResource;
import pl.ultrakino.resources.assemblers.FilmResourceAssembler;
import pl.ultrakino.service.FilmService;

import static pl.ultrakino.controller.RestAPIDefinitions.API_PREFIX;

@RestController(API_PREFIX + "/films")
@CrossOrigin
public class FilmController {

	private FilmService filmService;
	private FilmResourceAssembler filmResourceAssembler;

	@Autowired
	public FilmController(FilmService filmService, FilmResourceAssembler filmResourceAssembler) {
		this.filmService = filmService;
		this.filmResourceAssembler = filmResourceAssembler;
	}

	@PostMapping
	public FilmResource createFilm(@RequestBody FilmResource film) {
		filmService.create(film.toFilm());
		return film;
	}

	@GetMapping("/{filmId}")
	public FilmResource getFilm(@PathVariable int filmId) throws NoRecordWithSuchIdException {
		Film film = filmService.find(filmId);
		return filmResourceAssembler.toResource(film);
	}

	@ExceptionHandler(NoRecordWithSuchIdException.class)
	public ResponseEntity<String> contentNotFound() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
