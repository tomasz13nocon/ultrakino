package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SeriesDetailsResource extends ResourceSupport {

	private String title;
	private Integer year;
	private List<Rating> ratings = new ArrayList<>();
	private Float rating;
	private Integer timesRated = 0;
	private String originalTitle;
	private String description;
	private String coverFilename;
	private Set<FilmographyEntry> castAndCrew = new HashSet<>();
	private Set<Category> categories = new HashSet<>();
	private Set<Country> productionCountries = new HashSet<>();
	private LocalDate worldPremiere;


	private List<Episode> episodes = new ArrayList<>();
	private Integer seasonCount;
	private Integer episodeCount;
	private Integer runningTime;

}
