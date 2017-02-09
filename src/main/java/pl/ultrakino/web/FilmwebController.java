package pl.ultrakino.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Film;
import pl.ultrakino.service.ContentType;
import pl.ultrakino.service.FilmwebService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Constants.API_PREFIX + "/filmweb")
public class FilmwebController {

	private FilmwebService filmwebService;

	@Autowired
	public FilmwebController(FilmwebService filmwebService) {
		this.filmwebService = filmwebService;
	}

	@GetMapping
	public ResponseEntity searchForFilms(@RequestParam String title, @RequestParam String contentType, @RequestParam(required = false) Integer resultLimit) {
		try {
			List<String> ids = filmwebService.search(ContentType.valueOf(contentType), title, null);
			List<Content> films = new ArrayList<>();
			for (int i = 0; i < Math.min(ids.size(), resultLimit != null ? resultLimit : 6); i++) {
				films.add(contentType.equals(ContentType.FILM.name()) ?
						filmwebService.getFilmInfo(ids.get(i), false) :
						filmwebService.getSeriesInfo(ids.get(i), false));
			}
			return ResponseEntity.ok(films);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Invalid contentType value");
		} catch (FilmwebException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
