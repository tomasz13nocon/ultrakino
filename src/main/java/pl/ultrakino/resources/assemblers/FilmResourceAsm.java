package pl.ultrakino.resources.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.Player;
import pl.ultrakino.web.FilmController;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmResource;

import java.util.stream.Collectors;

@Component
public class FilmResourceAsm extends ResourceAssemblerSupport<Film, FilmResource> {

	public FilmResourceAsm() {
		super(FilmController.class, FilmResource.class);
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
		res.setYear(film.getYear());
		res.setCategories(film.getCategories());
		res.setLanguageVersions(film.getPlayers().stream().map(Player::getLanguageVersion).collect(Collectors.toSet()));
		return res;
	}
}
