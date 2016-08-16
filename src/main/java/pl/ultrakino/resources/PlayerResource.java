package pl.ultrakino.resources;

import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.LanguageVersion;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.Quality;

import java.time.LocalDateTime;

public class PlayerResource extends ResourceSupport {

	private LanguageVersion languageVersion;
	private String src;
	private Quality quality;
	private LocalDateTime additionDate;
	private UserResource addedBy;
	private Content content;
	private boolean foreignSrc;
	private boolean lostSrc;


	public Player toDomainObject() {
		return toDomainObject(false);
	}

	public Player toDomainObject(boolean skipAddedBy) {
		Player player = new Player();
		player.setLanguageVersion(languageVersion);
		player.setSrc(src);
		player.setQuality(quality);
		player.setAdditionDate(additionDate);
		player.setAddedBy(addedBy.toDomainObject());
		player.setContent(content);
		player.setForeignSrc(foreignSrc);
		player.setLostSrc(lostSrc);
		return player;
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

	public UserResource getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(UserResource addedBy) {
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
