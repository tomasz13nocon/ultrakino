package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Player;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PlayerResource {

	private int uid;
	private Player.LanguageVersion languageVersion;
	private String src;
	private LocalDateTime additionDate;
	private UserResource addedBy;
	private ContentResource content;
	private String hosting;

	private List<PlayerVoteResource> votes;
	private Integer upvotes;
	private Integer downvotes;
	private Boolean userVote;

}
