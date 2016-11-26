package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SeriesDetailsResource {

	private Integer uid;
	private String title;
	private Integer year;
	private Float rating;
	private Integer timesRated = 0;
	private Float userRating;
	private String originalTitle;
	private String description;
	private String coverFilename;
	private LocalDate worldPremiere;
	private Set<SeriesCategory> categories = new HashSet<>();
	private Integer seasonCount;
	private Integer episodeCount;

	private List<PersonResource> castAndCrew = new ArrayList<>();
	private Integer runningTime;

}
