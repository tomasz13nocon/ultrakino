package pl.ultrakino.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.model.User;
import pl.ultrakino.service.PlayerService;

import java.security.Principal;

@RestController
@RequestMapping(Constants.API_PREFIX + "/players")
public class PlayerController {

	private PlayerService playerService;

	@Autowired
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@PostMapping
	public ResponseEntity vote(boolean positive, Principal principal) {
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity removePlayer(@PathVariable int id, @AuthenticationPrincipal User user) {
		if (playerService.remove(id))
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.notFound().build();
	}

}
