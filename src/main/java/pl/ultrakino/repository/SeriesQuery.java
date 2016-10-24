package pl.ultrakino.repository;

import lombok.Getter;
import lombok.Setter;
import pl.ultrakino.model.Player;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SeriesQuery {

	public enum OrderBy {
		ADDITION_DATE,
		PREMIERE,
		TITLE,
		RECOMMENDATION_DATE,
		VIEWS,
	}

	private String title;
	private Integer yearFrom;
	private Integer yearTo;
	private List<Integer> categories;
	private Set<Player.LanguageVersion> versions;
	private ContentQuery.OrderBy orderBy;
	private boolean asc;
	private Integer resultLimit;
	private Integer pageNumber;

}
