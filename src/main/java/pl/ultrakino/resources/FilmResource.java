package pl.ultrakino.resources;

import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Person;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.Rating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilmResource extends ResourceSupport {

	private String title;
	private List<Rating> ratings = new ArrayList<>();
	private Float rating;
	private Integer timesRated;
	private String originalTitle;
	private String description;
	private String coverFilename;
	private List<Person> cast;
	private LocalDate worldPremiere;
	private LocalDate localPremiere;
	private List<Player> players;
	private Integer views;
	private List<Integer> categories;

	public FilmResource() {}

	public FilmResource(String title, List<Rating> ratings, Float rating, Integer timesRated, String originalTitle, String description, String coverFilename, List<Person> cast, LocalDate worldPremiere, LocalDate localPremiere, List<Player> players, Integer views, List<Integer> categories) {
		this.title = title;
		this.ratings = ratings;
		this.rating = rating;
		this.timesRated = timesRated;
		this.originalTitle = originalTitle;
		this.description = description;
		this.coverFilename = coverFilename;
		this.cast = cast;
		this.worldPremiere = worldPremiere;
		this.localPremiere = localPremiere;
		this.players = players;
		this.views = views;
		this.categories = categories;
	}

	public FilmResource(Film film) {
		this(film.getTitle(), film.getRatings(), film.getRating(), film.getTimesRated(), film.getOriginalTitle(), film.getDescription(), film.getCoverFilename(), film.getCast(), film.getWorldPremiere(), film.getLocalPremiere(), film.getPlayers(), film.getViews(), film.getCategories());
	}

	public Film toFilm() {
		return new Film(title, ratings, rating, timesRated, originalTitle, description, coverFilename, cast, worldPremiere, localPremiere, players, views, categories);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
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

	public List<Person> getCast() {
		return cast;
	}

	public void setCast(List<Person> cast) {
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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
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
}
