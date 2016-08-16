package pl.ultrakino.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Person;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.User;
import pl.ultrakino.repository.FilmRepository;
import pl.ultrakino.repository.PersonRepository;
import pl.ultrakino.repository.UserRepository;
import pl.ultrakino.resources.FilmResource;
import pl.ultrakino.resources.PersonResource;
import pl.ultrakino.resources.PlayerResource;
import pl.ultrakino.service.FilmService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

	private FilmRepository filmRepository;
	private PersonRepository personRepository;
	private UserRepository userRepository;

	/**
	 * For unit tests
	 */
	FilmServiceImpl() {}

	@Autowired
	public FilmServiceImpl(FilmRepository filmRepository, PersonRepository personRepository, UserRepository userRepository) {
		this.filmRepository = filmRepository;
		this.personRepository = personRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Creates Film entity and inserts it into the database
	 * @param filmResource
	 * @return created film
	 * @throws
	 */
	@Override
	public Film create(FilmResource filmResource) {
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
				person.setRole(personResource.getRole());
				cast.add(person);
			}
		}
		film.setCast(cast);

		List<PlayerResource> players = filmResource.getPlayers();
		if (players.get(0).getAddedBy().getUserId() == null)
			throw new IllegalStateException("user ID cannot be null");
		film.setPlayers(createPlayers(
				players,
				players.get(0).getAddedBy().getUserId()
		));

		filmRepository.create(film);
		return film;
	}

	// TODO: Change name to be more descriptive
	private List<Player> createPlayers(List<PlayerResource> resources, Integer userId) {
		List<Player> players = new ArrayList<>();
		User user = userRepository.getUserReference(userId);
		for (PlayerResource playerResource : resources) {
			Player player = new Player();
			player.setLanguageVersion(playerResource.getLanguageVersion());
			player.setSrc(playerResource.getSrc());
			player.setQuality(playerResource.getQuality());
			player.setAddedBy(user);
			player.setForeignSrc(playerResource.isForeignSrc());
			player.setLostSrc(playerResource.isLostSrc());
		}

		return players;
	}

	@Override
	public Film findById(Integer id) throws NoRecordWithSuchIdException {
		Film film = filmRepository.findById(id);
		Hibernate.initialize(film.getCast());
		Hibernate.initialize(film.getCategories());
		Hibernate.initialize(film.getPlayers());
		Hibernate.initialize(film.getRatings());
		return film;
	}

	@Override
	public List<Film> findRecommended() {
		return filmRepository.findRecommended();
	}

	@Override
	public List<Film> findNewest() {
		return filmRepository.findNewest();
	}

	@Override
	public List<Film> findMostWatched() {
		return filmRepository.findMostWatched();
	}

	@Override
	public List<Film> search(String query) {
		return filmRepository.search(query);
	}

	@Override
	public List<Film> advancedSearch(MultiValueMap<String, String> params) {
		return filmRepository.advancedSearch(params);
	}

}
