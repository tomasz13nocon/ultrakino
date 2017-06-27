package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public User save(User user) {
		em.persist(user);
		return user;
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public User getUserReference(Integer userId) {
		User user = em.getReference(User.class, userId);
		// Force EntityNotFoundException to be thrown
		user.getUsername();
		return user;
	}

	@Override
	public Optional<User> findByUsername(String username, boolean fetchCollections) {
		List<User> list = em
				.createQuery("SELECT u FROM User u " +
						(fetchCollections ?
								"LEFT JOIN FETCH u.addedPlayers " +
								"LEFT JOIN FETCH u.watchlist " +
								"LEFT JOIN FETCH u.favorites " +
								"LEFT JOIN FETCH u.watchedContent " +
								"LEFT JOIN FETCH u.ratings "
								: "") +
						"WHERE u.username=:username", User.class)
				.setParameter("username", username)
				.getResultList();
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return findByUsername(username, false);
	}

	@Override
	public User findById(int id) throws NoRecordWithSuchIdException {
		User user = em.find(User.class, id);
		if (user == null) throw new NoRecordWithSuchIdException();
		return user;
	}

	@Override
	public void merge(User user) {
		em.merge(user);
	}

	@Override
	public List<User> find(int start, int maxResults) {
		return em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.addedPlayers", User.class)
				.setFirstResult(start)
				.setMaxResults(maxResults)
				.getResultList();
	}

}
