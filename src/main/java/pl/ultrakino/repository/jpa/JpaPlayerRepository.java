package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
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

	@Override
	public boolean remove(int id) {
		return em.createQuery("DELETE FROM Player p WHERE p.id=:id")
				.setParameter("id", id)
				.executeUpdate() == 1;
	}

	@Override
	public boolean exists(String hosting, String src) {
		System.out.println(em.createQuery("SELECT COUNT(*) FROM Player p WHERE p.hosting=:hosting AND p.src=:src", Number.class)
				.setParameter("hosting", hosting)
				.setParameter("src", src)
				.getResultList().get(0).intValue());
		return em.createQuery("SELECT COUNT(*) FROM Player p WHERE p.hosting=:hosting AND p.src=:src", Number.class)
				.setParameter("hosting", hosting)
				.setParameter("src", src)
				.getResultList().get(0).longValue() != 0L;
	}

	@Override
	public Player save(Player player) {
		em.persist(player);
		return player;
	}

	@Override
	public Player findById(int id) throws NoRecordWithSuchIdException {
		Player player = em.find(Player.class, id);
		if (player == null)
			throw new NoRecordWithSuchIdException();
		return player;
	}

}
