package pl.ultrakino.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a single rating given for a single piece of content (e.g. a film) by a single user.
 */
@Entity
@Table(name = "ratings")
@Getter
@Setter
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rating_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User ratedBy;

	private Float rating;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Rating)) return false;
		Rating rating= (Rating) o;
		return Objects.equals(getRatedBy(), rating.getRatedBy()) &&
				Objects.equals(getContent(), rating.getContent());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRatedBy(), getContent());
	}

}
