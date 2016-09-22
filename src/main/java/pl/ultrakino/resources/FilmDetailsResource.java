package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.Film;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class FilmDetailsResource extends ContentResource {

//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer filmId;

//	@JsonView(Views.FilmCreation.class)
	private String title;

	private Integer year;

//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Float rating;

//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer timesRated;

	private String originalTitle;

	private String description;

	private String coverFilename;

	private List<PersonResource> cast = new ArrayList<>();

	private LocalDate worldPremiere;

	private LocalDate localPremiere;

	private List<PlayerResource> players = new ArrayList<>();

//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer views;

	private Set<Integer> categories = new HashSet<>();

	private List<CommentResource> comments = new ArrayList<>();

//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime recommendationDate;


	@Override
	public Film toDomainObject() {
		Film film = new Film();
		film.setTitle(title);
		film.setRating(rating);
		film.setTimesRated(timesRated);
		film.setOriginalTitle(originalTitle);
		film.setDescription(description);
		film.setCoverFilename(coverFilename);
//		film.setCast(cast.stream().map(PersonResource::toPerson).collect(Collectors.toList()));
		film.setWorldPremiere(worldPremiere);
		film.setLocalPremiere(localPremiere);
		film.setPlayers(players.stream().map(PlayerResource::toDomainObject).collect(Collectors.toSet()));
		film.setViews(views);
		film.setCategories(categories);
		film.setRecommendationDate(recommendationDate);
		return film;
	}

}
