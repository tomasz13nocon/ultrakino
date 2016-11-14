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
import pl.ultrakino.resources.UserDetailsResource;
import pl.ultrakino.resources.UserResource;
import pl.ultrakino.service.ContentService;
import pl.ultrakino.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PlayerServiceImpl playerService;
	private ContentService contentService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PlayerServiceImpl playerService, ContentService contentService) {
		this.userRepository = userRepository;
		this.playerService = playerService;
		this.contentService = contentService;
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

	@Override
	public User create(String username, String password, String email) {
		if (findByUsername(username).isPresent())
			throw new IllegalArgumentException("This username already exists");
		if (password.length() < 8)
			throw new IllegalArgumentException("Password must be at least 8 characters long");
		if (username.length() < 3 || username.length() > 64)
			throw new IllegalArgumentException("username must be at least 3 and at most 64 characters long");


		return null;
	}

	@Override
	public UserDetailsResource toDetailsResource(User user) {
		UserDetailsResource res = new UserDetailsResource();
		res.setUid(user.getId());
		res.setUsername(user.getUsername());
		res.setAvatarFilename(user.getAvatarFilename());
		res.setEmail(user.getEmail());
		res.setAddedPlayers(playerService.toResources(user.getAddedPlayers()));
		res.setWatchedContent(contentService.toResources(user.getWatchedContent()));
		res.setFavorites(new HashSet<>(contentService.toResources(user.getFavorites())));
		res.setWatchlist(new HashSet<>(contentService.toResources(user.getWatchlist())));
		return res;
	}


	@Override
	public UserResource toResource(User user) {
		UserResource res = new UserResource();
		res.setUid(user.getId());
		res.setUsername(user.getUsername());
		res.setAvatarFilename(user.getAvatarFilename());
		return res;
	}

}
