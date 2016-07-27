package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.controller.FilmController;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class FilmResourceAssembler extends ResourceAssemblerSupport<Film, FilmResource> {

	public FilmResourceAssembler() {
		super(FilmController.class, FilmResource.class);
	}

	@Override
	public FilmResource toResource(Film film) {
		FilmResource res = new FilmResource(film);
		res.add(linkTo(FilmController.class).slash(film.getId()).withSelfRel());
		return res;
	}

}
