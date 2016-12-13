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
import pl.ultrakino.service.ContentType;
import pl.ultrakino.service.FilmwebService;

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
	public ResponseEntity searchForFilms(@RequestParam String title, @RequestParam String contentType) {
		return ResponseEntity.ok("Helloqwe");
		/*try {
			List<String> ids = filmwebService.search(ContentType.valueOf(contentType), title, null);

			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Invalid contentType value");
		} catch (FilmwebException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}*/
	}
}
