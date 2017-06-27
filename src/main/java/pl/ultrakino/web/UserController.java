package pl.ultrakino.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.Utils;
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
	public ResponseEntity getCurrentUser(Principal principal) {
		if (principal == null) return ResponseEntity.ok().build();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.valueToTree(principal);
		Optional<User> userOp = userService.findByUsername(principal.getName(), true);
		if (!userOp.isPresent()) // I think this can never happen
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("hacking detected, pls no hack us");
		User user = userOp.get();
		node.set("details", mapper.convertValue(userService.toDetailsResource(user), ObjectNode.class));
		node.put("avatarFilename", user.getAvatarFilename());
		node.put("uid", user.getId());
		return ResponseEntity.ok(node);
	}



	// Current user details
	@GetMapping("/userdetails")
	public ResponseEntity getUserDetails(Principal principal) {
		if (principal == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		Optional<User> user = userService.findByUsername(principal.getName(), true);
		if (!user.isPresent())
			throw new AssertionError();
		return ResponseEntity.ok(userService.toDetailsResource(user.get()));
	}

	// Other user details
	@GetMapping("/users/{userId}")
	public ResponseEntity getUser(@PathVariable int userId) {
		try {
			User user = userService.findById(userId);
			return ResponseEntity.ok(userService.toResource(user));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// Get users with given username
	// For registration, checking whether a user exists.
	@GetMapping("/users")
	public ResponseEntity findUser(@RequestParam(required = false) String name,
											 @RequestParam(required = false) Integer start,
											 @RequestParam(required = false) Integer maxResults,
											 Principal principal) {
		if (name != null) {
			Optional<User> user = userService.findByUsername(name);
			if (user.isPresent())
				return ResponseEntity.ok(userService.toResource(user.get()));
			return ResponseEntity.ok().build();
		}
		else if (start != null && maxResults != null) {
			Optional<User> user = userService.findByUsername(principal.getName());
			if (!user.isPresent())
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			if (!user.get().getRoles().contains("ROLE_ADMIN"))
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			return ResponseEntity.ok(
					userService.find(start, maxResults)
					.stream().map(userService::toDetailsResource).collect(Collectors.toList()));
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
