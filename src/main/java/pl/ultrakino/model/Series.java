package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "series")
public class Series extends Content {

	@NotNull
	private String title;

	private Integer year;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Rating> ratings = new ArrayList<>();

	private Float rating;

	@Column(name = "times_rated")
	private Integer timesRated = 0;

	@Column(name = "filmweb_id")
	private String filmwebId;

	@Column(name = "original_title")
	private String originalTitle;

	private String description;

	@Column(name = "cover_filename")
	private String coverFilename;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private Set<FilmographyEntry> castAndCrew = new HashSet<>();

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Category> categories = new HashSet<>();

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Country> productionCountries = new HashSet<>();

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	//////////////////////////////////

	@Column(name = "number_of_seasons")
	private Integer numberOfSeasons;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
	private List<Episode> episodes = new ArrayList<>();

	@Column(name = "season_count")
	private Integer seasonCount;

	@Column(name = "episode_count")
	private Integer episodeCount;

	@Column(name = "running_time")
	private Integer runningTime;

}
