package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class PlayerVoteResource {

	private long uid;
	private boolean positive;


	public PlayerVoteResource() {
	}

	public PlayerVoteResource(long uid, boolean positive) {
		this.uid = uid;
		this.positive = positive;
	}

}
