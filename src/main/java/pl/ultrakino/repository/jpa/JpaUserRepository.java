package pl.ultrakino.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(User user) {
		em.persist(user);
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
}
