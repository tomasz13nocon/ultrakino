package pl.ultrakino.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingResource {

	private long uid;
	private ContentResource content;
	private UserDetailsResource ratedBy;
	private Float rating;

}
