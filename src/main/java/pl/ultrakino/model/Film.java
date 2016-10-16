package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Category> categories = new HashSet<>();

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Country> productionCountries = new HashSet<>();

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	//////////////////////////////////

	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL/*, mappedBy = "content"*/)
	@JoinColumn(name = "content_id")
	private Set<Player> players = new HashSet<>();

	private Integer views;

	@Column(name = "recommendation_date")
	private LocalDateTime recommendationDate;

	@Column(name = "addition_date")
	private LocalDateTime additionDate;

	@Column(name = "running_time")
	private Integer runningTime;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "content_id")
	private Set<Comment> comments = new HashSet<>();

	private String alltubeFilmwebId;


	@PrePersist
	public void prePersist() {
		additionDate = LocalDateTime.now();
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Film)) return false;
		Film film = (Film) o;
		return Objects.equals(getTitle(), film.getTitle()) &&
				Objects.equals(getWorldPremiere(), film.getWorldPremiere()) &&
				Objects.equals(getId(), film.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTitle(), getWorldPremiere());
	}
}
