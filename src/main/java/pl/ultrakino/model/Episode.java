package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "episodes")
public class Episode extends Content {

	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content", orphanRemoval = true)
	private Set<Rating> ratings = new HashSet<>();

	private Float rating;

	@Column(name = "times_rated")
	private Integer timesRated = 0;

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private Set<Player> players = new HashSet<>();

	@Column(name = "addition_date")
	private LocalDateTime additionDate;

	private Integer views;

	private Integer season;

	private Integer episodeNumber;

	@ManyToOne
	@JoinColumn(name = "series_content_id")
	private Series series;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content", orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();

	@PrePersist
	public void prePersist() {
		additionDate = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Episode)) return false;
		Episode episode = (Episode) o;
		return Objects.equals(getSeason(), episode.getSeason()) &&
				Objects.equals(getEpisodeNumber(), episode.getEpisodeNumber()) &&
				Objects.equals(getSeries(), episode.getSeries());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSeason(), getEpisodeNumber(), getSeries());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("title", title)
				.append("ratings", ratings)
				.append("rating", rating)
				.append("timesRated", timesRated)
				.toString();
	}
}
