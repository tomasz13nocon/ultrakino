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
@Inheritance(strategy = InheritanceType.JOINED) // This table is necessary for foreign keys to work properly
public abstract class Content {

	@Id
	@SequenceGenerator(name = "content_id_gen", sequenceName = "contents_content_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_id_gen")
	@Column(name = "content_id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
