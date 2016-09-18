package pl.ultrakino.resources;

import pl.ultrakino.model.Film;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	public Set<Integer> getCategories() {
		return categories;
	}

	public void setCategories(Set<Integer> categories) {
		this.categories = categories;
	}

	public LocalDateTime getRecommendationDate() {
		return recommendationDate;
	}

	public void setRecommendationDate(LocalDateTime recommendationDate) {
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
