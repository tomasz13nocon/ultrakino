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
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.exceptions.TvseriesonlineException;
import pl.ultrakino.exceptions.WebScraperException;
import pl.ultrakino.model.Film;
import pl.ultrakino.service.AlltubeService;
import pl.ultrakino.service.FilmService;
import pl.ultrakino.service.TvseriesonlineService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.API_PREFIX + "/bots")
public class BotController {

	private AlltubeService alltubeService;
	private FilmService filmService;
	private TvseriesonlineService tvseriesonlineService;

	@Autowired
	public BotController(AlltubeService alltubeService, TvseriesonlineService tvseriesonlineService, FilmService filmService) {
		this.alltubeService = alltubeService;
		this.filmService = filmService;
		this.tvseriesonlineService = tvseriesonlineService;
	}

	@PostMapping("/films")
	public synchronized ResponseEntity uploadFilms(@RequestBody ObjectNode body) {
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

	@PostMapping("/series")
	public synchronized ResponseEntity uploadSeries() {
		try {
			tvseriesonlineService.getAllShows();
			return ResponseEntity.ok().build();
		} catch (FilmwebException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonNodeFactory.instance.objectNode().put("Filmweb error", e.getMessage()));
		} catch (TvseriesonlineException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonNodeFactory.instance.objectNode().put("Tvseriesonline error", e.getMessage()));
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonNodeFactory.instance.objectNode().put("IO error", e.getMessage()));
		}
	}

}
