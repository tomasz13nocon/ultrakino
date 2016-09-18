package pl.ultrakino.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "series")
public class Series extends Content {

	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Rating> ratings = new ArrayList<>();

	private Float rating;

	@Column(name = "times_rated")
	private Integer timesRated;

	@Column(name = "original_title")
	private String originalTitle;

	private String description;

	@Column(name = "cover_filename")
	private String coverFilename;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<FilmographyEntry> castAndCrew = new ArrayList<>();

	@ElementCollection
	@Column(name = "category")
	private List<Integer> categories = new ArrayList<>();

	@Column(name = "number_of_seasons")
	private Integer numberOfSeasons;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
	private List<Episode> episodes;



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

	public List<FilmographyEntry> getCastAndCrew() {
		return castAndCrew;
	}

	public void setCastAndCrew(List<FilmographyEntry> castAndCrew) {
		this.castAndCrew = castAndCrew;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}

	public Integer getNumberOfSeasons() {
		return numberOfSeasons;
	}

	public void setNumberOfSeasons(Integer numberOfSeasons) {
		this.numberOfSeasons = numberOfSeasons;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
}
