package pl.ultrakino.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.Constants;
import pl.ultrakino.Utils;
import pl.ultrakino.exceptions.AlltubeException;
import pl.ultrakino.exceptions.ControllerInputException;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.exceptions.TvseriesonlineException;
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
			if (node == null) throw new ControllerInputException();
			int page = node.asInt();
			if (page == 0) throw new ControllerInputException();
			List<Film> films = alltubeService.fetchAndSaveFilms(page);
			return ResponseEntity.ok(films.stream().map(f -> {
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
		} catch (ControllerInputException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(Utils.jsonError("Request body must contain an integer 'page' attribute."));
		} catch (IOException | AlltubeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Utils.jsonError(e.getClass().getSimpleName() + ": " +e.getStackTrace()[0] + " - " + e.getMessage()));
		}
	}

	@PostMapping("/series")
	public synchronized ResponseEntity uploadSeries() {
		try {
			tvseriesonlineService.fetchAndSaveAllShows();
			return ResponseEntity.ok().build();
		} catch (FilmwebException | TvseriesonlineException | IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Utils.jsonError(e.getClass().getSimpleName() + ": " +e.getStackTrace()[0] + " - " + e.getMessage()));
		}
	}

}
