package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "series")
public class Series extends Content {

	@NotNull
	private String title;

	private Integer year;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content", orphanRemoval = true)
	private Set<Rating> ratings = new HashSet<>();

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

	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "series_series_categories",
			joinColumns = @JoinColumn(name="content_id"),
			inverseJoinColumns = @JoinColumn(name="series_category_id"))
	private Set<SeriesCategory> seriesCategories = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private Set<Country> productionCountries = new HashSet<>();

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;



	@Column(name = "recommendation_date")
	private LocalDateTime recommendationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
	private List<Episode> episodes = new ArrayList<>();

	@Column(name = "season_count")
	private Integer seasonCount = 0;

	@Column(name = "episode_count")
	private Integer episodeCount = 0;

	@Column(name = "running_time")
	private Integer runningTime;

	@Column(name = "tvseriesonline_title")
	private String tvseriesonlineTitle;

	@Column(name = "newest_episode_addition_date")
	private LocalDateTime updateDate;


	@PreUpdate
	public void preUpdate() {
		updateDate = LocalDateTime.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Series)) return false;
		Series series = (Series) o;
		return Objects.equals(getTitle(), series.getTitle()) &&
				Objects.equals(getWorldPremiere(), series.getWorldPremiere());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTitle(), getWorldPremiere());
	}
}
