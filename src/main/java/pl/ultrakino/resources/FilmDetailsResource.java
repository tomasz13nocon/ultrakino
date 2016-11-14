package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.FilmCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class FilmDetailsResource {

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
	private Set<FilmCategory> categories = new HashSet<>();

	private Float userRating;
	private List<PersonResource> cast = new ArrayList<>();
	private List<PlayerResource> players = new ArrayList<>();
	private Integer views;
	private List<CommentResource> comments = new ArrayList<>();
	private LocalDateTime recommendationDate;

}
