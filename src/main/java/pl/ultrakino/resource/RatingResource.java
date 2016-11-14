package pl.ultrakino.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResource {

	private long uid;
	private int contentId;
	private int userId;
	private Float rating;

}
