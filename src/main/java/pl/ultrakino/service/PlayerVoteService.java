package pl.ultrakino.service;

import pl.ultrakino.model.PlayerVote;
import pl.ultrakino.resource.PlayerVoteResource;

public interface PlayerVoteService {
	PlayerVoteResource toResource(PlayerVote vote);
}
