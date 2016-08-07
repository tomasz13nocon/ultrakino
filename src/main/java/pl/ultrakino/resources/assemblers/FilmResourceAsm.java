package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.controller.FilmController;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class FilmResourceAsm extends ResourceAssemblerSupport<Film, FilmResource> {

	public FilmResourceAsm() {
		super(FilmController.class, FilmResource.class);
	}

	@Override
	public FilmResource toResource(Film film) {
		FilmResource res = new FilmResource();
		res.setTitle(film.getTitle());
		res.setRating(film.getRating());
		res.setTimesRated(film.getTimesRated());
		res.setOriginalTitle(film.getOriginalTitle());
		res.setDescription(film.getDescription());
		res.setCoverFilename(film.getCoverFilename());
		res.setWorldPremiere(film.getWorldPremiere());
		res.setLocalPremiere(film.getLocalPremiere());
		res.setViews(film.getViews());
		res.setRecommendedOn(film.getRecommendationDate());

		res.add(linkTo(FilmController.class).slash(film.getId()).withSelfRel());
		return res;
	}

}
