package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.PlayerVote;
import pl.ultrakino.repository.PlayerVoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaPlayerVoteRepositoryImpl implements PlayerVoteRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public PlayerVote save(PlayerVote vote) {
		em.persist(vote);
		return vote;
	}

}
