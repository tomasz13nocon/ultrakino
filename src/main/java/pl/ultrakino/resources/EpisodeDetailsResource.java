package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;
import pl.ultrakino.model.Player;
import pl.ultrakino.model.Rating;
import pl.ultrakino.model.Series;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class EpisodeDetailsResource extends ResourceSupport {

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

	private List<CommentResource> comments = new ArrayList<>();
	private List<PlayerResource> players = new ArrayList<>();

}
