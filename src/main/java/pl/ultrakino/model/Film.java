package pl.ultrakino.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "films")
public class Film extends Content {

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

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Player> players;

	private Integer views;

	private Integer category;

}
