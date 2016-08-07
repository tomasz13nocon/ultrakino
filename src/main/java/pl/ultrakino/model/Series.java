package pl.ultrakino.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
public class Series extends Content {

	@Embedded
	private ContentComponent contentComponent = new ContentComponent();

	@Embedded
	private SeriesFilmComponent seriesFilmComponent = new SeriesFilmComponent();

	@Column(name = "number_of_seasons")
	private Integer numberOfSeasons;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
	private List<Episode> episodes;



	public String getTitle() {
		return contentComponent.getTitle();
	}

	public void setTitle(String title) {
		contentComponent.setTitle(title);
	}

	public List<Rating> getRatings() {
		return contentComponent.getRatings();
	}

	public void setRatings(List<Rating> ratings) {
		contentComponent.setRatings(ratings);
	}

	public Float getRating() {
		return contentComponent.getRating();
	}

	public void setRating(Float rating) {
		contentComponent.setRating(rating);
	}

	public Integer getTimesRated() {
		return contentComponent.getTimesRated();
	}

	public void setTimesRated(Integer timesRated) {
		contentComponent.setTimesRated(timesRated);
	}

	public String getOriginalTitle() {
		return seriesFilmComponent.getOriginalTitle();
	}

	public void setOriginalTitle(String originalTitle) {
		seriesFilmComponent.setOriginalTitle(originalTitle);
	}

	public String getDescription() {
		return seriesFilmComponent.getDescription();
	}

	public void setDescription(String description) {
		seriesFilmComponent.setDescription(description);
	}

	public String getCoverFilename() {
		return seriesFilmComponent.getCoverFilename();
	}

	public void setCoverFilename(String coverFilename) {
		seriesFilmComponent.setCoverFilename(coverFilename);
	}

	public List<Person> getCast() {
		return seriesFilmComponent.getCast();
	}

	public void setCast(List<Person> cast) {
		seriesFilmComponent.setCast(cast);
	}

	public List<Integer> getCategories() {
		return seriesFilmComponent.getCategories();
	}

	public void setCategories(List<Integer> categories) {
		seriesFilmComponent.setCategories(categories);
	}

}
