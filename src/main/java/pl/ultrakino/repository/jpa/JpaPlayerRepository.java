package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.model.Player;
import pl.ultrakino.repository.PlayerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaPlayerRepository implements PlayerRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Player> findBySrcWithFullLink(String src) {
		List<Player> players = em.createQuery("FROM Player WHERE fullLink=true AND src=:src", Player.class)
				.setParameter("src", src)
				.getResultList();
		if (players.isEmpty())
			return Optional.empty();
		return Optional.of(players.get(0));
	}

}
