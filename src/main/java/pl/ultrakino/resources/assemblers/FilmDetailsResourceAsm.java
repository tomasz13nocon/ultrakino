package pl.ultrakino.resources.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Rating;
import pl.ultrakino.repository.RatingRepository;
import pl.ultrakino.resources.FilmDetailsResource;
import pl.ultrakino.service.CommentService;
import pl.ultrakino.web.FilmController;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class FilmDetailsResourceAsm extends ResourceAssemblerSupport<Film, FilmDetailsResource> {

	private PersonResourceAsm personResourceAsm;
	private PlayerResourceAsm playerResourceAsm;
	private RatingRepository ratingRepository;
	private CommentService commentService;

	@Autowired
	public FilmDetailsResourceAsm(PersonResourceAsm personResourceAsm, PlayerResourceAsm playerResourceAsm, RatingRepository ratingRepository, CommentService commentService) {
		super(FilmController.class, FilmDetailsResource.class);
		this.personResourceAsm = personResourceAsm;
		this.playerResourceAsm = playerResourceAsm;
		this.ratingRepository = ratingRepository;
		this.commentService = commentService;
	}


	@Override
	public FilmDetailsResource toResource(Film film) {
		FilmDetailsResource res = new FilmDetailsResource();
		res.setUid(film.getId());
		res.setTitle(film.getTitle());
		res.setRating(film.getRating());
		res.setTimesRated(film.getTimesRated());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).contains("ROLE_USER")){
			Optional<Rating> userRating = ratingRepository.findByUsernameAndContentId(
					((UserDetails) auth.getPrincipal()).getUsername(),
					film.getId());
			if (userRating.isPresent())
				res.setUserRating(userRating.get().getRating());
		}
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
		res.setComments(commentService.toResources(film.getComments()));

		res.add(linkTo(FilmController.class).slash(film.getId()).withSelfRel());
		//res.add(li);
		return res;
	}

}
