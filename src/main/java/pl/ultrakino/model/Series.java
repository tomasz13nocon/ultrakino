package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Getter
@Setter
@Entity
@Table(name = "series")
public class Series extends Content {

	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Rating> ratings = new ArrayList<>();

	private Float rating;

	private String filmwebId;

	@Column(name = "times_rated")
	private Integer timesRated;

	@Column(name = "original_title")
	private String originalTitle;

	private String description;

	@Column(name = "cover_filename")
	private String coverFilename;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<FilmographyEntry> castAndCrew = new ArrayList<>();

	@ElementCollection
	@Column(name = "category")
	private List<Integer> categories = new ArrayList<>();

	@Column(name = "number_of_seasons")
	private Integer numberOfSeasons;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
	private List<Episode> episodes;

}
