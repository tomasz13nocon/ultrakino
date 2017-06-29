package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.Content;
import pl.ultrakino.model.Player;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlayerResource {

	private int uid;
	private Player.LanguageVersion languageVersion;
	private String src;
	private String quality;
	private LocalDateTime additionDate;
	private UserResource addedBy;
	private ContentResource content;
	private String hosting;

}
