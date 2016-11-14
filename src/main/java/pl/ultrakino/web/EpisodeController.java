package pl.ultrakino.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.service.EpisodeService;

@RestController
@RequestMapping(Constants.API_PREFIX + "/series/{seriesId}/episodes")
public class EpisodeController {

	private EpisodeService episodeService;

	@Autowired
	public EpisodeController(EpisodeService episodeService) {
		this.episodeService = episodeService;
	}

	@GetMapping("/{id}")
	public ResponseEntity getEpisode(@PathVariable int seriesId, @PathVariable int id) {
		try {
			return ResponseEntity.ok(episodeService.toDetailsResource(episodeService.findByIdAndSeriesId(id, seriesId)));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity getEpisodes(@PathVariable int seriesId, @RequestParam int season) {
		return ResponseEntity.ok(episodeService.toResources(episodeService.findBySeriesIdAndSeason(seriesId, season)));
	}

}
