package pl.ultrakino.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.Constants;
import pl.ultrakino.Utils;
import pl.ultrakino.exceptions.FileDeletionException;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.*;
import pl.ultrakino.repository.*;
import pl.ultrakino.resource.FilmDetailsResource;
import pl.ultrakino.resource.FilmResource;
import pl.ultrakino.resource.PlayerResource;
import pl.ultrakino.service.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

	private FilmRepository filmRepository;
	private PersonRepository personRepository;
	private UserRepository userRepository;
	private RatingRepository ratingRepository;
	private PersonService personService;
	private PlayerService playerService;
	private CommentService commentService;
	private PlaylistRepository playlistRepository;

	@Autowired
	public FilmServiceImpl(FilmRepository filmRepository,
						   PersonRepository personRepository,
						   UserRepository userRepository,
						   RatingRepository ratingRepository,
						   PersonService personService,
						   PlayerService playerService,
						   CommentService commentService, PlaylistRepository playlistRepository) {
		this.filmRepository = filmRepository;
		this.personRepository = personRepository;
		this.userRepository = userRepository;
		this.ratingRepository = ratingRepository;
		this.personService = personService;
		this.playerService = playerService;
		this.commentService = commentService;
		this.playlistRepository = playlistRepository;
	}


	// TODO: Change name to be more descriptive
	private Set<Player> createPlayers(List<PlayerResource> resources, Integer userId) {
		Set<Player> players = new HashSet<>();
		User user = userRepository.getUserReference(userId);
		for (PlayerResource playerResource : resources) {
			Player player = new Player();
			player.setLanguageVersion(playerResource.getLanguageVersion());
			player.setSrc(playerResource.getSrc());
//			player.setQuality(playerResource.getQuality());
			player.setAddedBy(user);
		}

		return players;
	}

	@Override
	public void remove(Film film) throws FileDeletionException {
		//TODO mark users who loose their playlists' entries
		playlistRepository.removeByFilm(film);
		try {
			Files.deleteIfExists(Paths.get(Constants.COVERS_DIRECTORY + film.getCoverFilename()));
		} catch (IOException e) {
			System.err.println("=== Deleting an image failed. This could be a permissions issue. ===");
			throw new FileDeletionException("Deleting an image failed.");
		}
		filmRepository.remove(film);

		// Alternative code that doesn't really remove the film from db but clears out most fields,
		// so that the film still can be linked from playlists and such
		/*List<Rating> ratings = film.getRatings();
		for (Rating rating : ratings) {
			rating.setContent(null);
		}
		ratings.clear();
		film.setRating(null);
		film.setTimesRated(0);
		film.setFilmwebId(null);
		film.setDescription(null);
		try {
			Files.deleteIfExists(Paths.get(Constants.COVERS_DIRECTORY + film.getCoverFilename()));
		} catch (IOException e) {
			throw new FileDeletionException("Deleting an image failed. This could be a permissions issue.");
		}
		film.setCoverFilename(null);
		Set<FilmographyEntry> castAndCrew = film.getCastAndCrew();
		for (FilmographyEntry entry : castAndCrew) {
			entry.setContent(null);
		}
		castAndCrew.clear();
		film.getFilmCategories().clear();
		film.getProductionCountries().clear();
		film.setLocalPremiere(null);
		Set<Player> players = film.getPlayers();
		for (Player player : players) {
			player.setContent(null);
		}
		players.clear();
		film.setViews(0);
		film.setRecommendationDate(null);
		film.setAdditionDate(null);*/
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public void remove(int filmId) throws NoRecordWithSuchIdException, FileDeletionException {
		Film film = filmRepository.findById(filmId);
		remove(film);
	}

	@Override
	public Film findById(Integer id) throws NoRecordWithSuchIdException {
		return filmRepository.findById(id);
	}

	@Override
	public Page<Film> find(MultiValueMap<String, String> params) {
		ContentQuery query = new ContentQuery(params);
		return filmRepository.find(query);
	}

	@Override
	public Film save(Film film) {
		return filmRepository.save(film);
	}

	@Override
	public void recommend(int filmId) throws NoRecordWithSuchIdException {
		Film film = filmRepository.findById(filmId);
		film.setRecommendationDate(LocalDateTime.now());
	}

	@Override
	public void deleteRecommendation(int filmId) throws NoRecordWithSuchIdException {
		Film film = filmRepository.findById(filmId);
		film.setRecommendationDate(null);
	}


	@Override
	public FilmResource toResource(Film film) {
		FilmResource res = new FilmResource();
		res.setUid(film.getId());
		res.setTitle(film.getTitle());
		res.setRating(film.getRating());
		res.setTimesRated(film.getTimesRated());
		res.setOriginalTitle(film.getOriginalTitle());
		res.setDescription(film.getDescription());
		res.setCoverFilename(film.getCoverFilename());
		res.setWorldPremiere(film.getWorldPremiere());
		res.setLocalPremiere(film.getLocalPremiere());
		res.setYear(film.getYear());
		res.setCategories(film.getFilmCategories());
		try {
			res.setLanguageVersions(film.getPlayers().stream().map(Player::getLanguageVersion).collect(Collectors.toSet()));
		}
		catch (LazyInitializationException e) {}
		return res;
	}

	@Override
	public List<FilmResource> toResources(Collection<Film> films) {
		return films.stream().map(this::toResource).collect(Collectors.toList());
	}

	@Override
	public FilmDetailsResource toDetailsResource(Film film) {
		FilmDetailsResource res = new FilmDetailsResource();
		res.setUid(film.getId());
		res.setTitle(film.getTitle());
		res.setRating(film.getRating());
		res.setTimesRated(film.getTimesRated());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (Utils.isUser(auth)) {
			try {
				User user = userRepository.findByIdWithCollections(((User) auth.getPrincipal()).getId());
				for (Rating rating : film.getRatings()) {
					if (rating.getRatedBy().getId().equals(user.getId())) {
						res.setUserRating(rating.getRating());
						break;
					}
				}
				res.setInUsersWatchlist(user.getWatchlist().contains(film));
				res.setInUsersFavorites(user.getFavorites().contains(film));
			} catch (NoRecordWithSuchIdException e) {
				// Only happens when authenticated user is no longer in db, so when it was deleted mid execution.
				// In that case just continue without user data.
			}
		}
		res.setOriginalTitle(film.getOriginalTitle());
		res.setDescription(film.getDescription());
		res.setCoverFilename(film.getCoverFilename());
		res.setWorldPremiere(film.getWorldPremiere());
		res.setLocalPremiere(film.getLocalPremiere());
		res.setViews(film.getViews());
		res.setYear(film.getYear());
		res.setRecommendationDate(film.getRecommendationDate());
		res.setCastAndCrew(personService.toResources(film.getCastAndCrew()));
		res.setPlayers(film.getPlayers().stream()
				.filter(p -> Constants.SUPPORTED_HOSTINGS.contains(p.getHosting()))
				.map(playerService::toResource)
				.collect(Collectors.toList()));
		res.setCategories(film.getFilmCategories());
		res.setLanguageVersions(film.getPlayers().stream().map(Player::getLanguageVersion).collect(Collectors.toSet()));
		res.setFilmwebId(film.getFilmwebId());
		res.setComments(commentService.toResources(film.getComments()));
		return res;
	}

	@Override
	public Film extractNewFilm(ObjectNode filmJson) {
		Film film = new Film();
		film.setTitle(filmJson.get("title").asText());
		return film;
	}

	@Override
	public Optional<Film> findByFilmwebId(String filmwebId) {
		return filmRepository.findByFilmwebId(filmwebId);
	}

	@Override
	public FilmDetailsResource findById(int filmId, int userId) throws NoRecordWithSuchIdException {
		Film film = filmRepository.findById(filmId);
		return toDetailsResource(film);
	}

}
