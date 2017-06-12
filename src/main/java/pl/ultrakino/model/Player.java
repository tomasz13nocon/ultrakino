package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {

	public enum LanguageVersion {
		VOICE_OVER,
		ORIGINAL,
		DUBBING,
		POLISH_FILM,
		POLISH_SUBS,
		ENGLISH_SUBS,
	}

	public Player() {}

	public Player(String src, String hosting, LanguageVersion languageVersion, User addedBy) {
		this.src = src;
		this.hosting = hosting;
		this.languageVersion = languageVersion;
		this.addedBy = addedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_id")
	private Integer id;

	@Column(name = "language_version")
	@Enumerated(EnumType.STRING)
	private LanguageVersion languageVersion;

	private String src;

	private String hosting;

	private String quality;

	private int upvotes;

	private int downvotes;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<PlayerVote> votes;

	@Column(name = "addition_date")
	private LocalDateTime additionDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User addedBy;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;

	@Column(name = "foreign_src")
	private boolean foreignSrc;

	@Column(name = "lost_src")
	private boolean lostSrc;


	@PrePersist
	public void prePersist() {
		additionDate = LocalDateTime.now();
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Player)) return false;
		Player player = (Player) o;
		return Objects.equals(getSrc(), player.getSrc()) &&
				Objects.equals(getHosting(), player.getHosting());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSrc(), getHosting());
	}
}
