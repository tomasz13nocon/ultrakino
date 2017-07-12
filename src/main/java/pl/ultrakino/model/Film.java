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
@Table(name = "films")
public class Film extends Content {

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
	@JoinTable(name = "films_film_categories",
			joinColumns = @JoinColumn(name="content_id"),
			inverseJoinColumns = @JoinColumn(name="film_category_id"))
	private Set<FilmCategory> filmCategories = new HashSet<>();

	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private Set<Country> productionCountries = new HashSet<>();

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;



	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private Set<Player> players = new HashSet<>();

	private Integer views;

	@Column(name = "recommendation_date")
	private LocalDateTime recommendationDate;

	@Column(name = "addition_date")
	private LocalDateTime additionDate;

	@Column(name = "running_time")
	private Integer runningTime;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content", orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();


	@PrePersist
	public void prePersist() {
		additionDate = LocalDateTime.now();
	}


	@Override
	public String toString() {
		return title + " (" + year + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Film)) return false;
		Film film = (Film) o;
		return Objects.equals(getTitle(), film.getTitle()) &&
				Objects.equals(getWorldPremiere(), film.getWorldPremiere());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTitle(), getWorldPremiere());
	}
}
