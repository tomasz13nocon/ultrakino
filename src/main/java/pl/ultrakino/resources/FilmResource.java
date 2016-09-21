package pl.ultrakino.resources;

import pl.ultrakino.model.Film;
import pl.ultrakino.model.Player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilmResource extends ContentResource {

	private Integer filmId;
	private String title;
	private Integer year;
	private Float rating;
	private Integer timesRated;
	private String originalTitle;
	private String description;
	private String coverFilename;
	private LocalDate worldPremiere;
	private LocalDate localPremiere;
	private Set<Player.LanguageVersion> languageVersions = new HashSet<>();
	private Set<Integer> categories = new HashSet<>();
	private LocalDateTime recommendationDate;

	@Override
	public Film toDomainObject() {
		return null; // TODO
	}

	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public Set<Player.LanguageVersion> getLanguageVersions() {
		return languageVersions;
	}

	public void setLanguageVersions(Set<Player.LanguageVersion> languageVersions) {
		this.languageVersions = languageVersions;
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
}
