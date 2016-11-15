package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.*;
import pl.ultrakino.repository.*;
import pl.ultrakino.resource.FilmDetailsResource;
import pl.ultrakino.resource.FilmResource;
import pl.ultrakino.resource.PersonResource;
import pl.ultrakino.resource.PlayerResource;
import pl.ultrakino.service.*;

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

	@Autowired
	public FilmServiceImpl(FilmRepository filmRepository,
						   PersonRepository personRepository,
						   UserRepository userRepository,
						   RatingRepository ratingRepository,
						   PersonService personService,
						   PlayerService playerService,
						   CommentService commentService) {
		this.filmRepository = filmRepository;
		this.personRepository = personRepository;
		this.userRepository = userRepository;
		this.ratingRepository = ratingRepository;
		this.personService = personService;
		this.playerService = playerService;
		this.commentService = commentService;
	}


	// TODO: Change name to be more descriptive
	private Set<Player> createPlayers(List<PlayerResource> resources, Integer userId) {
		Set<Player> players = new HashSet<>();
		User user = userRepository.getUserReference(userId);
		for (PlayerResource playerResource : resources) {
			Player player = new Player();
			player.setLanguageVersion(playerResource.getLanguageVersion());
			player.setSrc(playerResource.getSrc());
			player.setQuality(playerResource.getQuality());
			player.setAddedBy(user);
		}

		return players;
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
		res.setCategories(film.getCategories());
		res.setLanguageVersions(film.getPlayers().stream().map(Player::getLanguageVersion).collect(Collectors.toSet()));
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
		if (auth != null && auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).contains("ROLE_USER")){
			Optional<Rating> userRating = ratingRepository.findByUsernameAndContentId(
					((UserDetails) auth.getPrincipal()).getUsername(),
					film.getId());
			if (userRating.isPresent())
				res.setUserRating(userRating.get().getRating());
		}
		res.setOriginalTitle(film.getOriginalTitle());
		res.setDescription(film.getDescription());
		res.setCoverFilename(film.getCoverFilename());
		res.setWorldPremiere(film.getWorldPremiere());
		res.setLocalPremiere(film.getLocalPremiere());
		res.setViews(film.getViews());
		res.setYear(film.getYear());
		res.setRecommendationDate(film.getRecommendationDate());
		res.setCast(personService.toResources(film.getCastAndCrew()));
		res.setPlayers(playerService.toResources(film.getPlayers()));
		res.setCategories(film.getCategories());
		res.setLanguageVersions(film.getPlayers().stream().map(Player::getLanguageVersion).collect(Collectors.toSet()));
		res.setComments(commentService.toResources(film.getComments()));
		return res;
	}

}
