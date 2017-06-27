package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Size(min = 3, max = 255)
	private String username;

	private String passwd;

	@Email
	@NotEmpty
	private String email;

	@Column(name = "avatar_filename")
	private String avatarFilename;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "addedBy")
	private Set<Player> addedPlayers = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ratedBy")
	private List<Rating> ratings = new ArrayList<>();

	/* Following three mappings' names are hardcoded in JpaPlaylistRepository::removeByFilm() in native queries,
	 * as opposed to making these bidirectional. */
	@ManyToMany
	@JoinTable(name = "users_contents_watched",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "content_id"))
	private Set<Content> watchedContent = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "users_contents_watchlist",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "content_id"))
	private Set<Content> watchlist = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "users_contents_favorites",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "content_id"))
	private Set<Content> favorites = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "role")
	private Set<String> roles = new HashSet<>();

	@Column(name = "registration_date")
	private LocalDateTime registrationDate;

	@PrePersist
	public void prePersist() {
		registrationDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return username;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return Objects.equals(getUsername(), user.getUsername());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername());
	}
}
