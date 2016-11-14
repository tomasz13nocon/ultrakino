package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EpisodeDetailsResource {

	private Integer uid;
	private String title;
	private Float rating;
	private Integer timesRated = 0;
	private LocalDate worldPremiere;
	private LocalDate localPremiere;
	private LocalDateTime additionDate;
	private Integer views;
	private Integer season;
	private Integer episodeNumber;
	private EpisodeResource previousEpisode;
	private EpisodeResource nextEpisode;

	private Float userRating;
	private List<CommentResource> comments = new ArrayList<>();
	private List<PlayerResource> players = new ArrayList<>();

}
