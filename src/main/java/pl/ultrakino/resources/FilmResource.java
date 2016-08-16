package pl.ultrakino.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilmResource extends ContentResource {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer filmId;

	@JsonView(Views.FilmCreation.class)
	private String title;

	@JsonView(Views.FilmCreation.class)
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																														private Integer year;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Float rating;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer timesRated;

	@JsonView(Views.FilmCreation.class)
	private String originalTitle;

	@JsonView(Views.FilmCreation.class)
	private String description;

	@JsonView(Views.FilmCreation.class)
	private String coverFilename;

	@JsonView(Views.FilmCreation.class)
	private List<PersonResource> cast = new ArrayList<>();

	@JsonView(Views.FilmCreation.class)
	private LocalDate worldPremiere;

	@JsonView(Views.FilmCreation.class)
	private LocalDate localPremiere;

	@JsonView(Views.FilmCreation.class)
	private List<PlayerResource> players = new ArrayList<>();

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer views;

	@JsonView(Views.FilmCreation.class)
	private List<Integer> categories = new ArrayList<>();

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDate recommendationDate;


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
		film.setPlayers(players.stream().map(PlayerResource::toDomainObject).collect(Collectors.toList()));
		film.setViews(views);
		film.setCategories(categories);
		film.setRecommendationDate(recommendationDate);
		return film;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getTimesRated() {
		return timesRated;
	}

	public void setTimesRated(Integer timesRated) {
		this.timesRated = timesRated;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverFilename() {
		return coverFilename;
	}

	public void setCoverFilename(String coverFilename) {
		this.coverFilename = coverFilename;
	}

	public List<PersonResource> getCast() {
		return cast;
	}

	public void setCast(List<PersonResource> cast) {
		this.cast = cast;
	}

	public LocalDate getWorldPremiere() {
		return worldPremiere;
	}

	public void setWorldPremiere(LocalDate worldPremiere) {
		this.worldPremiere = worldPremiere;
	}

	public LocalDate getLocalPremiere() {
		return localPremiere;
	}

	public void setLocalPremiere(LocalDate localPremiere) {
		this.localPremiere = localPremiere;
	}

	public List<PlayerResource> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerResource> players) {
		this.players = players;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}

	public LocalDate getRecommendationDate() {
		return recommendationDate;
	}

	public void setRecommendationDate(LocalDate recommendationDate) {
		this.recommendationDate = recommendationDate;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public Integer getFilmId() {
		return filmId;
	}
}
