package pl.ultrakino.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "episodes")
public class Episode extends Content {

	@Column(name = "world_premiere")
	private LocalDate worldPremiere;

	@Column(name = "local_premiere")
	private LocalDate localPremiere;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
	private List<Player> players;

	@ManyToOne
	@JoinColumn(name = "series_content_id")
	private Series series;

	private Integer views;

}
