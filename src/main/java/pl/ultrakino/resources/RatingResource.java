package pl.ultrakino.resources;

import pl.ultrakino.model.Rating;

public class RatingResource {

	private ContentResource content;
	private UserDetailsResource ratedBy;
	private Float rating;


	public Rating toDomainObject() {
		Rating rating = new Rating();
		rating.setContent(content.toDomainObject());
		rating.setRatedBy(ratedBy.toDomainObject());
		rating.setRating(this.rating);
		return rating;
	}

	public ContentResource getContent() {
		return content;
	}

	public void setContent(ContentResource content) {
		this.content = content;
	}

	public UserDetailsResource getRatedBy() {
		return ratedBy;
	}

	public void setRatedBy(UserDetailsResource ratedBy) {
		this.ratedBy = ratedBy;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}
}
