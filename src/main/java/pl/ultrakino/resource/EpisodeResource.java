package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class EpisodeResource implements ContentResource {

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

}
