package pl.ultrakino.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ultrakino.Constants;

import java.security.Principal;

@RestController
@RequestMapping(Constants.API_PREFIX + "/")
public class PlayerController {

	@PostMapping
	public ResponseEntity vote(boolean positive, Principal principal) {
		return ResponseEntity.ok().build();
	}

}
