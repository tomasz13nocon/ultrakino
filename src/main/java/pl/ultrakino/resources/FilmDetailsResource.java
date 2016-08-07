package pl.ultrakino.resources;

import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Person;
import pl.ultrakino.model.Player;

import java.time.LocalDate;
import java.util.List;

public class FilmDetailsResource extends ResourceSupport {

	private String title;
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
	private LocalDate recommendedOn;


	public Film toFilm() {
		Film film = new Film();
		film.setTitle(title);
		film.setRating(rating);
		film.setTimesRated(timesRated);
		film.setOriginalTitle(originalTitle);
		film.setDescription(description);
		film.setCoverFilename(coverFilename);
		film.setCast(cast);
		film.setWorldPremiere(worldPremiere);
		film.setLocalPremiere(localPremiere);
		film.setPlayers(players);
		film.setViews(views);
		film.setCategories(categories);
		film.setRecommendationDate(recommendedOn);
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

	public LocalDate getRecommendedOn() {
		return recommendedOn;
	}

	public void setRecommendedOn(LocalDate recommendedOn) {
		this.recommendedOn = recommendedOn;
	}
}
