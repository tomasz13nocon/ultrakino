package pl.ultrakino.resources;

import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Film;

import java.time.LocalDate;

public class FilmResource extends ResourceSupport {

	private String title;
	private Float rating;
	private Integer timesRated;
	private String originalTitle;
	private String description;
	private String coverFilename;
	private LocalDate worldPremiere;
	private LocalDate localPremiere;
	private Integer views;
	private LocalDate recommendedOn;


	public Film toFilm() {
		Film film = new Film();
		film.setTitle(title);
		film.setRating(rating);
		film.setTimesRated(timesRated);
		film.setOriginalTitle(originalTitle);
		film.setDescription(description);
		film.setCoverFilename(coverFilename);
		film.setWorldPremiere(worldPremiere);
		film.setLocalPremiere(localPremiere);
		film.setViews(views);
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

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public LocalDate getRecommendedOn() {
		return recommendedOn;
	}

	public void setRecommendedOn(LocalDate recommendedOn) {
		this.recommendedOn = recommendedOn;
	}
}
