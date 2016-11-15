package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;

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
	private Set<PlayerResource> addedPlayers = new HashSet<>();
	private List<RatingResource> ratings = new ArrayList<>();
	private Set<ContentResource> watchedContent = new HashSet<>();
	private Set<ContentResource> watchlist = new HashSet<>();
	private Set<ContentResource> favorites = new HashSet<>();

}
