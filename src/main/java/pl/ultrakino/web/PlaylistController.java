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
import pl.ultrakino.model.Content;
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
	public ResponseEntity getWatchlist(@PathVariable int userId, @AuthenticationPrincipal User user) {
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if (user.getId() != userId)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		return ResponseEntity.ok(contentService.toResources(userService.getWatchlist(userId)));
	}

	@PostMapping("/watchlist")
	public ResponseEntity addToWatchlist(@PathVariable int userId, @RequestBody ObjectNode body, @AuthenticationPrincipal User user) {
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if (user.getId() != userId)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		JsonNode contentId = body.get("contentId");
		if (contentId == null || !contentId.isInt())
			return ResponseEntity.badRequest().body(Utils.jsonError("Field 'contentId' is incorrect or absent."));
		userService.addToWatchlist(userId, contentId.asInt());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/watchlist/{contentId}")
	public ResponseEntity deleteFromWatchlist(@PathVariable int userId, @PathVariable int contentId, @AuthenticationPrincipal User user) {
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if (user.getId() != userId)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		userService.removeFromWatchlist(userId, contentId);
		return ResponseEntity.ok().build();
	}


	@GetMapping("/favorites")
	public ResponseEntity getFavorites(@PathVariable int userId, @AuthenticationPrincipal User user) {
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if (user.getId() != userId)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		return ResponseEntity.ok(contentService.toResources(userService.getFavorites(userId)));
	}

	@PostMapping("/favorites")
	public ResponseEntity addToFavorites(@PathVariable int userId, @RequestBody ObjectNode body, @AuthenticationPrincipal User user) {
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if (user.getId() != userId)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		JsonNode contentId = body.get("contentId");
		if (contentId == null || !contentId.isInt())
			return ResponseEntity.badRequest().body(Utils.jsonError("Field 'contentId' is incorrect or absent."));
		userService.addToFavorites(userId, contentId.asInt());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/favorites/{contentId}")
	public ResponseEntity deleteFromFavorites(@PathVariable int userId, @PathVariable int contentId, @AuthenticationPrincipal User user) {
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		if (user.getId() != userId)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		userService.removeFromFavorites(userId, contentId);
		return ResponseEntity.ok().build();
	}

}
