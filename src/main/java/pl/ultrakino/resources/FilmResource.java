package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.Category;
import pl.ultrakino.model.Film;
import pl.ultrakino.model.Player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class FilmResource extends ContentResource {

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
	private Set<Category> categories = new HashSet<>();

}
