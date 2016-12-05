package pl.ultrakino.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.model.User;
import pl.ultrakino.service.ContentService;
import pl.ultrakino.service.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(Constants.API_PREFIX + "/users/{userId}")
public class PlaylistController {

	private UserService userService;
	private ContentService contentService;

	@Autowired
	public PlaylistController(UserService userService, ContentService contentService) {
		this.userService = userService;
		this.contentService = contentService;
	}

	@GetMapping("/watchlist")
	public ResponseEntity getWatchlist(@PathVariable int userId, Principal principal) {
		if (principal == null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		Optional<User> userOp = userService.findByUsername(principal.getName(), true);
		if (!userOp.isPresent())
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		User user = userOp.get();
		if (user.getId() != userId)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		return ResponseEntity.ok(contentService.toResources(user.getWatchlist()));
	}

	@PostMapping("/watchlist")
	public ResponseEntity addToWatchlist(@PathVariable int userId, Principal principal, @RequestBody ObjectNode body) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
	}

	@DeleteMapping("/watchlist")
	public ResponseEntity deleteFromWatchlist(@PathVariable int userId, Principal principal) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
	}

}
