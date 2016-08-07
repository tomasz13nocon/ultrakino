package pl.ultrakino.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "films")
public class Film extends Content {

	@Embedded
	private ContentComponent contentComponent = new ContentComponent();

	@Embedded
	private SeriesFilmComponent seriesFilmComponent = new SeriesFilmComponent();

	@Embedded
	private EpisodeFilmComponent episodeFilmComponent = new EpisodeFilmComponent();

	private Integer year;

	/**
	 * Date of the recommendation. If null, this film's not recommended.
	 */
	@Column(name = "recommendation_date")
	private LocalDate recommendationDate;


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

	public LocalDate getWorldPremiere() {
		return episodeFilmComponent.getWorldPremiere();
	}

	public void setWorldPremiere(LocalDate worldPremiere) {
		episodeFilmComponent.setWorldPremiere(worldPremiere);
	}

	public LocalDate getLocalPremiere() {
		return episodeFilmComponent.getLocalPremiere();
	}

	public void setLocalPremiere(LocalDate localPremiere) {
		episodeFilmComponent.setLocalPremiere(localPremiere);
	}

	public List<Player> getPlayers() {
		return episodeFilmComponent.getPlayers();
	}

	public void setPlayers(List<Player> players) {
		episodeFilmComponent.setPlayers(players);
	}

	public Integer getViews() {
		return episodeFilmComponent.getViews();
	}

	public void setViews(Integer views) {
		episodeFilmComponent.setViews(views);
	}


	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public LocalDate getRecommendationDate() {
		return recommendationDate;
	}

	public void setRecommendationDate(LocalDate recommendedOn) {
		this.recommendationDate = recommendedOn;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
