package pl.ultrakino.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.ultrakino.exceptions.NoRecordWithSuchIdException;
import pl.ultrakino.model.*;
import pl.ultrakino.repository.*;
import pl.ultrakino.resources.FilmDetailsResource;
import pl.ultrakino.resources.PersonResource;
import pl.ultrakino.resources.PlayerResource;
import pl.ultrakino.service.FilmService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class FilmServiceImpl implements FilmService {

	private FilmRepository filmRepository;
	private PersonRepository personRepository;
	private UserRepository userRepository;

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
	private Set<Player> createPlayers(List<PlayerResource> resources, Integer userId) {
		Set<Player> players = new HashSet<>();
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
		return filmRepository.findById(id);
	}

	@Override
	public Page<Film> find(MultiValueMap<String, String> params) {
		FilmQuery query = new FilmQuery();


		List<String> titleParam = params.get("title");
		if (titleParam != null)
			query.title(titleParam.get(0));


		List<String> yearFromParam = params.get("yearFrom");
		if (yearFromParam != null) {
			try {
				query.yearFrom(Integer.parseInt(yearFromParam.get(0)));
			}
			catch(NumberFormatException e){
				throw new IllegalArgumentException("value of yearFrom parameter is not a valid integer");
			}
		}


		List<String> yearToParam = params.get("yearTo");
		if (yearToParam != null) {
			try {
				query.yearTo(Integer.parseInt(yearToParam.get(0)));
			}
			catch(NumberFormatException e){
				throw new IllegalArgumentException("value of yearTo parameter is not a valid integer");
			}
		}


		List<String> categoriesParam = params.get("categories");
		if (categoriesParam != null) {
			try {
				List<Integer> categories = categoriesParam.stream().map(Integer::parseInt).collect(Collectors.toList());
				query.categories(categories);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of categories parameter contains an invalid integer");
			}
		}


		List<String> orderByParam = params.get("orderBy");
		if (orderByParam != null) {
			try {
				FilmQuery.OrderBy orderBy = FilmQuery.OrderBy.valueOf(orderByParam.get(0));
				query.orderBy(orderBy);
			}
			catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("value of orderBy parameter is not a valid OrderBy constant");
			}
		}


		List<String> ascParam = params.get("asc");
		if (ascParam != null && ascParam.get(0).equals("true")) {
			query.asc(true);
		}


		List<String> resultLimitParam = params.get("resultLimit");
		if (resultLimitParam != null) {
			try {
				Integer resultLimit = Integer.parseInt(resultLimitParam.get(0));
				query.resultLimit(resultLimit);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of resultLimit parameter is not a valid integer");
			}
		}


		List<String> pageNumberParam = params.get("pageNumber");
		if (pageNumberParam != null) {
			try {
				Integer pageNumber = Integer.parseInt(pageNumberParam.get(0));
				query.pageNumber(pageNumber);
			}
			catch (NumberFormatException e) {
				throw new IllegalArgumentException("value of pageNumber parameter is not a valid integer");
			}
		}


		return filmRepository.find(query);
	}

	@Override
	public void recommendFilm(int filmId) throws NoRecordWithSuchIdException {
		Film film = filmRepository.findById(filmId);
		film.setRecommendationDate(LocalDateTime.now());
	}

}
