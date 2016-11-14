package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Content;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDetailsResource {

	private Integer uid;
	private String username;
	private String email;
	private String avatarFilename;
	private List<PlayerResource> addedPlayers = new ArrayList<>();
	private List<RatingResource> ratings = new ArrayList<>();
	private List<ContentResource> watchedContent = new ArrayList<>();
	private Set<ContentResource> watchlist = new HashSet<>();
	private Set<ContentResource> favorites = new HashSet<>();

}
