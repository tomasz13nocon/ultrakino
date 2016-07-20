package pl.ultrakino.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

	@Size(min = 6)
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

}
