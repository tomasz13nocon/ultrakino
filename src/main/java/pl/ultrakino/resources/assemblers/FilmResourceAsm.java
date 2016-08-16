package pl.ultrakino.resources.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.web.FilmController;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class FilmResourceAsm extends ResourceAssemblerSupport<Film, FilmResource> {

	private PersonResourceAsm personResourceAsm;
	private PlayerResourceAsm playerResourceAsm;

	private boolean
	includeCast,
	includePlayers,
	includeCategories;

	@Autowired
	public FilmResourceAsm(PersonResourceAsm personResourceAsm, PlayerResourceAsm playerResourceAsm) {
		super(FilmController.class, FilmResource.class);
		this.personResourceAsm = personResourceAsm;
		this.playerResourceAsm = playerResourceAsm;
	}

	public FilmResourceAsm cast() {
		includeCast = true;
		return this;
	}

	public FilmResourceAsm players() {
		includePlayers = true;
		return this;
	}

	public FilmResourceAsm categories() {
		includeCategories = true;
		return this;
	}


	@Override
	public FilmResource toResource(Film film) {
		FilmResource res = new FilmResource();
		res.setFilmId(film.getId());
		res.setTitle(film.getTitle());
		res.setRating(film.getRating());
		res.setTimesRated(film.getTimesRated());
		res.setOriginalTitle(film.getOriginalTitle());
		res.setDescription(film.getDescription());
		res.setCoverFilename(film.getCoverFilename());
		res.setWorldPremiere(film.getWorldPremiere());
		res.setLocalPremiere(film.getLocalPremiere());
		res.setViews(film.getViews());
		res.setYear(film.getYear());
		res.setRecommendationDate(film.getRecommendationDate());
		if (includeCast) {
			// Beans are singletons so these have to be reset for the next assembling.
			res.setCast(personResourceAsm.toResources(film.getCast()));
			includeCast = false;
		}
		if (includePlayers) {
			res.setPlayers(playerResourceAsm.toResources(film.getPlayers()));
			includePlayers = false;
		}
		if (includeCategories) {
			res.setCategories(film.getCategories());
			includeCategories = false;
		}

		res.add(linkTo(FilmController.class).slash(film.getId()).withSelfRel());
		return res;
	}

}
