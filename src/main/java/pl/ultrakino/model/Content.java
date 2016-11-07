package pl.ultrakino.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Content is a film, an episode or a series.
 */
@Entity
@Table(name = "contents")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Content implements Rateable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
