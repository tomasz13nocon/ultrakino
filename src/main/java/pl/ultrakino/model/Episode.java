package pl.ultrakino.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "episodes")
public class Episode extends Content {

	@Embedded
	private ContentComponent contentComponent = new ContentComponent();

	@Embedded
	private EpisodeFilmComponent episodeFilmComponent = new EpisodeFilmComponent();

	@ManyToOne
	@JoinColumn(name = "series_content_id")
	private Series series;


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

}
