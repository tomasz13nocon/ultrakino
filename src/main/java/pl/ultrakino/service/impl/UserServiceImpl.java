package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.UserRepository;
import pl.ultrakino.service.UserService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opUser = findByUsername(username);
		if (!opUser.isPresent())
			throw new UsernameNotFoundException("Username '" + username + "' not found.");
		User user = opUser.get();
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPasswd(),
				user.getRoles()
						.stream()
						.map((final String e) -> new GrantedAuthority() {
							private final String authority;
							{
								this.authority = e;
							}
							@Override
							public String getAuthority() {
								return authority;
							}
						})
						.collect(Collectors.toList()));
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(int id) throws NoRecordWithSuchIdException {
		return userRepository.findById(id);
	}
}
