package pl.ultrakino.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.resources.assemblers.UserResourceAsm;
import pl.ultrakino.service.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(RestAPIDefinitions.API_PREFIX)
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user")
	public ResponseEntity getCurrentUser(Principal principal) {
		if (principal == null) return ResponseEntity.ok().build();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.valueToTree(principal);
		Optional<User> userOp = userService.findByUsername(principal.getName());
		if (!userOp.isPresent()) // I think this can never happen, not sure tho
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("hacking detected, pls no hack us");
		User user = userOp.get();
		node.put("avatarFilename", user.getAvatarFilename());
		return ResponseEntity.ok(node);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity getUser(@PathVariable int userId) {
		try {
			User user = userService.findById(userId);
			return ResponseEntity.ok().build();
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
