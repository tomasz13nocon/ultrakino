package pl.ultrakino.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.resources.UserResource;

import java.util.Optional;

public interface UserService extends UserDetailsService {

	Optional<User> findByUsername(String username);
	User findById(int id) throws NoRecordWithSuchIdException;

	Optional<UserResource> create(String username, String password, String email);

}
