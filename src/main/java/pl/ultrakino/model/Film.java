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

	/**
	 * Categories are Integers, because we don't really need any category semantics anywhere but the frontend client.
	 * All that matters here is their uniqueness.
	 */
	@ElementCollection
	private List<Integer> categories;


	public Film() {}

	public Film(String title, List<Rating> ratings, Float rating, Integer timesRated, String originalTitle, String description, String coverFilename, List<Person> cast, LocalDate worldPremiere, LocalDate localPremiere, List<Player> players, Integer views, List<Integer> categories) {
		super(title, ratings, rating, timesRated);
		this.originalTitle = originalTitle;
		this.description = description;
		this.coverFilename = coverFilename;
		this.cast = cast;
		this.worldPremiere = worldPremiere;
		this.localPremiere = localPremiere;
		this.players = players;
		this.views = views;
		this.categories = categories;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverFilename() {
		return coverFilename;
	}

	public void setCoverFilename(String coverFilename) {
		this.coverFilename = coverFilename;
	}

	public List<Person> getCast() {
		return cast;
	}

	public void setCast(List<Person> cast) {
		this.cast = cast;
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

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}
}
