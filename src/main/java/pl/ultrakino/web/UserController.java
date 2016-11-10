package pl.ultrakino.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.resources.UserResource;
import pl.ultrakino.resources.assemblers.UserDetailsResourceAsm;
import pl.ultrakino.resources.assemblers.UserResourceAsm;
import pl.ultrakino.service.UserService;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.API_PREFIX)
public class UserController {

	private UserService userService;
	private UserResourceAsm userResourceAsm;
	private UserDetailsResourceAsm userDetailsResourceAsm;

	@Autowired
	public UserController(UserService userService, UserResourceAsm userResourceAsm, UserDetailsResourceAsm userDetailsResourceAsm) {
		this.userService = userService;
		this.userResourceAsm = userResourceAsm;
		this.userDetailsResourceAsm = userDetailsResourceAsm;
	}

	@GetMapping("/user")
	public ResponseEntity getCurrentUser(Principal principal) {
		if (principal == null) return ResponseEntity.ok().build();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.valueToTree(principal);
		Optional<User> userOp = userService.findByUsername(principal.getName());
		if (!userOp.isPresent()) // I think this can never happen
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("hacking detected, pls no hack us");
		User user = userOp.get();
		node.put("avatarFilename", user.getAvatarFilename());
		node.put("uid", user.getId());
		return ResponseEntity.ok(node);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity getUser(@PathVariable int userId) {
		try {
			User user = userService.findById(userId);
			return ResponseEntity.ok(userDetailsResourceAsm.toResource(user));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/users")
	public ResponseEntity createUser(@RequestBody MultiValueMap<String, String> body) {
		try {
			Optional<UserResource> user = userService.create(
					body.get("username").get(0),
					body.get("password").get(0),
					body.get("email").get(0));
			return ResponseEntity.ok(user);
		} catch (NullPointerException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
