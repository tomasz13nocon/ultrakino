package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "player_votes")
@Getter
@Setter
public class PlayerVote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_vote_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private boolean positive;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayerVote)) return false;
		PlayerVote that = (PlayerVote) o;
		return Objects.equals(getPlayer(), that.getPlayer()) &&
				Objects.equals(getUser(), that.getUser());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPlayer(), getUser());
	}
}
