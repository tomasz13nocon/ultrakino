package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.web.FilmController;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmDetailsResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class FilmDetailsResourceAsm extends ResourceAssemblerSupport<Film, FilmDetailsResource> {

	public FilmDetailsResourceAsm() {
		super(FilmController.class, FilmDetailsResource.class);
	}

	@Override
	public FilmDetailsResource toResource(Film film) {
		FilmDetailsResource res = new FilmDetailsResource();
		res.setTitle(film.getTitle());
		res.setRating(film.getRating());
		res.setTimesRated(film.getTimesRated());
		res.setOriginalTitle(film.getOriginalTitle());
		res.setDescription(film.getDescription());
		res.setCoverFilename(film.getCoverFilename());
		res.setCast(film.getCast());
		res.setWorldPremiere(film.getWorldPremiere());
		res.setLocalPremiere(film.getLocalPremiere());
		res.setPlayers(film.getPlayers());
		res.setViews(film.getViews());
		res.setCategories(film.getCategories());
		res.setRecommendedOn(film.getRecommendationDate());

		res.add(linkTo(FilmController.class).slash(film.getId()).withSelfRel());
		return res;
	}
}
