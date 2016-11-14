package pl.ultrakino.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.resource.UserDetailsResource;
import pl.ultrakino.resource.UserResource;

import java.util.Optional;

public interface UserService extends UserDetailsService {

	Optional<User> findByUsername(String username);
	User findById(int id) throws NoRecordWithSuchIdException;

	User create(String username, String password, String email);

	UserDetailsResource toDetailsResource(User user);

	UserResource toResource(User user);
}
