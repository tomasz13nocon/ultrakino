package pl.ultrakino.repository;

import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

	User save(User user);

	List<User> findAll();

	/**
	 * Lazily fetches reference to the user. See {@link javax.persistence.EntityManager#getReference(Class, Object)}
	 * @param userId id of the user
	 * @return Lazily fetched {@code User} reference
	 * @throws IllegalArgumentException if {@code userId} is null
	 * @throws javax.persistence.EntityNotFoundException if there is no {@code User} with given id
	 */
	User getUserReference(Integer userId);

	Optional<User> findByUsername(String username, boolean fetchCollections);
	Optional<User> findByUsername(String username);

	User findById(int id) throws NoRecordWithSuchIdException;

/*	/**
	 * Finds all users who have specified film in any of their playlists.
	 * @param film Film
	 * @return List of Users
	 *//*
	List<User> findByFilmInAnyPlaylist(Film film);*/

	User findByIdWithCollections(int id) throws NoRecordWithSuchIdException;

	void merge(User user);

	List<User> find(int start, int maxResults);

	void remove(User user);

	List<Content> getWatchlist(int userId);

	List<Content> getFavorites(int userId);

	void removeFromWatchlist(int userId, int contentId);

	void removeFromFavorites(int userId, int contentId);

	void addToFavorites(int userId, int contentId);
}
