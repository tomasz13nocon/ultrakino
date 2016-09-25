package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Player;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlayerResource extends ResourceSupport {

	private int uid;
	private Player.LanguageVersion languageVersion;
	private String src;
	private String quality;
	private LocalDateTime additionDate;
	private UserDetailsResource addedBy;
	private Content content;
	private boolean foreignSrc;
	private boolean lostSrc;

}
