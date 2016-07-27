package pl.ultrakino.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Content (film, episode or series) that has ID, title, and is rateable
 */
@Entity
@Table(name = "contents") // For automatic name generation of some tables
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Content {

	@Id
	@SequenceGenerator(name = "content_id_gen", sequenceName = "contents_content_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_id_gen")
	@Column(name = "content_id")
	private Integer id;

	private String title;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Rating> ratings = new ArrayList<>();

	private Float rating;

	@Column(name = "times_rated")
	private Integer timesRated;

	public Content() {}

	public Content(String title, List<Rating> ratings, Float rating, Integer timesRated) {
		this.title = title;
		this.ratings = ratings;
		this.rating = rating;
		this.timesRated = timesRated;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getTimesRated() {
		return timesRated;
	}

	public void setTimesRated(Integer timesRated) {
		this.timesRated = timesRated;
	}
}
