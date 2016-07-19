package pl.ultrakino.model;

import javax.persistence.*;

/**
 * Represents a single rating given for a single piece of content (e.g. a film) by a single user.
 */
@Entity
@Table(name = "ratings")
public class Rating {

	@Id
	@SequenceGenerator(name = "rating_id_gen", sequenceName = "ratings_rating_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_id_gen")
	@Column(name = "rating_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User ratedBy;

	private Float rating;


}
