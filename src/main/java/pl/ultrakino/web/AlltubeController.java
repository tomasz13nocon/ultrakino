package pl.ultrakino.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.exceptions.AlltubeException;
import pl.ultrakino.service.AlltubeService;
import pl.ultrakino.service.FilmService;

import java.io.IOException;

@RestController
@RequestMapping(RestAPIDefinitions.API_PREFIX + "/alltube")
public class AlltubeController {

	private AlltubeService alltubeService;
	private FilmService filmService;

	@Autowired
	public AlltubeController(AlltubeService alltubeService, FilmService filmService) {
		this.alltubeService = alltubeService;
		this.filmService = filmService;
	}

	@PostMapping
	public ResponseEntity uploadFilms() {
		try {
			return ResponseEntity.ok(alltubeService.getFilms(1));
		} catch (IOException | AlltubeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonNodeFactory.instance.objectNode().put("error", e.getMessage()));
		}
	}

}
