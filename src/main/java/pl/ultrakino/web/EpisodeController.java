package pl.ultrakino.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.Constants;
import pl.ultrakino.resources.EpisodeDetailsResource;

@RestController
@RequestMapping(Constants.API_PREFIX + "/episodes")
public class EpisodeController {

	@GetMapping
	public ResponseEntity getEpisode(@PathVariable int id) {
		return null;
	}

}
