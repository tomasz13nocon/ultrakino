package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.exceptions.NoUserWithSuchUsernameException;
import pl.ultrakino.model.*;
import pl.ultrakino.repository.*;
import pl.ultrakino.resources.FilmDetailsResource;
import pl.ultrakino.resources.PersonResource;
import pl.ultrakino.resources.PlayerResource;
import pl.ultrakino.resources.assemblers.FilmDetailsResourceAsm;
import pl.ultrakino.service.FilmService;
import pl.ultrakino.service.RatingService;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

	private FilmRepository filmRepository;
	private PersonRepository personRepository;
	private UserRepository userRepository;
	private FilmDetailsResourceAsm filmDetailsResourceAsm;
	private RatingRepository ratingRepository;
	private RatingService ratingService;

	@Autowired
	public FilmServiceImpl(FilmRepository filmRepository, PersonRepository personRepository, UserRepository userRepository, FilmDetailsResourceAsm filmDetailsResourceAsm, RatingRepository ratingRepository, RatingService ratingService) {
		this.filmRepository = filmRepository;
		this.personRepository = personRepository;
		this.userRepository = userRepository;
		this.filmDetailsResourceAsm = filmDetailsResourceAsm;
		this.ratingRepository = ratingRepository;
		this.ratingService = ratingService;
	}

	/**
	 * Creates Film entity and inserts it into the database
	 * @param filmResource
	 * @return created film
	 * @throws
	 */
	@Override
	public Film create(FilmDetailsResource filmResource) {
		Film film = new Film();
		film.setTitle(filmResource.getTitle());
		film.setOriginalTitle(filmResource.getOriginalTitle());
		film.setDescription(filmResource.getDescription());
		film.setCoverFilename(filmResource.getCoverFilename());
		film.setWorldPremiere(filmResource.getWorldPremiere());
		film.setLocalPremiere(filmResource.getLocalPremiere());
		film.setCategories(filmResource.getCategories());
		film.setRecommendationDate(filmResource.getRecommendationDate()); // TODO: Can i have it here?

		// If a Person already exists in DB, insert the existing one into the cast list.
		ListIterator<PersonResource> iterator = filmResource.getCast().listIterator();
		List<Person> cast = new ArrayList<>();
		while (iterator.hasNext()) {
			PersonResource personResource = iterator.next();
			Optional<Person> dbPerson = personRepository.findByName(personResource.getName());
			if (dbPerson.isPresent())
				cast.add(dbPerson.get());
			else {
				Person person = new Person();
				person.setName(personResource.getName());
				// TODO
//				person.setRole(personResource.getRole());
				cast.add(person);
			}
		}
		// TODO
//		film.setCast(cast);

		List<PlayerResource> players = filmResource.getPlayers();
		if (players.get(0).getAddedBy().getUid() == null)
			throw new IllegalStateException("user ID cannot be null");
		film.setPlayers(createPlayers(
				players,
				players.get(0).getAddedBy().getUid()
		));

		filmRepository.save(film);
		return film;
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
	public FilmDetailsResource findById(Integer id) throws NoRecordWithSuchIdException {
		return filmDetailsResourceAsm.toResource(filmRepository.findById(id));
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

}
