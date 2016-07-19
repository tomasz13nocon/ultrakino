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

}
