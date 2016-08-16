package pl.ultrakino.repository;

import pl.ultrakino.model.User;

import java.util.List;

public interface UserRepository {

	void save(User user);

	List<User> findAll();

	/**
	 * Lazily fetches reference to the user. See {@link javax.persistence.EntityManager#getReference(Class, Object)}
	 * @param userId id of the user
	 * @return Lazily fetched {@code User} reference
	 * @throws IllegalArgumentException if {@code userId} is null
	 * @throws javax.persistence.EntityNotFoundException if there is no {@code User} with given id
	 */
	User getUserReference(Integer userId);

}
