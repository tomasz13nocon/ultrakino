package pl.ultrakino.resources;

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

public class UserResource extends ResourceSupport {

	private Integer userId;
	private String username;
	private String passwd;
	private String email;
	private String avatarFilename;
	private List<PlayerResource> addedPlayers = new ArrayList<>();
	private List<RatingResource> ratings;
	private List<ContentResource> watchedContent;



	public User toDomainObject() {
		User user = new User();
		user.setUsername(username);
		user.setPasswd(passwd);
		user.setEmail(email);
		user.setAvatarFilename(avatarFilename);
		user.setAddedPlayers(addedPlayers.stream().map(PlayerResource::toDomainObject).collect(Collectors.toList()));
		user.setRatings(ratings.stream().map(RatingResource::toDomainObject).collect(Collectors.toList()));
		user.setWatchedContent(watchedContent.stream().map(ContentResource::toDomainObject).collect(Collectors.toList()));
		return user;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarFilename() {
		return avatarFilename;
	}

	public void setAvatarFilename(String avatarFilename) {
		this.avatarFilename = avatarFilename;
	}

	public List<PlayerResource> getAddedPlayers() {
		return addedPlayers;
	}

	public void setAddedPlayers(List<PlayerResource> addedPlayers) {
		this.addedPlayers = addedPlayers;
	}

	public List<RatingResource> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingResource> ratings) {
		this.ratings = ratings;
	}

	public List<ContentResource> getWatchedContent() {
		return watchedContent;
	}

	public void setWatchedContent(List<ContentResource> watchedContent) {
		this.watchedContent = watchedContent;
	}
}
