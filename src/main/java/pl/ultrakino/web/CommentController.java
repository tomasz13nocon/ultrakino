package pl.ultrakino.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.Comment;
import pl.ultrakino.service.CommentService;

import java.security.Principal;

@RestController
@RequestMapping(Constants.API_PREFIX + "/comments")
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	public ResponseEntity postComment(@RequestBody ObjectNode body, Principal principal) {
		if (principal == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		JsonNode comment = body.get("comment");
		JsonNode contentId = body.get("contentId");
		if (comment == null ||
				!comment.isTextual() ||
				contentId == null ||
				!contentId.isInt())
			return ResponseEntity.badRequest().build();
		try {
			return ResponseEntity.ok(commentService.toResource(commentService.save(
					comment.asText(),
					contentId.asInt(),
					principal.getName())));
		} catch (NoRecordWithSuchIdException e) {
			return ResponseEntity.notFound().build();
		} catch (NoUserWithSuchUsernameException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", "contentId is not a valid integer"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(JsonNodeFactory.instance.objectNode().put("error", "Comment must be less than 255 characters and more than 2"));
		}
	}

}
