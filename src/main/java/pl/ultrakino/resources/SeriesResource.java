package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.FilmCategory;
import pl.ultrakino.model.SeriesCategory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SeriesResource extends ResourceSupport {

	private Integer uid;
	private String title;
	private Integer year;
	private Float rating;
	private Integer timesRated = 0;
	private String originalTitle;
	private String description;
	private String coverFilename;
	private LocalDate worldPremiere;
	private Set<SeriesCategory> categories = new HashSet<>();

	//	private List<EpisodeResource> episodes = new ArrayList<>();
	private Integer seasonCount;
	private Integer episodeCount;

}
