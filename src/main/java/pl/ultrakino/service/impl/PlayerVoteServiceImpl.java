package pl.ultrakino.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.model.PlayerVote;
import pl.ultrakino.resource.PlayerVoteResource;
import pl.ultrakino.service.PlayerVoteService;

@Service
@Transactional
public class PlayerVoteServiceImpl implements PlayerVoteService {

	@Override
	public PlayerVoteResource toResource(PlayerVote vote) {
		PlayerVoteResource res = new PlayerVoteResource();
		res.setUid(vote.getId());
		res.setPositive(vote.isPositive());
		return res;
	}

}
