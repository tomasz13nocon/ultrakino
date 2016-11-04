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
	public Optional<Player> findBySrc(String src) {
		List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.src=:src", Player.class)
				.setParameter("src", src)
				.getResultList();
		if (players.isEmpty())
			return Optional.empty();
		return Optional.of(players.get(0));
	}

	@Override
	public Optional<Player> findBySrcAndHosting(String src, String hosting) {
		List<Player> players = em.createQuery("SELECT p FROM Player p WHERE p.src=:src AND p.hosting=:hosting", Player.class)
				.setParameter("src", src)
				.setParameter("hosting", hosting)
				.getResultList();
		if (players.isEmpty())
			return Optional.empty();
		return Optional.of(players.get(0));
	}

}
