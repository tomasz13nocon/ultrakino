package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import pl.ultrakino.controller.FilmController;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmListResource;
import pl.ultrakino.resources.FilmResource;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class FilmListResourceAsm extends ResourceAssemblerSupport<List<Film>, FilmListResource> {

	public FilmListResourceAsm() {
		super(FilmController.class, FilmListResource.class);
	}

	@Override
	public FilmListResource toResource(List<Film> list) {
		List<FilmResource> entries = new FilmResourceAsm().toResources(list);
		FilmListResource res = new FilmListResource();
		res.setEntries(entries);
//		res.add(linkTo(FilmController.class).);
		return res;
	}
}
