package pl.ultrakino.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "films")
public class Film extends Content {

	@NotNull
	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Rating> ratings = new ArrayList<>();

	private Float rating;

	@Column(name = "times_rated")
	private Integer timesRated = 0;

	@Column(name = "original_title")
	private String originalTitle;

	private String description;

	@Column(name = "cover_filename")
	private String coverFilename;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private Set<FilmographyEntry> castAndCrew = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "category")
	private Set<Integer> categories = new HashSet<>();

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL/*, mappedBy = "content"*/)
	@JoinColumn(name = "content_id")
	private Set<Player> players = new HashSet<>();

	private Integer views;

	private Integer year;

	/**
	 * Date of the recommendation. If null, this film's not recommended.
	 */
	@Column(name = "recommendation_date")
	private LocalDateTime recommendationDate;

	@Column(name = "addition_date")
	private LocalDateTime additionDate;

	@Column(name = "filmweb_id")
	private String filmwebId;

	@Column(name = "running_time")
	private Integer runningTime;

	@ElementCollection
	@Column(name = "country")
	private List<String> productionCountries = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "content_id")
	private Set<Comment> comments = new HashSet<>();



	@PrePersist
	public void prePersist() {
		additionDate = LocalDateTime.now();
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

	public Set<FilmographyEntry> getCastAndCrew() {
		return castAndCrew;
	}

	public void setCastAndCrew(Set<FilmographyEntry> castAndCrew) {
		this.castAndCrew = castAndCrew;
	}

	public Set<Integer> getCategories() {
		return categories;
	}

	public void setCategories(Set<Integer> categories) {
		this.categories = categories;
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

	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public LocalDateTime getRecommendationDate() {
		return recommendationDate;
	}

	public void setRecommendationDate(LocalDateTime recommendationDate) {
		this.recommendationDate = recommendationDate;
	}

	public LocalDateTime getAdditionDate() {
		return additionDate;
	}

	public void setAdditionDate(LocalDateTime additionDate) {
		this.additionDate = additionDate;
	}

	public String getFilmwebId() {
		return filmwebId;
	}

	public void setFilmwebId(String filmwebId) {
		this.filmwebId = filmwebId;
	}

	public Integer getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(Integer runningTime) {
		this.runningTime = runningTime;
	}

	public List<String> getProductionCountries() {
		return productionCountries;
	}

	public void setProductionCountries(List<String> productionCountries) {
		this.productionCountries = productionCountries;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Film)) return false;
		Film film = (Film) o;
		return Objects.equals(getTitle(), film.getTitle()) &&
				Objects.equals(getWorldPremiere(), film.getWorldPremiere()) &&
				Objects.equals(getId(), film.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTitle(), getWorldPremiere());
	}
}
