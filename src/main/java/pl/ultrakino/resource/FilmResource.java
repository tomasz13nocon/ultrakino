package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.model.Player;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class FilmResource implements ContentResource {

	private Integer uid;
	private String title;
	private Integer year;
	private Float rating;
	private Integer timesRated = 0;
	private String originalTitle;
	private String description;
	private String coverFilename;
	private LocalDate worldPremiere;
	private LocalDate localPremiere;
	private Set<Player.LanguageVersion> languageVersions = new HashSet<>();
	private Set<FilmCategory> categories = new HashSet<>();

}
