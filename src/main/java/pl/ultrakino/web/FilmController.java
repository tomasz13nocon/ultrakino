package pl.ultrakino.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmDetailsResource;
import pl.ultrakino.resources.FilmResource;
import pl.ultrakino.resources.assemblers.FilmDetailsResourceAsm;
import pl.ultrakino.resources.assemblers.FilmResourceAsm;
import pl.ultrakino.service.FilmService;

import java.util.List;

import static pl.ultrakino.web.RestAPIDefinitions.API_PREFIX;

@RestController
@RequestMapping(value = API_PREFIX + "/films", produces = "application/json;charset=utf-8")
public class FilmController {

	private FilmService filmService;
	private FilmResourceAsm filmResourceAsm;
	private FilmDetailsResourceAsm filmDetailsResourceAsm;

	@Autowired
	public FilmController(FilmService filmService, FilmResourceAsm filmResourceAsm, FilmDetailsResourceAsm filmDetailsResourceAsm) {
		this.filmService = filmService;
		this.filmResourceAsm = filmResourceAsm;
		this.filmDetailsResourceAsm = filmDetailsResourceAsm;
	}

	@PostMapping
	public FilmResource createFilm(@RequestBody FilmResource film) {
		filmService.create(film.toFilm());
		return film;
	}

	@GetMapping("/{filmId}")
	public FilmDetailsResource getFilm(@PathVariable int filmId) throws NoRecordWithSuchIdException {
		Film film = filmService.findById(filmId);
		System.out.println("Hello");
		return filmDetailsResourceAsm.toResource(film);
	}

	@GetMapping("/recommended")
	public List<FilmResource> getRecommendedFilms() {
		List<Film> recommendedFilms = filmService.findRecommended();
		return filmResourceAsm.toResources(recommendedFilms);
	}

	@ExceptionHandler(NoRecordWithSuchIdException.class)
	public ResponseEntity<String> contentNotFound() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
