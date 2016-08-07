package pl.ultrakino.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class SeriesFilmComponent {

	@Column(name = "original_title")
	private String originalTitle;

	private String description;

	@Column(name = "cover_filename")
	private String coverFilename;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "persons_contents_filmography",
			joinColumns = @JoinColumn(name = "content_id"),
			inverseJoinColumns = @JoinColumn(name = "person_id"))
	private List<Person> cast = new ArrayList<>();

	/**
	 * Categories are Integers, because we don't really need any category semantics anywhere but the frontend client.
	 * All that matters here is their uniqueness.
	 */
	@ElementCollection
	@Column(name = "category")
	private List<Integer> categories = new ArrayList<>();


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

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}
}
