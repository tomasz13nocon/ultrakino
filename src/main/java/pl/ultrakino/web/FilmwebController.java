package pl.ultrakino.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.Constants;
import pl.ultrakino.Utils;
import pl.ultrakino.exceptions.FilmwebException;
import pl.ultrakino.model.Content;
import pl.ultrakino.resource.ContentResource;
import pl.ultrakino.service.ContentType;
import pl.ultrakino.service.FilmService;
import pl.ultrakino.service.FilmwebService;
import pl.ultrakino.service.SeriesService;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(Constants.API_PREFIX + "/filmweb")
public class FilmwebController {

	private FilmwebService filmwebService;
	private FilmService filmService;
	private SeriesService seriesService;

	@Autowired
	public FilmwebController(FilmwebService filmwebService, FilmService filmService, SeriesService seriesService) {
		this.filmwebService = filmwebService;
		this.filmService = filmService;
		this.seriesService = seriesService;
	}

	@GetMapping
	public ResponseEntity searchForFilms(@RequestParam(required = false) String title,
										 @RequestParam(required = false) String contentType,
										 @RequestParam(required = false) String link,
										 @RequestParam(required = false) Integer resultLimit) {
		if (contentType == null || (link == null && title == null)) {
			return ResponseEntity.badRequest().body(Utils.jsonError("parameters 'contentType' and one of 'link' or 'title' are required."));
		}
		if (link != null) {
			if (!Pattern.matches("^(http[s]?://)?(www\\.)?filmweb\\.pl/.*$", link))
				return ResponseEntity.badRequest().body(Utils.jsonError("Format linku niepoprawny."));
			try {
				String id = filmwebService.getFilmwebId(link);
				return ResponseEntity.ok(((ObjectNode) new ObjectMapper().valueToTree(
						contentType.equals(ContentType.FILM.name()) ?
								filmService.toResource(filmwebService.getFilmInfo(id, false)) :
								seriesService.toResource(filmwebService.getSeriesInfo(id, false))))
						.put("filmwebId", id));
			} catch (FilmwebException e) {
				System.err.println(link + " - " + e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.jsonError("Przetwarzanie filmweb.pl nie powiodło się."));
			} catch (MalformedURLException e) {
				return ResponseEntity.badRequest().body(Utils.jsonError("Format linky niepoprawny."));
			}
		}
		else {
			try {
				List<String> ids = filmwebService.search(ContentType.valueOf(contentType), title, null);
				List<ObjectNode> films = new ArrayList<>();
				for (int i = 0; i < Math.min(ids.size(), resultLimit != null ? resultLimit : 6); i++) {
					String id = ids.get(i);
					films.add(((ObjectNode) new ObjectMapper().valueToTree(
							contentType.equals(ContentType.FILM.name()) ?
									filmService.toResource(filmwebService.getFilmInfo(id, false)) :
									seriesService.toResource(filmwebService.getSeriesInfo(id, false))))
							.put("filmwebId", id));
				}
				return ResponseEntity.ok(films);
			} catch (IllegalArgumentException e) {
				return ResponseEntity.badRequest().body(Utils.jsonError("Invalid contentType value"));
			} catch (FilmwebException e) {
				System.err.println(title + " - " + e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.jsonError("Przetwarzanie filmweb.pl nie powiodło się."));
			}
		}
	}
}
