package pl.ultrakino.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.Utils;
import pl.ultrakino.exceptions.FileDeletionException;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.service.UserService;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.API_PREFIX)
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// Authenticate
	@GetMapping("/user")
	public ResponseEntity getCurrentUser(@AuthenticationPrincipal User user) {
		if (user == null) return ResponseEntity.ok().build();
		ObjectMapper mapper = new ObjectMapper();
		return ResponseEntity.ok(
				((ObjectNode) mapper.valueToTree(userService.toResource(user)))
						.set("roles", mapper.valueToTree(user.getRoles())));


		/*if (principal == null) return ResponseEntity.ok().build();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.valueToTree(principal);
		Optional<User> userOp = userService.findByUsername(principal.getName(), false);
		if (!userOp.isPresent())
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		User user = userOp.get();
		node.set("details", mapper.convertValue(userService.toDetailsResource(user, false), ObjectNode.class));
		node.put("avatarFilename", user.getAvatarFilename());
		node.put("uid", user.getId());
		return ResponseEntity.ok(node);*/
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity getUser(@PathVariable int userId, @AuthenticationPrincipal User user) {
		try {
			if (user != null && user.getId() == userId) {
				User u = userService.findById/*WithCollections*/(userId);
				return ResponseEntity.ok(userService.toResource(u)); // TODO make it toDetailsResource and uncomment line above
			}
			return ResponseEntity.ok(userService.toResource(userService.findById(userId)));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity deleteUser(@PathVariable int userId) {
		try {
			userService.remove(userId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		} catch (FileDeletionException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.jsonError(e.getMessage()));
		}
	}

	// Get users with given username
	// Useful for registration, checking whether a user exists.
	// Also get user list (only for admins)
	@GetMapping("/users")
	public ResponseEntity findUser(@RequestParam(required = false) String name,
											 @RequestParam(required = false) Integer start,
											 @RequestParam(required = false) Integer maxResults) {
		if (name != null) {
			Optional<User> user = userService.findByUsername(name);
			if (user.isPresent())
				return ResponseEntity.ok(userService.toResource(user.get()));
			return ResponseEntity.ok().build();
		}
		else if (start != null && maxResults != null) {
			return ResponseEntity.ok(
					userService.find(start, maxResults)
					.stream().map(u -> userService.toDetailsResource(u)).collect(Collectors.toList()));
		}
		else
			return ResponseEntity.badRequest().body(Utils.jsonError("Required parameters are absent."));
	}

	// Create user
	@PostMapping("/users")
	public ResponseEntity createUser(@RequestBody ObjectNode body) {
		try {
			// TODO CHECK FOR EXISTANCE AND TYPE OF ARGUMENTS
			return ResponseEntity.ok(userService.create(
					body.get("username").asText(),
					body.get("password").asText(),
					body.get("email").asText()));
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
