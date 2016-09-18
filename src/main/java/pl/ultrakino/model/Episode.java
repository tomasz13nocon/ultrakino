package pl.ultrakino.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "episodes")
public class Episode extends Content {

	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Rating> ratings = new ArrayList<>();

	private Float rating;

	@Column(name = "times_rated")
	private Integer timesRated;

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL/*, mappedBy = "content"*/)
	@JoinColumn(name = "content_id")
	private List<Player> players = new ArrayList<>();

	private Integer views;

	@ManyToOne
	@JoinColumn(name = "series_content_id")
	private Series series;



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

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}
}
