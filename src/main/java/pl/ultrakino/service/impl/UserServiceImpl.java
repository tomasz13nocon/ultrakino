package pl.ultrakino.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ultrakino.Constants;
import pl.ultrakino.exceptions.FileDeletionException;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.ContentRepository;
import pl.ultrakino.repository.UserRepository;
import pl.ultrakino.resource.UserDetailsResource;
import pl.ultrakino.resource.UserResource;
import pl.ultrakino.service.ContentService;
import pl.ultrakino.service.PlayerService;
import pl.ultrakino.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ContentRepository contentRepository;
	// Autowired at fields, because of circular dependencies
	@Autowired
	private PlayerService playerService;
	@Autowired
	private ContentService contentService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ContentRepository contentRepository) {
		this.userRepository = userRepository;
		this.contentRepository = contentRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opUser = findByUsername(username);
		if (!opUser.isPresent())
			throw new UsernameNotFoundException("Username '" + username + "' not found.");
		return opUser.get();
	}

	@Override
	public Optional<User> findByUsername(String username, boolean fetchCollections) {
		return userRepository.findByUsername(username, fetchCollections);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return findByUsername(username, false);
	}

	@Override
	public User findById(int id) throws NoRecordWithSuchIdException {
		return userRepository.findById(id);
	}

	@Override
	public User findByIdWithCollections(int id) throws NoRecordWithSuchIdException {
		return userRepository.findByIdWithCollections(id);
	}

	@Override
	public User create(String username, String password, String email) {
		if (findByUsername(username).isPresent())
			throw new IllegalArgumentException("This username already exists");
		if (password.length() < 8)
			throw new IllegalArgumentException("Password must be at least 8 characters long");
		if (username.length() < 3 || username.length() > 64)
			throw new IllegalArgumentException("username must be at least 3 and at most 64 characters long");

		// TODO finish
		User user = new User();
		user.setUsername(username);
		user.setPasswd(BCrypt.hashpw(password, BCrypt.gensalt()));
		user.setEmail(email);
		user.setRoles(new HashSet<>(Collections.singletonList("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetailsResource toDetailsResource(User user) {
		UserDetailsResource res = new UserDetailsResource();
		res.setUid(user.getId());
		res.setUsername(user.getUsername());
		res.setAvatarFilename(user.getAvatarFilename() == null ? Constants.DEFAULT_AVATAR : user.getAvatarFilename());
		res.setEmail(user.getEmail());
		if (Hibernate.isInitialized(user.getAddedPlayers()))
			res.setAddedPlayers(new HashSet<>(playerService.toResources(user.getAddedPlayers(), true)));
		if (Hibernate.isInitialized(user.getWatchedContent()))
			res.setWatchedContent(new HashSet<>(contentService.toResources(user.getWatchedContent())));
		if (Hibernate.isInitialized(user.getFavorites()))
			res.setFavorites(new HashSet<>(contentService.toResources(user.getFavorites())));
		if (Hibernate.isInitialized(user.getWatchlist()))
			res.setWatchlist(new HashSet<>(contentService.toResources(user.getWatchlist())));
		res.setRegistrationDate(user.getRegistrationDate());
		res.setRoles(user.getRoles());
		return res;
	}


	@Override
	public UserResource toResource(User user) {
		UserResource res = new UserResource();
		res.setUid(user.getId());
		res.setUsername(user.getUsername());
		res.setAvatarFilename(user.getAvatarFilename() == null ? Constants.DEFAULT_AVATAR : user.getAvatarFilename());
		return res;
	}

	@Override
	public void merge(User user) {
		userRepository.merge(user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public List<User> find(int start, int maxResults) {
		return userRepository.find(start, maxResults);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public void remove(int userId) throws NoRecordWithSuchIdException, FileDeletionException {
		User user = userRepository.findById(userId);
		if (user.getAvatarFilename() != null) {
			try {
				Files.deleteIfExists(Paths.get(Constants.AVATARS_DIRECTORY + user.getAvatarFilename()));
			} catch (IOException e) {
				System.err.println("=== Deleting an image failed. This could be a permissions issue. ===");
				throw new FileDeletionException("Deleting an image failed.");
			}
		}
		Set<Player> players = user.getAddedPlayers();
		for (Player player : players) {
			player.setAddedBy(null);
		}
		userRepository.remove(user);
	}

	@Override
	public List<Content> getWatchlist(int userId) {
		return userRepository.getWatchlist(userId);
	}

	@Override
	public List<Content> getFavorites(int userId) {
		return userRepository.getFavorites(userId);
	}

	@Override
	public void removeFromWatchlist(int userId, int contentId) {
		userRepository.removeFromWatchlist(userId, contentId);
	}

	@Override
	public void removeFromFavorites(int userId, int contentId) {
		userRepository.removeFromFavorites(userId, contentId);
	}

	@Override
	public void addToWatchlist(int userId, int contentId) {
		try {
			User user = userRepository.findById(userId);
			user.getWatchlist().add(contentRepository.findById(contentId));
		} catch (NoRecordWithSuchIdException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addToFavorites(int userId, int contentId) {
		userRepository.addToFavorites(userId, contentId);
	}

}
