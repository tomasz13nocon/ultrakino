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
	@Enumerated(EnumType.STRING)
	private LanguageVersion languageVersion;

	private String src;

	@Enumerated(EnumType.STRING)
	private Quality quality;

	@Column(name = "addition_date")

	private LocalDateTime additionDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User addedBy;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;

	@Column(name = "foreign_src")
	private boolean foreignSrc;

	@Column(name = "lost_src")
	private boolean lostSrc;


	@PrePersist
	public void prePersist() {
		additionDate = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LanguageVersion getLanguageVersion() {
		return languageVersion;
	}

	public void setLanguageVersion(LanguageVersion languageVersion) {
		this.languageVersion = languageVersion;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public LocalDateTime getAdditionDate() {
		return additionDate;
	}

	public void setAdditionDate(LocalDateTime additionDate) {
		this.additionDate = additionDate;
	}

	public User getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(User addedBy) {
		this.addedBy = addedBy;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public boolean isForeignSrc() {
		return foreignSrc;
	}

	public void setForeignSrc(boolean foreignSrc) {
		this.foreignSrc = foreignSrc;
	}

	public boolean isLostSrc() {
		return lostSrc;
	}

	public void setLostSrc(boolean lostSrc) {
		this.lostSrc = lostSrc;
	}
}
