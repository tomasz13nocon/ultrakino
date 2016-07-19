package pl.ultrakino.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "series")
public class Series extends Content {

	@Column(name = "original_title")
	private String originalTitle;

	private String description;

	@Column(name = "cover_filename")
	private String coverFilename;

	@ManyToMany
	@JoinTable(name = "persons_contents_filmography",
			joinColumns = @JoinColumn(name = "content_id"),
			inverseJoinColumns = @JoinColumn(name = "person_id"))
	private List<Person> cast;

	@Column(name = "number_of_seasons")
	private Integer numberOfSeasons;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "series")
	private List<Episode> episodes;

	private Integer category;

}
