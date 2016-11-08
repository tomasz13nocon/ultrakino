package pl.ultrakino.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Rating;
import pl.ultrakino.service.RatingService;

import java.security.Principal;

@RestController
@RequestMapping(Constants.API_PREFIX + "/ratings")
public class RatingController {

	private RatingService ratingService;

	@Autowired
	public RatingController(RatingService ratingService) {
		this.ratingService = ratingService;
	}

	@PostMapping
	public ResponseEntity rate(@RequestBody ObjectNode body, Principal principal) {
		if (principal == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		JsonNode rating = body.get("rating");
		JsonNode contentId = body.get("contentId");
		if (rating == null || !rating.isNumber() ||
				contentId == null || !contentId.isInt()) {
			return ResponseEntity.badRequest().build();
		}

		try {
			Rating r = ratingService.save(contentId.asInt(), principal.getName(), (float) rating.asDouble());
			return ResponseEntity.ok(ratingService.toResource(r));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		} catch (NoUserWithSuchUsernameException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", "This content has been already rated by this user."));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", "Rating has to be between 0 and 10."));
		}
	}

}
