package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "episodes")
public class Episode extends Content {

	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Rating> ratings = new ArrayList<>();

	private Float rating;

	@Column(name = "times_rated")
	private Integer timesRated = 0;

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL/*, mappedBy = "content"*/)
	@JoinColumn(name = "content_id")
	private Set<Player> players = new HashSet<>();

	private Integer views;

	private Integer season;

	private Integer episodeNumber;

	@ManyToOne
	@JoinColumn(name = "series_content_id")
	private Series series;

}
