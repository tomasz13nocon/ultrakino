package pl.ultrakino.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

	@Id
	@SequenceGenerator(name = "user_id_gen", sequenceName = "users_user_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
	@Column(name = "user_id")
	private Integer id;

	@Size(min = 3, max = 255)
	private String username;

	@Size(min = 8)
	private String passwd;

	@Email
	@NotEmpty
	private String email;

	@Column(name = "avatar_filename")
	private String avatarFilename;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "addedBy")
	private List<Player> addedPlayers = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ratedBy")
	private List<Rating> ratings;

	@ManyToMany
	@JoinTable(name = "users_contents_watched",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "content_id"))
	private List<Content> watchedContent;

	@ElementCollection
	private Set<String> roles;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Player> getAddedPlayers() {
		return addedPlayers;
	}

	public void setAddedPlayers(List<Player> addedPlayers) {
		this.addedPlayers = addedPlayers;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<Content> getWatchedContent() {
		return watchedContent;
	}

	public void setWatchedContent(List<Content> watchedContent) {
		this.watchedContent = watchedContent;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
