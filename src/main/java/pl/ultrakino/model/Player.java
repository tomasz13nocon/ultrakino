package pl.ultrakino.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "players")
public class Player {

	@Id
	@SequenceGenerator(name = "player_id_gen", sequenceName = "players_player_id_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_gen")
	@Column(name = "player_id")
	private Integer id;

	@Column(name = "language_version")
	private LanguageVersion languageVersion;

	private String src;

	private Quality quality;

	@Column(name = "addition_date")
	private LocalDateTime additionDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User addedBy;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;


}
