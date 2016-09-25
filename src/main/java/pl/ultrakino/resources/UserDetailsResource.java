package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.Rating;
import pl.ultrakino.model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsResource extends ResourceSupport {

	private Integer uid;
	private String username;
	private String email;
	private String avatarFilename;
	private List<PlayerResource> addedPlayers = new ArrayList<>();
	private List<RatingResource> ratings;
	private List<ContentResource> watchedContent;

}
