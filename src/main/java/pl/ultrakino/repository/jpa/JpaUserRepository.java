package pl.ultrakino.repository.jpa;

import org.springframework.stereotype.Repository;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Film;
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
//								"LEFT JOIN FETCH u.addedPlayers " +
								"LEFT JOIN FETCH u.watchlist " +
								"LEFT JOIN FETCH u.favorites "
//								"LEFT JOIN FETCH u.watchedContent " +
//								"LEFT JOIN FETCH u.ratings "
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
	public User findByIdWithCollections(int id) throws NoRecordWithSuchIdException {
		User user = em.createQuery("SELECT u FROM User u " +
				"LEFT JOIN FETCH u.watchlist w " +
				"LEFT JOIN FETCH w.filmCategories " +
				"LEFT JOIN FETCH w.productionCountries " +
				"LEFT JOIN FETCH u.favorites f " +
				"LEFT JOIN FETCH f.filmCategories " +
				"LEFT JOIN FETCH f.productionCountries " +
				"LEFT JOIN FETCH u.addedPlayers p " +
				"LEFT JOIN FETCH p.content c " +
				"LEFT JOIN FETCH c.filmCategories " +
				"LEFT JOIN FETCH c.productionCountries " +
				"WHERE u.id=:userId", User.class)
				.setParameter("userId", id)
				.getSingleResult();
		if (user == null) throw new NoRecordWithSuchIdException();
		return user;
	}

	@Override
	public void merge(User user) {
		em.merge(user);
	}

	@Override
	public List<User> find(int start, int maxResults) {
		return em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.addedPlayers p LEFT JOIN FETCH p.content", User.class)
				.setFirstResult(start)
				.setMaxResults(maxResults)
				.getResultList();
	}

	@Override
	public void remove(User user) {
		em.remove(user);
	}

	@Override
	public List<Content> getWatchlist(int userId) {
		return em.createQuery("SELECT c From User u " +
				"JOIN u.watchlist c " +
				"LEFT JOIN FETCH c.filmCategories " +
				"LEFT JOIN FETCH c.seriesCategories " +
				"LEFT JOIN FETCH c.productionCountries " + // TODO here This fetches only series' countries. Those of films are lazily loaded
				"WHERE u.id=:userId", Content.class)
				.setParameter("userId", userId)
				.getResultList();
	}

	@Override
	public List<Content> getFavorites(int userId) {
		return em.createQuery("SELECT c From User u " +
				"JOIN u.favorites c " +
				"LEFT JOIN FETCH c.filmCategories " +
				"LEFT JOIN FETCH c.productionCountries " +
				"WHERE u.id=:userId", Content.class)
				.setParameter("userId", userId)
				.getResultList();
	}

	@Override
	public void removeFromWatchlist(int userId, int contentId) {
		em.createNativeQuery("DELETE FROM users_contents_watchlist WHERE " +
				"user_id=:userId AND " +
				"content_id=:contentId")
				.setParameter("userId", userId)
				.setParameter("contentId", contentId)
				.executeUpdate();
	}

	@Override
	public void removeFromFavorites(int userId, int contentId) {
		em.createNativeQuery("DELETE FROM users_contents_favorites WHERE " +
				"user_id=:userId AND " +
				"content_id=:contentId")
				.setParameter("userId", userId)
				.setParameter("contentId", contentId)
				.executeUpdate();
	}

	@Override
	public void addToFavorites(int userId, int contentId) {
		em.createNativeQuery("INSERT INTO users_contents_favorites (user_id, content_id) VALUES (:userId, :contentId)")
				.setParameter("userId", userId)
				.setParameter("contentId", contentId)
				.executeUpdate();
	}

}
