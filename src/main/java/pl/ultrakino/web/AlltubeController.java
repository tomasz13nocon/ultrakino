package pl.ultrakino.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.AlltubeException;
import pl.ultrakino.model.Film;
import pl.ultrakino.service.AlltubeService;
import pl.ultrakino.service.FilmService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Constants.API_PREFIX + "/alltube")
public class AlltubeController {

	private AlltubeService alltubeService;
	private FilmService filmService;

	@Autowired
	public AlltubeController(AlltubeService alltubeService, FilmService filmService) {
		this.alltubeService = alltubeService;
		this.filmService = filmService;
	}

	@PostMapping
	public ResponseEntity uploadFilms(@RequestBody ObjectNode body) {
		try {
			JsonNode node = body.get("page");
			if (node == null) throw new NullPointerException();
			int page = node.asInt();
			if (page == 0) throw new NumberFormatException();
			List<Film> films = alltubeService.getFilms(page);
			return ResponseEntity.ok(films.stream().map(filmService::save).map(f -> {
				ObjectNode o = JsonNodeFactory.instance.objectNode()
						.put("title", f.getTitle())
						.put("filmwebId", f.getFilmwebId());
				o.putArray("players").addAll(f.getPlayers().stream().map(p ->
								JsonNodeFactory.instance.objectNode()
								.put("hosting", p.getHosting())
								.put("src", p.getSrc())
								.put("languageVersion", p.getLanguageVersion().toString())
						).collect(Collectors.toList()));
				return o;
			}).collect(Collectors.toList()));
		} catch (NullPointerException | NumberFormatException e) {
			return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(JsonNodeFactory.instance.objectNode().put("error", "Request body must contain an integer 'page' attribute."));
		} catch (IOException | AlltubeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonNodeFactory.instance.objectNode().put("error", "Something went hella wrong."));
		}
	}

}
