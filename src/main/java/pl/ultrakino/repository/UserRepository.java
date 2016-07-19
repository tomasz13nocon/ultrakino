package pl.ultrakino.repository;

import pl.ultrakino.model.User;

import java.util.List;

public interface UserRepository {

	void save(User user);

	List<User> findAll();

}
