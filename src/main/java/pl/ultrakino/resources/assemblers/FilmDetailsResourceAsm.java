package pl.ultrakino.resources.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.Film;
import pl.ultrakino.resources.FilmDetailsResource;
import pl.ultrakino.web.FilmController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class FilmDetailsResourceAsm extends ResourceAssemblerSupport<Film, FilmDetailsResource> {

	private PersonResourceAsm personResourceAsm;
	private PlayerResourceAsm playerResourceAsm;
	private CommentResourceAsm commentResourceAsm;

	@Autowired
	public FilmDetailsResourceAsm(PersonResourceAsm personResourceAsm, PlayerResourceAsm playerResourceAsm, CommentResourceAsm commentResourceAsm) {
		super(FilmController.class, FilmDetailsResource.class);
		this.personResourceAsm = personResourceAsm;
		this.playerResourceAsm = playerResourceAsm;
		this.commentResourceAsm = commentResourceAsm;
	}


	@Override
	public FilmDetailsResource toResource(Film film) {
		FilmDetailsResource res = new FilmDetailsResource();
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
		res.setCast(personResourceAsm.toResources(film.getCastAndCrew()));
		res.setPlayers(playerResourceAsm.toResources(film.getPlayers()));
		res.setCategories(film.getCategories());
		res.setComments(commentResourceAsm.toResources(film.getComments()));

		res.add(linkTo(FilmController.class).slash(film.getId()).withSelfRel());
		//res.add(li);
		return res;
	}

}
