package pl.ultrakino.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.Utils;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.PlayerVote;
import pl.ultrakino.model.User;
import pl.ultrakino.service.PlayerService;
import pl.ultrakino.service.PlayerVoteService;

import java.util.Optional;

@RestController
@RequestMapping(Constants.API_PREFIX + "/players")
public class PlayerController {

	private PlayerService playerService;
	private PlayerVoteService playerVoteService;

	@Autowired
	public PlayerController(PlayerService playerService, PlayerVoteService playerVoteService) {
		this.playerService = playerService;
		this.playerVoteService = playerVoteService;
	}

	@PostMapping("/{id}/votes")
	public ResponseEntity vote(@PathVariable int id, @RequestBody ObjectNode body, @AuthenticationPrincipal User user) {
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		JsonNode positiveNode = body.get("positive");
		if (positiveNode == null || !positiveNode.isBoolean()) {
			return ResponseEntity.badRequest().body(Utils.jsonError("field 'positive' is incorrect or absent."));
		}
		System.err.println(positiveNode.asBoolean());
		try {
			Optional<PlayerVote> voteOp = playerService.vote(id, positiveNode.asBoolean(), user);
			if (!voteOp.isPresent())
				return ResponseEntity.badRequest().body(Utils.jsonError("You have already voted for this player."));
			return ResponseEntity.ok().body(playerVoteService.toResource(voteOp.get()));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity removePlayer(@PathVariable int id, @AuthenticationPrincipal User user) {
		if (playerService.remove(id))
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/hostings")
	public ResponseEntity getSupportedHostings() {
		return ResponseEntity.ok(Constants.SUPPORTED_HOSTINGS);
	}

}
